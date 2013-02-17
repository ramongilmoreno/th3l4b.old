package com.th3l4b.srm.base.original;

import java.io.PrintWriter;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.DefaultNamedContainer;
import com.th3l4b.common.named.INamedContainer;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;

public class DefaultModel extends DefaultNamed implements IModel, IPrintable {

	DefaultNamedContainer<IEntity> _entities = new DefaultNamedContainer<IEntity>();
	DefaultNamedContainer<IRelationship> _relationships = new DefaultNamedContainer<IRelationship>();

	@Override
	public INamedContainer<IEntity> entities() throws Exception {
		return _entities;
	}

	@Override
	public INamedContainer<IRelationship> relationships() throws Exception {
		return _relationships;
	}

	@Override
	public void print(PrintWriter out) {
		PrintWriter iout = IndentedWriter.get(out);
		try {
			out.println("Entities:");
			for (IEntity e : entities().items()) {
				TextUtils.print(e, iout);
			}

			out.println("Relationships:");
			for (IRelationship r : relationships().items()) {
				TextUtils.print(r, iout);
			}

			// TODO Auto-generated method stub
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
