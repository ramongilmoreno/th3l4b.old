package com.th3l4b.srm.parser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;

public class Main {

	public static void parse(InputStream is) throws Exception {
		PrintWriter out = new PrintWriter(System.out);
		PrintWriter iout = IndentedWriter.get(out);
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

	public static void main(String[] args) throws Exception {
		parse(new FileInputStream(args[0]));
	}
}
