package com.th3l4b.srm.codegen.java.jdbc;

import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class JDBCCodeGeneratorContext extends JavaCodeGeneratorContext {

	private JDBCNames _JDBCNames = new JDBCNames();

	public JDBCNames getJDBCNames() {
		return _JDBCNames;
	}

	public void setJDBCNames(JDBCNames jDBCNames) {
		_JDBCNames = jDBCNames;
	}

	public void copyTo(JDBCCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setJDBCNames(getJDBCNames());
	}

}
