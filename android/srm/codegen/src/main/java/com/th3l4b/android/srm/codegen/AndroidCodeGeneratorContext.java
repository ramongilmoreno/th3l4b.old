package com.th3l4b.android.srm.codegen;

import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class AndroidCodeGeneratorContext extends JavaCodeGeneratorContext {

	private AndroidNames _androidNames = new AndroidNames();
	
	public AndroidNames getAndroidNames() {
		return _androidNames;
	}
	
	public void setAndroidNames(AndroidNames androidNames) {
		_androidNames = androidNames;
	}
	
	public void copyTo(AndroidCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setAndroidNames(getAndroidNames());
	}

}
