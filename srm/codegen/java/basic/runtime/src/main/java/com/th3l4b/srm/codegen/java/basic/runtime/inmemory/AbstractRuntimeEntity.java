package com.th3l4b.srm.codegen.java.basic.runtime.inmemory;

import com.th3l4b.srm.codegen.java.basic.runtime.DefaultIdentifier;
import com.th3l4b.srm.runtime.EntityStatus;
import com.th3l4b.srm.runtime.ICoordinates;
import com.th3l4b.srm.runtime.IIdentifier;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public abstract class AbstractRuntimeEntity<T extends IRuntimeEntity<T>>
		implements IRuntimeEntity<T> {

	protected IIdentifier __identifier = new DefaultIdentifier(clazz());
	protected EntityStatus __status = EntityStatus.Modify;
	private ICoordinates _coordinates;

	@Override
	public ICoordinates coordinates() throws Exception {
		if (_coordinates == null) {
			_coordinates = new ICoordinates() {
				@Override
				public IIdentifier getIdentifier() throws Exception {
					return __identifier;
				}

				@Override
				public void setIdentifier(IIdentifier identifier)
						throws Exception {
					__identifier = identifier;
				}

				@Override
				public EntityStatus getStatus() throws Exception {
					return __status;
				}

				@Override
				public void setStatus(EntityStatus status) throws Exception {
					__status = status;
				}
			};
		}
		return _coordinates;
	}

}
