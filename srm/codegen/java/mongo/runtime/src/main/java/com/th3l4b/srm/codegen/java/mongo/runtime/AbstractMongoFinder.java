package com.th3l4b.srm.codegen.java.mongo.runtime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.basic.runtime.inmemory.Pair;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractMongoFinder {

	/**
	 * Maps the relationships of a class with its column name.
	 */
	protected Map<Pair, String> _map = new LinkedHashMap<Pair, String>();

	protected abstract DB getDB() throws Exception;

	protected abstract IMongoIdentifierParser getIdentifierParser()
			throws Exception;

	protected abstract IMongoStatusParser getStatusParser() throws Exception;

	protected abstract IMongoRuntimeTypesContext getTypes() throws Exception;

	protected abstract IMongoEntityParserContext getParsers() throws Exception;

	public <R extends IRuntimeEntity<R>, S extends IRuntimeEntity<S>> Iterable<R> find(
			Class<R> resultClass, Class<S> sourceClass, IIdentifier identifier,
			String relationship) throws Exception {
		DefaultIdentifier.checkIdentifierType(identifier, sourceClass);
		String column = _map.get(new Pair(sourceClass, relationship));
		return find(resultClass, identifier, column);
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> find(Class<R> resultClass,
			IIdentifier sourceIdentifier, String relationshipColumnName)
			throws Exception {
		IMongoEntityParser<R> parser = getParsers()
				.getEntityParser(resultClass);
		String table = parser.table();
		DBObject q = new BasicDBObject();
		getIdentifierParser().set(sourceIdentifier, relationshipColumnName, q);
		getStatusParser().set(EntityStatus.Persisted, parser.statusColumn(), q);
		DBCursor cursor = getDB().getCollection(table).find(q);
		try {
			LinkedHashSet<R> r = new LinkedHashSet<R>();
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				r.add(parser.parse(null, next));
			}
			return r;

		} finally {
			cursor.close();
		}
	}

	public <R extends IRuntimeEntity<R>> R find(Class<R> clazz,
			IIdentifier identifier) throws Exception {
		DefaultIdentifier.checkIdentifierType(identifier, clazz);
		IMongoEntityParser<R> parser = getParsers().getEntityParser(clazz);
		DBObject q = new BasicDBObject();
		getIdentifierParser().set(identifier, parser.idColumn(), q);
		DBCursor cursor = getDB().getCollection(parser.table()).find(q);
		try {
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				return parser.parse(null, next);
			}

			// If item is not found, return an unknown one.
			R r = parser.create();
			ICoordinates coordinates = r.coordinates();
			coordinates.setIdentifier(identifier);
			coordinates.setStatus(EntityStatus.Unknown);
			return r;
		} finally {
			cursor.close();
		}
	}

	public <R extends IRuntimeEntity<R>> Iterable<R> all(final Class<R> clazz)
			throws Exception {
		IMongoEntityParser<R> parser = getParsers().getEntityParser(clazz);
		DBObject o = new BasicDBObject();
		getStatusParser().set(EntityStatus.Persisted, parser.statusColumn(), o);
		DBCursor cursor = getDB().getCollection(parser.table()).find(o);
		try {
			LinkedHashSet<R> r = new LinkedHashSet<R>();
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				r.add(parser.parse(null, next));
			}
			return r;
		} finally {
			cursor.close();
		}
	}

	public Iterable<IRuntimeEntity<?>> backup() throws Exception {
		ArrayList<IRuntimeEntity<?>> r = new ArrayList<IRuntimeEntity<?>>();
		for (IMongoEntityParser<?> parser : getParsers().all()) {
			DBCursor cursor = getDB().getCollection(parser.table()).find();
			try {
				while (cursor.hasNext()) {
					DBObject next = cursor.next();
					r.add((IRuntimeEntity<?>) parser.parse(null, next));
				}
			} finally {
				cursor.close();
			}
		}
		return r;
	}
}
