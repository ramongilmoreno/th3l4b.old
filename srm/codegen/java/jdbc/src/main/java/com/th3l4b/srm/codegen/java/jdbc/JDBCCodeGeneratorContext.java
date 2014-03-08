package com.th3l4b.srm.codegen.java.jdbc;

import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.database.SQLNames;
import com.th3l4b.srm.codegen.java.basic.JavaCodeGeneratorContext;

public class JDBCCodeGeneratorContext extends JavaCodeGeneratorContext {

	private JDBCNames _JDBCNames;
	private SQLNames _SQLNames;

	public JDBCCodeGeneratorContext(BaseNames baseNames) {
		super(baseNames);
		_JDBCNames = new JDBCNames(baseNames);
		_SQLNames = new SQLNames(baseNames);
	}

	public JDBCNames getJDBCNames() {
		return _JDBCNames;
	}

	public void setJDBCNames(JDBCNames jDBCNames) {
		_JDBCNames = jDBCNames;
	}

	public SQLNames getSQLNames() {
		return _SQLNames;
	}

	public void setSQLNames(SQLNames sQLNames) {
		_SQLNames = sQLNames;
	}

	public void copyTo(JDBCCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setJDBCNames(getJDBCNames());
		to.setSQLNames(getSQLNames());
	}

}
