package com.th3l4b.srm.codegen.database;

import com.th3l4b.srm.codegen.base.CodeGeneratorContext;
import com.th3l4b.srm.database.BasicSetDatabaseTypesContext;
import com.th3l4b.srm.database.IDatabaseTypesContext;
import com.th3l4b.types.base.IType;
import com.th3l4b.types.base.basicset.BasicSetTypesContext;
import com.th3l4b.types.base.basicset.BasicSetTypesEnum;

public class SQLCodeGeneratorContext extends CodeGeneratorContext {

	private IDatabaseTypesContext _databaseTypes = BasicSetDatabaseTypesContext
			.get();

	private IType _identifierType;
	private IType _statusType;

	private SQLNames _SQLNames = new SQLNames();

	public SQLCodeGeneratorContext() throws Exception {
		setIdentifierType(BasicSetTypesContext.get().get(
				BasicSetTypesEnum._label.getName()));
		setStatusType(BasicSetTypesContext.get().get(
				BasicSetTypesEnum._label.getName()));
	}

	public IDatabaseTypesContext getDatabaseTypes() {
		return _databaseTypes;
	}

	public void setDatabaseTypes(IDatabaseTypesContext databaseTypes) {
		_databaseTypes = databaseTypes;
	}

	public IType getIdentifierType() {
		return _identifierType;
	}

	public void setIdentifierType(IType identifierType) {
		_identifierType = identifierType;
	}

	public IType getStatusType() {
		return _statusType;
	}

	public void setStatusType(IType statusType) {
		_statusType = statusType;
	}

	public SQLNames getSQLNames() {
		return _SQLNames;
	}

	public void setSQLNames(SQLNames sQLNames) {
		_SQLNames = sQLNames;
	}

	public void copyTo(SQLCodeGeneratorContext to) throws Exception {
		super.copyTo(to);
		to.setDatabaseTypes(getDatabaseTypes());
		to.setSQLNames(getSQLNames());
	}

}
