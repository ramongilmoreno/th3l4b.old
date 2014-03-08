package com.th3l4b.srm.codegen.mojo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.TreeSet;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import com.th3l4b.common.log.AbstractLog;
import com.th3l4b.common.log.ILogLevel;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.parser.ParserUtils;
import com.th3l4b.types.base.basicset.BasicSetTypesContext;

/**
 * Base for other mojos.
 */
public abstract class SRMAbstractMojo extends AbstractMojo implements
		ISRMMojoConstants {

	/**
	 * Using this alternative as project expression does not get injected in
	 * this abstract class of Mojo.
	 * 
	 * @parameter alias="base" expression="${project.basedir}"
	 */
	protected File _base = null;

	/**
	 * @parameter alias="input"
	 */
	protected File _input = null;

	/**
	 * @parameter alias="output"
	 *            expression="${project.build.directory}/srm-generated-sources"
	 */
	protected File _output = null;

	/**
	 * @parameter alias="package"
	 * @required
	 */
	protected String _package = null;

	/**
	 * @parameter alias="overwrite" default-value="true"
	 */
	protected boolean _overwrite = true;

	protected String _lastProduct;

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

	protected void startProduct(String product, CodeGeneratorContext context)
			throws Exception {
		_lastProduct = product;
		context.getLog()
				.message(
						TextUtils.toPrintable("Start producing " + _lastProduct
								+ "..."));
	}

	protected void endProduct(CodeGeneratorContext context) throws Exception {
		context.getLog().message(
				TextUtils.toPrintable(_lastProduct + " finished."));
		_lastProduct = null;
	}

	public void execute() throws MojoExecutionException {
		try {
			CodeGeneratorContext context = new CodeGeneratorContext(
					new BaseNames());
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
			// Test input
			if (_input == null) {
				// Locate a file in the src/main/srm directory
				File dir = new File(_base, DIR_SRM_SRC);
				TreeSet<File> sorted = new TreeSet<File>(
						new Comparator<File>() {
							Collator _collator = Collator
									.getInstance(Locale.US);

							@Override
							public int compare(File o1, File o2) {
								return _collator.compare(o1.getName(),
										o2.getName());
							}
						});
				if (dir.isDirectory()) {
					sorted.addAll(Arrays.asList(dir.listFiles(new FileFilter() {
						@Override
						public boolean accept(File pathname) {
							return pathname.getName().endsWith(".srm")
									&& pathname.isFile();
						}
					})));
				}
				if (sorted.size() > 1) {
					context.getLog()
							.warning(
									TextUtils
											.toPrintable("Multiple .srm files found at "
													+ DIR_SRM_SRC
													+ ". Only first will be kept."));
				}
				if (sorted.size() < 1) {
					throw new MojoFailureException(
							"Could not find any input .srm file in directory "
									+ DIR_SRM_SRC);
				}
				_input = sorted.first();

			}
			if (_input == null) {
				throw new MojoFailureException(
						"Could not find any input .srm file.");
			} else {
				context.getLog().message(
						TextUtils.toPrintable("Loading .srm model file: "
								+ _input.getCanonicalPath()));

			}

			IModel model = null;
			long ts = _input.lastModified();

			// Parse input
			FileInputStream fis = new FileInputStream(_input);
			try {
				model = ParserUtils.parse(fis);
			} finally {
				fis.close();
			}

			context.setOutput(_output);
			context.setTimestamp(ts);
			context.setOverwrite(_overwrite);
			context.setTypes(BasicSetTypesContext.get());

			// Normalize...
			INormalizedModel normalized = Normalizer.normalize(model);
			execute(model, normalized, context);

		} catch (Exception e) {
			throw new MojoExecutionException("Could not generate code", e);
		}

	}

	protected abstract void execute(IModel model, INormalizedModel normalized,
			CodeGeneratorContext context) throws Exception;
}
