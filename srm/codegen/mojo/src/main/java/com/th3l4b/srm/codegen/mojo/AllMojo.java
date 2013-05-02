package com.th3l4b.srm.codegen.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import com.th3l4b.common.log.AbstractLog;
import com.th3l4b.common.log.ILogLevel;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGenerator;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;
import com.th3l4b.srm.codegen.java.basic.JavaNames;
import com.th3l4b.srm.parser.ParserUtils;
import com.th3l4b.types.base.basicset.BasicSetTypesContext;

/**
 * Generates all sources
 * 
 * @goal all
 */
public class AllMojo extends AbstractMojo {

	/**
	 * @parameter alias="input"
	 * @required
	 */
	private File _input = null;

	/**
	 * @parameter alias="output"
	 * @required
	 */
	private File _output = null;

	/**
	 * @parameter alias="package"
	 * @required
	 */
	private String _package = null;

	/**
	 * @parameter alias="overwrite" default-value="true"
	 */
	private boolean _overwrite = true;

	public File getInput() {
		return _input;
	}

	public void setInput(File input) {
		_input = input;
	}

	public File getOutput() {
		return _output;
	}

	public void setOutput(File output) {
		_output = output;
	}

	public String getPackage() {
		return _package;
	}

	public void setPackage(String package1) {
		_package = package1;
	}

	public boolean isOverwrite() {
		return _overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		_overwrite = overwrite;
	}

	public void execute() throws MojoExecutionException {
		try {
			IModel model = null;
			long ts = _input.lastModified();

			// Parse input
			FileInputStream fis = new FileInputStream(_input);
			try {
				model = ParserUtils.parse(fis);
			} finally {
				fis.close();
			}

			CodeGeneratorContext context = new CodeGeneratorContext();
			context.setOutput(_output);
			context.setTimestamp(ts);
			context.setOverwrite(_overwrite);
			context.setTypes(BasicSetTypesContext.get());
			context.setLog(new AbstractLog() {
				@Override
				public void log(IPrintable item, ILogLevel level)
						throws Exception {
					String text = TextUtils.toString(item);

					// Remove trailing CR/LF
					text = text.replaceAll("[\\r\\n]*$", "");

					Log log = getLog();
					switch (level) {
					case debug:
						log.debug(text);
						break;
					case message:
						log.info(text);
						break;
					case warning:
						log.warn(text);
						break;
					default:
						log.error(text);
						break;
					}
				}
			});
			{
				// Produce debug files
				final IModel fm = model;
				FileUtils.overwriteIfOlder(context, "model.txt",
						new IPrintable() {
							@Override
							public void print(PrintWriter out) {
								TextUtils.print(fm, out);
							}

						});
			}
			// Normalize...
			INormalizedModel normalized = Normalizer.normalize(model);
			{
				// Produce debug files
				final INormalizedModel fm = normalized;
				FileUtils.overwriteIfOlder(context, "model-normalized.txt",
						new IPrintable() {
							@Override
							public void print(PrintWriter out) {
								TextUtils.print(fm, out);
							}

						});
			}

			// Produce code.
			JavaCodeGeneratorContext javaContext = new JavaCodeGeneratorContext();
			context.copyTo(javaContext);
			javaContext.setJavaNames(new JavaNames());
			javaContext.setPackage(_package);
			JavaCodeGenerator java = new JavaCodeGenerator();
			javaContext.getLog().message(
					TextUtils.toPrintable("Producing entities..."));
			for (INormalizedEntity ne : normalized.items()) {
				java.entity(ne, normalized, javaContext);
				java.entityImpl(ne, normalized, javaContext);
			}
			javaContext.getLog().message(
					TextUtils.toPrintable("Entities production finished."));
			javaContext.getLog().message(
					TextUtils.toPrintable("Producing finder..."));
			java.finder(normalized, javaContext);
			java.finderInMemory(normalized, javaContext);
			javaContext.getLog().message(
					TextUtils.toPrintable("Finder finished."));
			javaContext.getLog().message(
					TextUtils.toPrintable("Producing model utils..."));
			java.modelUtils(normalized, javaContext);
			javaContext.getLog().message(
					TextUtils.toPrintable("Model utils finished."));

		} catch (Exception e) {
			throw new MojoExecutionException("Could not generate code", e);
		}

	}
}
