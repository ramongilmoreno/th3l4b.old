package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.th3l4b.srm.codegen.java.basicruntime.update.AbstractUpdateToolUpdater;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCUpdateToolUpdater extends
		AbstractUpdateToolUpdater {

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCEntityParserContext getParsers() throws Exception;

	protected abstract IJDBCIdentifierParser getIdentifierParser()
			throws Exception;

	@Override
	protected <T extends IRuntimeEntity<T>> void updateEntity(T entity,
			T original, IModelUtils utils) throws Exception {
		if (entity.coordinates().getStatus() != EntityStatus.Deleted) {
			entity.coordinates().setStatus(EntityStatus.Persisted);
		}

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
			parser.set(entity, 1, statement);
			getIdentifierParser().set(entity.coordinates().getIdentifier(),
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
