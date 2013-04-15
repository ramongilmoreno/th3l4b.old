package com.th3l4b.srm.codegen.java.basicruntime.junit.basicruntime;

import com.th3l4b.srm.codegen.java.basicruntime.junit.IEntityA;
import com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory.AbstractModelUtils;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public class ModelUtils extends AbstractModelUtils {
	
	public ModelUtils () {
		_creators.put(IEntityA.class.getName(), new Creator() {
			@Override
			public Object create() throws Exception {
				return new DefaultEntityA();
			}
		});
		_copiers.put(IEntityA.class.getName(), new Copier<IEntityA>() {
			@Override
			protected void copyEntity(IEntityA source, IEntityA target)
					throws Exception {
				target.setEntityA(source.getEntityA());
			}
			
		});
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T2 extends IRuntimeEntity<T2>> T2 create(Class<T2> clazz)
			throws Exception {
		Creator creator = _creators.get(clazz.getName());
		if (creator == null) {
			throw new IllegalArgumentException("Unknown class: " + clazz.getName());
		}
		return (T2) creator.create();
	}
	
	@Override
	public <T2 extends IRuntimeEntity<T2>> void copy(T2 source, T2 target,
			Class<T2> clazz) throws Exception {
		_copiers.get(clazz.getName()).copy(source, target);
	}

}
