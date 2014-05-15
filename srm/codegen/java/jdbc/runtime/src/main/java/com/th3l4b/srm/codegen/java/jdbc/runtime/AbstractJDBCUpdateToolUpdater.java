package com.th3l4b.srm.codegen.java.jdbc.runtime;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCUpdateToolUpdater extends
		AbstractUpdateToolUpdater {

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCEntityParserContext getParsers() throws Exception;

	protected abstract IJDBCIdentifierParser getIdentifierParser()
			throws Exception;
	
	protected abstract IModelUtils getModelUtils () throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void updateEntity(T entity,
			T original, IModelUtils utils) throws Exception {
		IJDBCEntityParser<T> parser = getParsers().getEntityParser(
				entity.clazz());
		if (original == null) {
			StringBuffer insert = new StringBuffer("insert into ");
			insert.append(parser.table());
			insert.append("(");
			boolean first = true;
			for (String column : parser.allColumns()) {
				if (first) {
					first = false;
				} else {
					insert.append(", ");
				}
				insert.append(column);
			}
			insert.append(") values (");
			for (int i = 0; i < parser.allColumns().length; i++) {
				if (i == 0) {
					first = false;
				} else {
					insert.append(", ");
				}
				insert.append("?");
			}
			insert.append(")");
			PreparedStatement statement = getConnection().prepareStatement(
					insert.toString());
			parser.set(entity, 1, statement);
			try {
				if (statement.executeUpdate() != 1) {
					throw new AnErrorJDBCDatabaseOperationException();
				}
			} finally {
				if (statement != null) {
					statement.close();
				}
			}
		} else {
			// Merge the two items
			T update2 = parser.create();
			getModelUtils().copy(original, update2, entity.clazz());
			getModelUtils().copy(entity, update2, entity.clazz());
			
			StringBuffer update = new StringBuffer("update ");
			update.append(parser.table());
			update.append(" set ");
			boolean first = true;
			for (String column : parser.allColumns()) {
				if (first) {
					first = false;
				} else {
					update.append(", ");
				}
				update.append(column + " = ?");
			}
			update.append(" where " + parser.idColumn() + " = ?");
			PreparedStatement statement = getConnection().prepareStatement(
					update.toString());
			parser.set(update2, 1, statement);
			getIdentifierParser().set(update2.coordinates().getIdentifier(),
					1 + parser.allColumns().length, statement);
			try {
				if (statement.executeUpdate() != 1) {
					throw new AnErrorJDBCDatabaseOperationException();
				}
			} finally {
				if (statement != null) {
					statement.close();
				}
			}
		}
	}
}
