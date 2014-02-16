package com.th3l4b.srm.codegen.mojo;

public interface ISRMMojoConstants {
	static final String DIR_SRM_SRC = "src/main/srm";
	static final String ARTIFACT_SRM = "srm";
	static final String ARTIFACT_SRM_PROPERTIES = "srm-properties";
	static final String PROPERTY_PREFIX = ISRMMojoConstants.class.getPackage()
			.getName();
	static final String PROPERTY_PACKAGE = PROPERTY_PREFIX + ".package";
}
