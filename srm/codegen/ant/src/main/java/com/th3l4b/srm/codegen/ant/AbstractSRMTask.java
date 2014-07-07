package com.th3l4b.srm.codegen.ant;

import java.io.File;
import java.io.FileInputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

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

public abstract class AbstractSRMTask extends Task {

	private File _input;
	private File _output;
	private Boolean _overwrite = Boolean.FALSE;
	private String _package;
	private String _lastProduct;

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

	public Boolean getOverwrite() {
		return _overwrite;
	}

	public void setOverwrite(Boolean overwrite) {
		_overwrite = overwrite;
	}

	public String getPackage() {
		return _package;
	}

	public void setPackage(String package1) {
		_package = package1;
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

	@Override
	public void execute() throws BuildException {

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

					switch (level) {
					case debug:
						AbstractSRMTask.this.log(text, Project.MSG_DEBUG);
						break;
					case message:
						AbstractSRMTask.this.log(text, Project.MSG_INFO);
						break;
					case warning:
						AbstractSRMTask.this.log(text, Project.MSG_WARN);
						break;
					default:
						AbstractSRMTask.this.log(text, Project.MSG_ERR);
						break;
					}
				}
			});
			if (_input == null) {
				throw new Exception("Could not find any input .srm file.");
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
			throw new BuildException("Could not generate code", e);
		}
	}

	protected abstract void execute(IModel model, INormalizedModel normalized,
			CodeGeneratorContext context) throws Exception;

}
