package com.th3l4b.srm.codegen.java.sync.runtime;

public interface IEntityDiff<T> {
	/**
	 * @param from Original entity status
	 * @param to Desired entity status
	 * @param diff Delta modification to get from from to to
	 * @return True if any difference is found
	 */
	boolean diff (T from, T to, T diff) throws Exception;
}
