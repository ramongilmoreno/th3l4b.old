package com.th3l4b.srm.codegen.java.basicruntime.rest.junit;

import com.th3l4b.srm.codegen.java.basicruntime.DefaultIdentifier;
import com.th3l4b.srm.codegen.java.basicruntime.rest.DefaultRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IFindAllForRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IFindManyForRESTFinder;
import com.th3l4b.srm.codegen.java.basicruntime.rest.IFindOneForRESTFinder;

public class SampleRESTFinder extends DefaultRESTFinder<ISampleFinder> {
	public SampleRESTFinder() throws Exception {
		// All
		register(new IFindAllForRESTFinder<ISampleEntityA, ISampleFinder>() {
			public Iterable<ISampleEntityA> all(ISampleFinder finder) throws Exception {
				return finder.all(ISampleEntityA.class);
			}
		}, "Sample entity A");

		// One
		register(new IFindOneForRESTFinder<ISampleEntityA, ISampleFinder>() {
			public ISampleEntityA find(String id, ISampleFinder finder) throws Exception {
				return finder.getEntity(new DefaultIdentifier(ISampleEntityA.class, id));
			}
		}, "Sample entity A");
		
		// Many
		register(new IFindManyForRESTFinder<ISampleEntityB, ISampleFinder>() {
			public Iterable<ISampleEntityB> many(String id, ISampleFinder finder) throws Exception {
				return finder.findAllSampleEntityBFromEntityA(new DefaultIdentifier(ISampleEntityA.class, id));
			}
		}, "Sample entity B", "Sample entity B (relationship)");
		
	}
}
