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
import com.th3l4b.srm.database.IDatabaseType;

public class SQLCodeGenerator {

	/**
	 * Prints out the CREATE TABLE statement for the given entity.
	 */
	public void createTable(INormalizedEntity entity, INormalizedModel model,
			IDatabaseType database, SQLCodeGeneratorContext context,
			PrintWriter out) throws Exception {
		SQLNames names = context.getSQLNames();
		PrintWriter iout = IndentedWriter.get(out);
		out.println("CREATE TABLE " + names.table(entity) + " (");
		String idType = names.type(context.getIdentifierType(), database);
		iout.println("" + names.id(entity) + " " + idType
				+ " NOT NULL PRIMARY KEY;");
		String statusType = names.type(context.getStatusType(), database);
		iout.println("" + names.status(entity) + " " + statusType
				+ " NOT NULL;");
		for (IField field : entity.items()) {
			iout.println(""
					+ names.column(field)
					+ " "
					+ names.type(context.getTypes().get(field.getType()),
							database) + ";");
		}
		for (INormalizedManyToOneRelationship rel : entity.relationships()
				.items()) {
			iout.println("" + names.column(rel, model) + " " + idType + ";");

		}
		iout.flush();
		out.println(")");
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
		final SQLNames names = context.getSQLNames();
		for (IDatabaseType d : context.getDatabaseTypes().items()) {
			final IDatabaseType database = d;
			String name = "sql/" + names.name(model) + "-"
					+ names.name(database) + ".sql";
			FileUtils.overwriteIfOlder(context, name, new AbstractPrintable() {
				@Override
				protected void printWithException(PrintWriter out)
						throws Exception {
					for (INormalizedEntity entity : model.items()) {
						createTable(entity, model, database, context, out);
						out.println("/");
						out.println();
					}
				}
			});
		}
	}
}
