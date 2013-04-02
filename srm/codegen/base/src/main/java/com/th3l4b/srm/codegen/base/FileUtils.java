package com.th3l4b.srm.codegen.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.ITextConstants;
import com.th3l4b.common.text.TextUtils;

public class FileUtils {

	public static boolean overwriteIfOlder(CodeGeneratorContext context,
			String file, IPrintable printable) throws Exception {
		// Ensure dir exists.
		File dir = context.getOutput();
		if (!dir.exists() && !dir.mkdirs()) {
			throw new IllegalStateException(
					"Could not create all directories for packages: "
							+ dir.getAbsolutePath());
		}
		File f = new File(dir, file);
		CodeGeneratorContext nc = new CodeGeneratorContext();
		context.copyTo(nc);
		nc.setOutput(f);
		String msg = null;
		boolean r = overwriteIfOlder(nc, printable);
		if (r) {
			msg = "Generated";
		} else {
			msg = "Skipping. It is up to date";
		}
		nc.getLog().message(
				TextUtils.toPrintable(msg + ": " + f.getAbsolutePath()));
		return r;
	}

	public static boolean overwriteIfOlder(CodeGeneratorContext context,
			IPrintable printable) throws Exception {
		File f = context.getOutput();
		long timestamp = context.getTimestamp();
		if (context.isOverwrite() || !f.exists()
				|| (f.lastModified() <= timestamp)) {
			FileOutputStream fos = new FileOutputStream(f);
			try {
				OutputStreamWriter osw = new OutputStreamWriter(fos,
						ITextConstants.UTF_8);
				try {
					PrintWriter out = new PrintWriter(osw);
					printable.print(out);
					out.flush();
				} finally {
					osw.flush();
				}
			} finally {
				fos.close();
			}

			return true;
		} else {
			return false;
		}

	}

	public static String asDirectories(String pkg) {
		return pkg.replaceAll("\\.", "/");
	}

	public static boolean java(CodeGeneratorContext context, String pkg,
			String className, IPrintable printable) throws Exception {
		CodeGeneratorContext nc = new CodeGeneratorContext();
		context.copyTo(nc);
		nc.setOutput(new File(context.getOutput(), asDirectories(pkg)));
		return overwriteIfOlder(nc, className + ".java", printable);
	}
}
