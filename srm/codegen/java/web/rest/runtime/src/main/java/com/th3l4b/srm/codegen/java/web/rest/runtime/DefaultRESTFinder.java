package com.th3l4b.srm.codegen.java.web.rest.runtime;

import java.util.LinkedHashMap;
import java.util.Map;

import com.th3l4b.common.data.Pair;
import com.th3l4b.srm.runtime.IFinder;
import com.th3l4b.srm.runtime.IRuntimeEntity;

/**
 * Populate finders in constructor.
 */
public class DefaultRESTFinder<F extends IFinder> implements IRESTFinder<F> {

	Map<String, IFindAllForRESTFinder<? extends IRuntimeEntity<?>, F>> _all = new LinkedHashMap<String, IFindAllForRESTFinder<? extends IRuntimeEntity<?>, F>>();
	Map<String, IFindOneForRESTFinder<? extends IRuntimeEntity<?>, F>> _one = new LinkedHashMap<String, IFindOneForRESTFinder<? extends IRuntimeEntity<?>, F>>();
	Map<Pair<String, String>, IFindManyForRESTFinder<? extends IRuntimeEntity<?>, F>> _many = new LinkedHashMap<Pair<String, String>, IFindManyForRESTFinder<? extends IRuntimeEntity<?>, F>>();

	protected <T extends IRuntimeEntity<?>> void register(
			IFindAllForRESTFinder<T, F> finder, String entity) throws Exception {
		_all.put(entity, finder);
	}

	protected <T extends IRuntimeEntity<?>> void register(
			IFindOneForRESTFinder<T, F> finder, String entity) throws Exception {
		_one.put(entity, finder);
	}

	protected <T extends IRuntimeEntity<?>> void register(
			IFindManyForRESTFinder<T, F> finder, String entity,
			String relationship) throws Exception {
		_many.put(new Pair<String, String>(entity, relationship), finder);
	}

	@Override
	public Iterable<? extends IRuntimeEntity<?>> all(String type, F finder)
			throws Exception {
		return _all.get(type).all(finder);
	}

	@Override
	public IRuntimeEntity<?> get(String type, String id, F finder)
			throws Exception {
		return _one.get(type).find(id, finder);
	}

	@Override
	public Iterable<? extends IRuntimeEntity<?>> get(String entity, String id,
			String relationship, F finder) throws Exception {
		return _many.get(new Pair<String, String>(entity, relationship)).many(id,
				finder);
	}

}
