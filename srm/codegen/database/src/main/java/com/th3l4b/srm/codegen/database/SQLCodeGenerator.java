package com.th3l4b.srm.codegen.database;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.IField;
import com.th3l4b.srm.base.normalized.INormalizedEntity;
import com.th3l4b.srm.base.normalized.INormalizedManyToOneRelationship;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.runtime.IDatabaseConstants;

public class SQLCodeGenerator {

	/**
	 * Prints out the CREATE TABLE statement for the given entity.
	 */
	public void createTable(INormalizedEntity entity, INormalizedModel model,
			IDatabaseType database, SQLCodeGeneratorContext context,
			PrintWriter out) throws Exception {
		SQLNames sqlNames = context.getSQLNames();
		PrintWriter iout = IndentedWriter.get(out);
		out.println("CREATE TABLE " + sqlNames.table(entity) + " (");
		String idType = sqlNames.type(context.getIdentifierType(), database);
		iout.println("" + sqlNames.column(IDatabaseConstants.ID, null) + " "
				+ idType + " NOT NULL PRIMARY KEY,");
		String statusType = sqlNames.type(context.getStatusType(), database);
		iout.print("" + sqlNames.column(IDatabaseConstants.STATUS, null) + " "
				+ statusType + " NOT NULL");
		for (IField field : entity.items()) {
			iout.println(",");
			iout.print(""
					+ sqlNames.column(field)
					+ " "
					+ sqlNames.type(context.getTypes().get(field.getType()),
							database));
		}
		for (INormalizedManyToOneRelationship rel : entity.relationships()
				.items()) {
			iout.println(",");
			iout.print("" + sqlNames.column(rel, model) + " " + idType);

		}
		iout.flush();
		out.println();
		out.print(")");
	}

	public String createTableSingleLine(INormalizedEntity entity,
			INormalizedModel model, IDatabaseType database,
			SQLCodeGeneratorContext context) throws Exception {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		createTable(entity, model, database, context, pw);
		pw.flush();
		sw.flush();
		return sw.getBuffer().toString().replaceAll("\\s", " ")
				.replaceAll("\\s[\\s]+", " ").trim();
	}

	public void sql(final INormalizedModel model,
			final SQLCodeGeneratorContext context) throws Exception {
		final BaseNames baseNames = context.getBaseNames();
		final SQLNames names = context.getSQLNames();
		for (IDatabaseType d : context.getDatabaseTypes().items()) {
			final IDatabaseType database = d;
			String name = "sql/" + baseNames.name(model) + "-"
					+ names.name(database) + ".sql";
			FileUtils.overwriteIfOlder(context, name, new AbstractPrintable() {
				@Override
				protected void printWithException(PrintWriter out)
						throws Exception {
					sql(model, database, context, out);
				}
			});
		}
	}

	private void createIndexes(INormalizedEntity entity,
			INormalizedModel model, IDatabaseType database,
			SQLCodeGeneratorContext context, PrintWriter out) throws Exception {
		final SQLNames sqlNames = context.getSQLNames();
		for (INormalizedManyToOneRelationship rel : entity.relationships()
				.items()) {
			out.println("CREATE INDEX "
					+ sqlNames.index(entity, rel, database, model) + " ON "
					+ sqlNames.table(entity)

					+ " (" + sqlNames.column(rel, model) + ")/");
			out.println();
		}
	}

	public void sql(INormalizedModel model, IDatabaseType database,
			SQLCodeGeneratorContext context, PrintWriter out) throws Exception {
		// Create table
		for (INormalizedEntity entity : model.items()) {
			createTable(entity, model, database, context, out);
			out.println("/");
			out.println();
			createIndexes(entity, model, database, context, out);
		}
	}

}
