package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.th3l4b.srm.codegen.java.basicruntime.inmemory.Pair;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractJDBCFinder {

	/**
	 * Maps the relationships of a class with its column name.
	 */
	protected Map<Pair, String> _map = new LinkedHashMap<Pair, String>();

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCIdentifierParser getIdentifierParser()
			throws Exception;

	protected abstract IJDBCStatusParser getStatusParser() throws Exception;

	protected abstract IJDBCRuntimeTypesContext getTypes() throws Exception;

	protected abstract IJDBCEntityParserContext getParsers() throws Exception;

	public <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> Iterable<R> find(
			Class<R> resultClass, Class<S> sourceClass, IIdentifier identifier,
			String relationship) throws Exception {
		String column = _map.get(new Pair(sourceClass, relationship));
		return find(resultClass, identifier, column);
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> find(Class<R> resultClass,
			IIdentifier sourceIdentifier, String relationshipColumnName)
			throws Exception {
		IJDBCEntityParser<R> parser = getParsers().getEntityParser(resultClass);
		StringBuffer query = new StringBuffer("select ");
		JDBCUtils.columnsAndFrom(parser, query);
		query.append(" where ");
		query.append(relationshipColumnName);
		query.append(" = ? and ");
		query.append(parser.statusColumn());
		query.append(" = ?");

		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		getIdentifierParser().set(sourceIdentifier, 1, statement);
		getStatusParser().set(EntityStatus.Persisted, 2, statement);
		ResultSet result = statement.executeQuery();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (result.next()) {
			R entity = parser.parse(1, result);
			r.add(entity);
		}
		result.close();
		statement.close();
		return r;
	}

	public <R extends IRuntimeEntity<R>> R find(Class<R> clazz,
			IIdentifier identifier) throws Exception {
		IJDBCEntityParser<R> parser = getParsers().getEntityParser(clazz);
		StringBuffer query = new StringBuffer("select ");
		JDBCUtils.columnsAndFrom(parser, query);
		query.append(" where ");
		query.append(parser.idColumn());
		query.append(" = ?");
		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		getIdentifierParser().set(identifier, 1, statement);
		ResultSet result = statement.executeQuery();
		R r = null;
		if (result.next()) {
			r = parser.parse(1, result);
		}
		result.close();
		statement.close();
		return r;
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> all(final Class<R> clazz)
			throws Exception {
		IJDBCEntityParser<R> parser = getParsers().getEntityParser(clazz);
		StringBuffer query = new StringBuffer("select ");
		JDBCUtils.columnsAndFrom(parser, query);
		query.append(" where ");
		query.append(parser.statusColumn());
		query.append(" = ?");

		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		getStatusParser().set(EntityStatus.Persisted, 1, statement);
		ResultSet result = statement.executeQuery();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (result.next()) {
			R entity = parser.parse(1, result);
			r.add(entity);
		}
		result.close();
		statement.close();
		return r;
	}
}
