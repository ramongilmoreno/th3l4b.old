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

	protected static void transferProperties(IPropertied source,
			IPropertied target) throws Exception {
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
			for (IField f : e.items()) {
				DefaultField nf = new DefaultField();
				transferProperties(f, nf);
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
					if (normal.contains(entity) != null) {
						throw new IllegalArgumentException(
								"There is already an entity in the model with the same name as the automatic entity for the many-to-many relationship: "
										+ entity);
					}
				}

				// Find entity for this many-to-many relationship
				INormalizedEntity e = normal.contains(entity);
				if (e == null) {
					// If many-to-many entity does not exist, create a new
					// entity
					e = new DefaultNormalizedEntity();
					e.setName(entity);
					normal.add(e);
				}

				// Attach many-to-one relationships to sources
				addNormalizedRelationshipFromRelationsip(r, e, true);
				addNormalizedRelationshipFromRelationsip(r, e, false);

			} else {
				// Locate source
				INormalizedEntity e = normal.get(r.getFrom());

				// It is a many to one
				DefaultNormalizedManyToOneRelationship nr = new DefaultNormalizedManyToOneRelationship();
				transferProperties(r, nr);
				nr.setTo(r.getTo());

				// Clone direct and reverse
				nr.setDirect(cloneINamed(r.getDirect()));
				nr.setReverse(cloneINamed(r.getReverse()));

				applyName(nr);
				e.relationships().add(nr);
			}
		}

		return normal;
	}

	private static INamed cloneINamed(INamed src) throws Exception {
		if (src == null) {
			return src;
		} else {
			DefaultNamed r = new DefaultNamed();
			transferProperties(src, r);
			return r;
		}
	}

	private static void addNormalizedRelationshipFromRelationsip(
			IRelationship r, INormalizedEntity e, boolean from)
			throws Exception {
		DefaultNormalizedManyToOneRelationship nr = new DefaultNormalizedManyToOneRelationship();
		nr.setTo(from ? r.getFrom() : r.getTo());
		INamed direct = from ? r.getReverse() : r.getDirect();
		if (direct != null) {
			nr.setDirect(cloneINamed(direct));
		} else {
			nr.setDirect(new DefaultNamed(nr.getTo()));
		}
		
		INamed reverse = from ? r.getDirect() : r.getReverse();
		if (reverse != null) {
			nr.setReverse(cloneINamed(reverse));
		} else {
			nr.setDirect(new DefaultNamed(r.getEntity()));
		}
		if (nr.getReverse() == null) {
			String to = from ? r.getTo() : r.getFrom();
			nr.setReverse(new DefaultNamed(to));
		}

		applyName(nr);
		e.relationships().add(nr);
	}

	private static void applyName(INormalizedManyToOneRelationship relationship)
			throws Exception {
		// Compute name, either name or from direct
		INamed direct = relationship.getDirect();
		String name = NamedUtils.NAME_GETTER.get(direct);
		if (name == null) {
			name = relationship.getTo();
		}
		relationship.setName(name);
	}
}
