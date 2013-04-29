package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractModelUtils;

public class ModelUtils extends AbstractModelUtils {

	public ModelUtils() {
		_creators.put(IEntityA.class.getName(), new Creator() {
			@Override
			public Object create() throws Exception {
				return initialize(IEntityA.class, new DefaultEntityA());
			}
		});
		_copiers.put(IEntityA.class.getName(), new Copier<IEntityA>() {
			@Override
			protected void copyEntity(IEntityA source, IEntityA target)
					throws Exception {
				if (source.isSetEntityB()) {
					target.setEntityB(source.getEntityB());
				}
				target.coordinates().setIdentifier(
						source.coordinates().getIdentifier());
				target.coordinates()
						.setStatus(source.coordinates().getStatus());
			}

		});
	}

}
