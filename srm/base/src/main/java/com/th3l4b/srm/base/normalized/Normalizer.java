package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.ModelUtils;
import com.th3l4b.srm.base.original.IEntity;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.base.original.IRelationship;
import com.th3l4b.srm.base.original.RelationshipType;

public class Normalizer {
	
	protected static void transferProperties(IPropertied source, IPropertied target) throws Exception {
		target.getProperties().putAll(source.getProperties());
	}

	public static INormalizedModel normalize(IModel original) throws Exception {
		DefaultNormalizedModel normal = new DefaultNormalizedModel();
		transferProperties(original, normal);

		// First compose all required entities
		for (IEntity e : original.entities().items()) {
			DefaultNormalizedEntity ne = new DefaultNormalizedEntity();
			transferProperties(e, ne);
			normal.add(ne);
		}

		// Iterate relationship to find if any many-to-many requires a new
		// entity
		for (IRelationship r : original.relationships().items()) {
			if (r.getType() == RelationshipType.manyToMany) {
				String entity = r.getEntity();
				if (entity == null) {
					entity = ModelUtils.getRelationshipName(r);
					if (normal.get(entity) != null) {
						throw new IllegalArgumentException(
								"There is already an entity in the model with the same name as the automatic entity for the many-to-many relationship: "
										+ entity);
					}
				}

				if (normal.get(entity) == null) {
					// Create a new entity
					DefaultNormalizedEntity ne = new DefaultNormalizedEntity();
					ne.setName(entity);
					normal.add(ne);
				}
			}
		}

		return normal;
	}
}
