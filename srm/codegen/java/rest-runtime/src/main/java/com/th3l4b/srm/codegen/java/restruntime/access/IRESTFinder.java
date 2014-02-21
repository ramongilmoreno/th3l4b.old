package com.th3l4b.srm.codegen.java.restruntime.access;

import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IRESTFinder {
	Iterable<IRuntimeEntity<?>> get(String type);
	IRuntimeEntity<?> get(String type, String id);
	Iterable<IRuntimeEntity<?>> get(String type, String id, String relationship);
}
