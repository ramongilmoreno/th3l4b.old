package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCFinder {

	protected Map<String, IJDBCEntityParser<?>> _parsers = new LinkedHashMap<String, IJDBCEntityParser<?>>();

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCIdentifierParser getIdentifierParser()
			throws Exception;

	@SuppressWarnings("unchecked")
	protected <R extends IRuntimeEntity<R>> IJDBCEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception {
		return (IJDBCEntityParser<R>) _parsers.get(clazz.getName());
	}

	public <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> Iterable<R> find(
			Class<R> resultClass, IIdentifier sourceIdentifier,
			String relationshipColumnName) throws Exception {
		IJDBCEntityParser<R> parser = getEntityParser(resultClass);
		StringBuffer query = new StringBuffer("select ");
		boolean first = true;
		for (String col : parser.columns("a")) {
			if (first) {
				first = false;
			} else {
				query.append(", ");
			}
			query.append(col);
		}
		query.append(" from " + parser.table() + " a ");
		query.append(" where a." + relationshipColumnName + " = ?");
		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		IJDBCIdentifierParser ids = getIdentifierParser();
		ids.set(sourceIdentifier, 1, statement);
		ResultSet result = statement.executeQuery();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (result.next()) {
			r.add(parser.parse("a", result, ids));
		}
		result.close();
		statement.close();
		return r;

	}

	public <R extends IRuntimeEntity<R>> R find(Class<R> clazz,
			IIdentifier identifier) throws Exception {
		IJDBCEntityParser<R> parser = getEntityParser(clazz);
		PreparedStatement statement = getConnection().prepareStatement(
				"select " + parser.idColumn("a") + " from " + parser.table()
						+ " a where " + parser.idColumn("a") + " = ?");
		IJDBCIdentifierParser ids = getIdentifierParser();
		ids.set(identifier, 1, statement);
		ResultSet result = statement.executeQuery();
		R r = null;
		if (result.next()) {
			r = parser.parse("a", result, ids);
		}
		result.close();
		statement.close();
		return r;
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> all(final Class<R> clazz)
			throws Exception {
		IJDBCEntityParser<R> parser = getEntityParser(clazz);
		PreparedStatement statement = getConnection().prepareStatement(
				"select " + parser.idColumn("a") + " from " + parser.table()
						+ " a");
		IJDBCIdentifierParser ids = getIdentifierParser();
		ResultSet result = statement.executeQuery();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (result.next()) {
			r.add(parser.parse("a", result, ids));
		}
		result.close();
		statement.close();
		return r;
	}

}
