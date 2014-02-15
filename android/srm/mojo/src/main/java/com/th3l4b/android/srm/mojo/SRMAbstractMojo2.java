package com.th3l4b.android.srm.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import com.th3l4b.common.log.AbstractLog;
import com.th3l4b.common.log.ILogLevel;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.TextUtils;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.parser.ParserUtils;
import com.th3l4b.types.base.basicset.BasicSetTypesContext;

/**
 * Base for other mojos.
 */
public abstract class SRMAbstractMojo2 extends AbstractMojo {

	/**
	 * @parameter alias="srmGroupId"
	 * @required
	 */
	protected String _srmGroupId = null;

	/**
	 * @parameter alias="srmArtifactId"
	 * @required
	 */
	protected String _srmArtifactId = null;

	/**
	 * @parameter alias="srmVersion"
	 * @required
	 */
	protected String _srmVersion = null;

	/**
	 * @parameter alias="output"
	 *            expression="${project.build.directory}/srm-android-generated-sources"
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

	/**
	 * Used to look up Artifacts in the remote repository.
	 * 
	 * @parameter expression=
	 *            "${component.org.apache.maven.artifact.factory.ArtifactFactory}"
	 * @required
	 */
	protected ArtifactFactory _factory;

	/**
	 * Used to look up Artifacts in the remote repository.
	 * 
	 * @parameter expression=
	 *            "${component.org.apache.maven.artifact.resolver.ArtifactResolver}"
	 * @required
	 */
	protected ArtifactResolver _artifactResolver;

	/**
	 * List of Remote Repositories used by the resolver
	 * 
	 * @parameter expression="${project.remoteArtifactRepositories}"
	 * @required
	 */
	protected List<?> _remoteRepositories;

	/**
	 * Location of the local repository.
	 * 
	 * @parameter expression="${localRepository}"
	 * @required
	 */
	protected ArtifactRepository _localRepository;

	protected File _input = null;

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
			// Setup context
			CodeGeneratorContext context = new CodeGeneratorContext();
			context.setOutput(_output);
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

			// Finding source srm by resolving artifact in dependency
			context.getLog().message(
					TextUtils.toPrintable("Resolving srm for " + _srmGroupId
							+ ":" + _srmArtifactId + " " + _srmVersion));
			// http://stackoverflow.com/questions/1440224/how-can-i-download-maven-artifacts-within-a-plugin
			Artifact artifact = this._factory.createArtifact(_srmGroupId,
					_srmArtifactId, _srmVersion, "", "srm");
			_artifactResolver.resolve(artifact, this._remoteRepositories,
					this._localRepository);
			context.getLog()
					.message(
							TextUtils.toPrintable("Found srm at: "
									+ artifact.getFile()));
			setInput(artifact.getFile());

			// Setup timestamp
			long ts = _input.lastModified();
			context.setTimestamp(ts);

			// Parse input
			IModel model = null;
			FileInputStream fis = new FileInputStream(_input);
			try {
				model = ParserUtils.parse(fis);
			} finally {
				fis.close();
			}

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
