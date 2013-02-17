package com.th3l4b.srm.base.normalized;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.INamed;
import com.th3l4b.common.named.NamedUtils;
import com.th3l4b.common.propertied.IPropertied;
import com.th3l4b.srm.base.DefaultField;
import com.th3l4b.srm.base.IField;
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
			
			// Transfer fields
			for (IField f: e.items()) {
				DefaultField nf = new DefaultField();
				transferProperties(f,  nf);
				ne.add(nf);
			}
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

				// Find entity for this many-to-many relationship
				INormalizedEntity e = normal.get(entity);
				if (e == null) {
					// If many-to-many entity does not exist, create a new entity
					e = new DefaultNormalizedEntity();
					e.setName(entity);
					normal.add(e);
				}
				
				// Attach many-to-one relationships to sources
				addNormalizedRelationshipFromRelationsip(r, e, true);
				addNormalizedRelationshipFromRelationsip(r, e, false);
				
			}
		}

		return normal;
	}

	private static void addNormalizedRelationshipFromRelationsip(
			IRelationship r, INormalizedEntity e, boolean from) throws Exception {
		DefaultNormalizedManyToOneRelationship nr = new DefaultNormalizedManyToOneRelationship();
		nr.setTo(from ? r.getFrom() : r.getTo());
		INamed direct = from ? r.getReverse() : r.getDirect();
		if (direct != null) {
			DefaultNamed n = new DefaultNamed();
			transferProperties(direct, n);
			nr.setDirect(n);
		}
		
		INamed reverse = from ? r.getDirect() : r.getReverse();
		if (reverse != null) {
			DefaultNamed n = new DefaultNamed();
			transferProperties(reverse, n);
			nr.setReverse(n);
		}
		
		// Compute name, either name from di
		String name = NamedUtils.NAME_GETTER.get(direct);
		if (name == null) {
			name = nr.getTo();
		}
		nr.setName(name);
		e.relationships().add(nr);
	}
}
