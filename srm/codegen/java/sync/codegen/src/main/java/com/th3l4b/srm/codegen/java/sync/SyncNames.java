package com.th3l4b.srm.codegen.java.sync;

import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;

public class SyncNames {

	private BaseNames _baseNames;

	public SyncNames(BaseNames baseNames) {
		_baseNames = baseNames;
	}

	public String packageForSync(SyncCodeGeneratorContext context) {
		return context.getPackage() + ".sync";
	}

	public String fqnSync(String clazz, SyncCodeGeneratorContext context) {
		return packageForSync(context) + "." + clazz;
	}

	public String packageForDiff(SyncCodeGeneratorContext context) {
		return packageForSync(context) + ".diff";
	}

	public String fqnDiff(String clazz, SyncCodeGeneratorContext context) {
		return packageForDiff(context) + "." + clazz;
	}

	public String diffName(INormalizedEntity entity) throws Exception {
		return _baseNames.name(entity) + "Diff";
	}

	public String diffContextName(INormalizedModel model) throws Exception {
		return "" + _baseNames.name(model) + "DiffContext";
	}

	public String abstractUpdateFilterName(INormalizedModel model)
			throws Exception {
		return "Abstract" + _baseNames.name(model) + "UpdateFilter";
	}
}
