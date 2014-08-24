package com.th3l4b.srm.codegen.java.web.runtime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.th3l4b.common.data.Pair;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.srm.runtime.ISRMContext;
import com.th3l4b.srm.runtime.IToMapEntityParserContext;

@SuppressWarnings("serial")
public abstract class AbstractJSONEntitiesServlet<CONTEXT extends ISRMContext<FINDER>, FINDER extends IFinder>
		extends HttpServlet {

	public static final String JSON_CONTENT_TYPE = "application/json";

	protected abstract Pair<CONTEXT, IToMapEntityParserContext> getContext(
			HttpServletRequest req, HttpServletResponse resp) throws Exception;

	protected abstract Iterable<IRuntimeEntity<?>> service(
			Iterable<IRuntimeEntity<?>> parsed, CONTEXT a,
			IToMapEntityParserContext b) throws Exception;

	protected boolean applyCompression() {
		return true;
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// Parse input
			Pair<CONTEXT, IToMapEntityParserContext> pair = getContext(request,
					response);
			JSONEntitiesParserContext parsingContext = new JSONEntitiesParserContext(
					request.getReader(), pair.getA(), pair.getB());
			JSONEntitiesParser json = new JSONEntitiesParser();
			Pair<IRuntimeEntity<?>, Iterable<IRuntimeEntity<?>>> parsed = json
					.parse(false, true, parsingContext);

			// Service
			Iterable<IRuntimeEntity<?>> result = service(parsed.getB(),
					pair.getA(), pair.getB());

			// Write response
			response.setCharacterEncoding(ITextConstants.UTF_8);
			// http://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type
			response.setContentType(JSON_CONTENT_TYPE);

			String accepts = request.getHeader("Accept-Encoding");
			if (applyCompression() && (accepts != null)
					&& (accepts.indexOf("gzip") != -1)) {
				response.setHeader("Content-Encoding", "gzip");
				response.setHeader("Vary", "Accept-Encoding");
				// http://www.redirecttonull.com/?p=134
				GZIPOutputStream gzipos = new GZIPOutputStream(
						response.getOutputStream(), 2048) {
					{
						def.setLevel(Deflater.BEST_COMPRESSION);
					}
				};

				try {
					OutputStreamWriter osw = new OutputStreamWriter(gzipos,
							ITextConstants.UTF_8);
					try {
						JSONEntitiesParserContext writingContext = new JSONEntitiesParserContext(
								osw, pair.getA(), pair.getB());
						json.serialize(result, writingContext);

					} finally {
						osw.close();
					}

				} finally {
					gzipos.close();
				}

			} else {
				JSONEntitiesParserContext writingContext = new JSONEntitiesParserContext(
						response.getWriter(), pair.getA(), pair.getB());
				json.serialize(result, writingContext);

			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
