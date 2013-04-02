package com.th3l4b.srm.codegen.java.basicruntime.storage.inmemory;

import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractRuntimeEntity<T extends IRuntimeEntity> implements IRuntimeEntity {
	
	protected IIdentifier _identifier;
	
	private ICoordinates _coordinates;
	
	protected abstract Class<T> getImplementedClass () throws Exception;
	
	@Override
	public ICoordinates coordinates() throws Exception {
		if (_coordinates == null) {
			_coordinates = new ICoordinates () {
				@Override
				public IIdentifier getIdentifier() throws Exception {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void setIdentifier(IIdentifier identifier)
						throws Exception {
					// TODO Auto-generated method stub
					
				}

				@Override
				public EntityStatus getStatus() throws Exception {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void setStatus(EntityStatus status) throws Exception {
					// TODO Auto-generated method stub
					
				}
			};
		}
		return _coordinates;
	}

}
