package com.th3l4b.srm.parser.junit;

import java.io.InputStream;
import java.io.PrintWriter;

import org.junit.Test;

import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.parser.ParserUtils;

public class ParserTest {
	@Test
	public void testParser() throws Exception {
		PrintWriter out = new PrintWriter(System.out);
		PrintWriter iout = IndentedWriter.get(out);
		InputStream is = getClass().getResourceAsStream("Test.srm");
		try {
			IModel model = ParserUtils.parse(is);
			out.println("Original model:");
			TextUtils.print(model, iout);
			out.println("Normalized model:");
			INormalizedModel normal = Normalizer.normalize(model);
			TextUtils.print(normal, iout);
		} finally {
			is.close();
		}
		iout.flush();
		out.flush();
	}
}
