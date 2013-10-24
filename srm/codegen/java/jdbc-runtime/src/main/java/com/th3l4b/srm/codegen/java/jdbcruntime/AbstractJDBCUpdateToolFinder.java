package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.update.IUpdateToolFinder;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCUpdateToolFinder implements IUpdateToolFinder {

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCEntityParserContext getParsers() throws Exception;

	protected abstract IJDBCIdentifierParser getIdentifierParser()
			throws Exception;

	@Override
	public Map<IIdentifier, IRuntimeEntity<?>> find(
			Map<IIdentifier, IRuntimeEntity<?>> input, IModelUtils utils)
			throws Exception {
		LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r = new LinkedHashMap<IIdentifier, IRuntimeEntity<?>>();
		for (IRuntimeEntity<?> entity : input.values()) {
			processEntity(entity, r, utils);

		}
		return r;
	}

	private <T extends IRuntimeEntity<T>> void processEntity(
			IRuntimeEntity<T> entity,
			LinkedHashMap<IIdentifier, IRuntimeEntity<?>> r, IModelUtils utils)
			throws Exception {
		IJDBCEntityParser<T> parser = getParsers().getEntityParser(
				entity.clazz());
		StringBuffer query = new StringBuffer("select ");
		JDBCUtils.columnsAndFrom(parser, query);
		query.append(" where ");
		query.append(parser.idColumn());
		query.append(" = ?");
		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		try {
			getIdentifierParser().set(entity.coordinates().getIdentifier(), 1,
					statement);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				T candidate = parser.create(entity.clazz());
				parser.parse(candidate, 1, result);
				r.put(candidate.coordinates().getIdentifier(), candidate);
				return;
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

	}
}
