package com.th3l4b.srm.runtime.update;

import com.th3l4b.common.log.ILogger;
import com.th3l4b.srm.runtime.IRuntimeEntity;

public interface IListener {
	<T extends IRuntimeEntity> void created (T entity, ILogger log) throws Exception;
	<T extends IRuntimeEntity> void deleted (T entity, ILogger log) throws Exception;
	<T extends IRuntimeEntity> void updatedField (T modifications, T old, String field, ILogger log) throws Exception;
	<T extends IRuntimeEntity> void updatedRelationship (T modifications, T old, String relationship, ILogger log) throws Exception;
}
