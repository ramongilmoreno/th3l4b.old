package com.th3l4b.srm.codegen.java.jdbcruntime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;
import com.th3l4b.types.runtime.IJavaRuntimeTypesContext;

public abstract class AbstractJDBCFinder {

	protected Map<String, IJDBCEntityParser<?>> _parsers = new LinkedHashMap<String, IJDBCEntityParser<?>>();

	protected abstract Connection getConnection() throws Exception;

	protected abstract IJDBCIdentifierParser getIdentifierParser()
			throws Exception;

	protected abstract IJavaRuntimeTypesContext getTypes() throws Exception;

	@SuppressWarnings("unchecked")
	protected <R extends IRuntimeEntity<R>> IJDBCEntityParser<R> getEntityParser(
			Class<R> clazz) throws Exception {
		return (IJDBCEntityParser<R>) _parsers.get(clazz.getName());
	}

	protected <R extends IRuntimeEntity<R>> void putEntityParser(
			IJDBCEntityParser<R> parser, Class<R> clazz) throws Exception {
		_parsers.put(clazz.getName(), parser);
	}

	public <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> Iterable<R> find(
			Class<R> resultClass, IIdentifier sourceIdentifier,
			String relationshipColumnName) throws Exception {
		IJDBCEntityParser<R> parser = getEntityParser(resultClass);
		StringBuffer query = new StringBuffer("select ");
		query.append(parser.idColumn());
		for (String col : parser.fieldsColumns()) {
			query.append(", ");
			query.append(col);
		}
		query.append(" from " + parser.table());
		query.append(" where ");
		query.append(relationshipColumnName);
		query.append(" = ? and ");
		query.append(parser.statusColumn());
		query.append(" = ?");
		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		IJDBCIdentifierParser ids = getIdentifierParser();
		IJavaRuntimeTypesContext types = getTypes();
		ids.set(sourceIdentifier, 1, statement);
		statement.setString(2, EntityStatus.Persisted.initial());
		ResultSet result = statement.executeQuery();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (result.next()) {
			R entity = parser.create(resultClass);
			entity.coordinates().setIdentifier(ids.parse(1, result));
			entity.coordinates().setStatus(EntityStatus.Persisted);
			parser.parse(entity, 3, result, ids, types);
			r.add(entity);
		}
		result.close();
		statement.close();
		return r;

	}

	public <R extends IRuntimeEntity<R>> R find(Class<R> clazz,
			IIdentifier identifier) throws Exception {
		IJDBCEntityParser<R> parser = getEntityParser(clazz);
		StringBuffer query = new StringBuffer("select ");
		query.append(parser.idColumn());
		query.append(", ");
		query.append(parser.statusColumn());
		for (String col : parser.fieldsColumns()) {
			query.append(", ");
			query.append(col);
		}
		query.append(" from ");
		query.append(parser.table());
		query.append(" where ");
		query.append(parser.idColumn());
		query.append(" = ?");
		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		IJDBCIdentifierParser ids = getIdentifierParser();
		IJavaRuntimeTypesContext types = getTypes();
		ids.set(identifier, 1, statement);
		ResultSet result = statement.executeQuery();
		R r = null;
		if (result.next()) {
			r = parser.create(clazz);
			r.coordinates().setIdentifier(ids.parse(1, result));
			EntityStatus status = EntityStatus.fromInitial(result.getString(2),
					EntityStatus.Unknown);
			r.coordinates().setStatus(status);
			parser.parse(r, 3, result, ids, types);
		}
		result.close();
		statement.close();
		return r;
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> all(final Class<R> clazz)
			throws Exception {
		IJDBCEntityParser<R> parser = getEntityParser(clazz);
		StringBuffer query = new StringBuffer("select ");
		query.append(parser.idColumn());
		for (String col : parser.fieldsColumns()) {
			query.append(", ");
			query.append(col);
		}
		query.append(" from ");
		query.append(parser.table());
		query.append(" where ");
		query.append(parser.statusColumn());
		query.append(" = ?");
		PreparedStatement statement = getConnection().prepareStatement(
				query.toString());
		IJDBCIdentifierParser ids = getIdentifierParser();
		IJavaRuntimeTypesContext types = getTypes();
		statement.setString(1, EntityStatus.Persisted.initial());
		ResultSet result = statement.executeQuery();
		LinkedHashSet<R> r = new LinkedHashSet<R>();
		while (result.next()) {
			R entity = parser.create(clazz);
			entity.coordinates().setIdentifier(ids.parse(1, result));
			entity.coordinates().setStatus(EntityStatus.Persisted);
			parser.parse(entity, 2, result, ids, types);
			r.add(entity);
		}
		result.close();
		statement.close();
		return r;
	}
}
