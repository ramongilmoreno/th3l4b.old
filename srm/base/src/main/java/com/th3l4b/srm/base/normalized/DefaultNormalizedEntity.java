package com.th3l4b.srm.base.normalized;

import java.io.PrintWriter;

import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamedContainer;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.original.DefaultEntity;

public class DefaultNormalizedEntity extends DefaultEntity implements
		INormalizedEntity {

	protected DefaultNamedContainer<INormalizedManyToOneRelationship> _relationships = new DefaultNamedContainer<INormalizedManyToOneRelationship>() {
		public String toString() {
			return "Relationships of " + DefaultNormalizedEntity.this;
		}
	};

	@Override
	public INamedContainer<INormalizedManyToOneRelationship> relationships()
			throws Exception {
		return _relationships;
	}

	@Override
	public void print(PrintWriter out) {
		super.print(out);
		PrintWriter iout = IndentedWriter.get(out);
		PrintWriter iiout = IndentedWriter.get(iout);
		iout.println("Relationships:");
		_relationships.print(iiout);
		iiout.flush();
		iout.flush();
	}

}
