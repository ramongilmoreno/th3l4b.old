package com.th3l4b.srm.base.original;

import com.th3l4b.common.log.ILogger;
import com.th3l4b.common.text.TextUtils;

public class Validator {
	public static void validate(IModel model, ILogger log) throws Exception {
		for (IRelationship r : model.relationships().items()) {
			if (r.getType() == RelationshipType.manyToOne) {
				// Check that many to may relationships entity name
				// exists.
				String name = r.getName();
				String entity = r.getEntity();
				if (model.entities().contains(name) != null) {
					if (entity == null) {
						log.error(TextUtils
								.toPrintable("A many-to-many relationship has a name that collides with an existing entity. To avoid this error: state explicitly in the relationship (the name after 'many-to-many' token) that the such entity is really ment to hold this relationship. Offending entity name: "
										+ name));
					}
				}
			}
		}
	}
}
