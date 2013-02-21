package com.th3l4b.types.base.basicset;

import com.th3l4b.types.base.ITypesContext;

/**
 * Implementation of the basi list of types.
 */
public class TypesSet {
	
	private static ITypesContext _default;

	public static ITypesContext get () {
		if (_default == null) {
			_default = new ITypesContext();
		}
		return _default;
	}
}
