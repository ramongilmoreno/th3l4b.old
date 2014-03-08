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
import com.th3l4b.srm.runtime.DatabaseUtils;
import com.th3l4b.srm.runtime.IDatabaseConstants;

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
		iout.println("" + DatabaseUtils.column(SQLNames.ID, true) + " "
				+ idType + " NOT NULL PRIMARY KEY,");
		String statusType = names.type(context.getStatusType(), database);
		iout.print("" + DatabaseUtils.column(SQLNames.STATUS, true) + " "
				+ statusType + " NOT NULL");
		for (IField field : entity.items()) {
			iout.println(",");
			iout.println(""
					+ names.column(field, false)
					+ " "
					+ names.type(
							context.getTypes().get(
									IDatabaseConstants.BOOLEAN_TYPE), database)
					+ ",");
			iout.print(""
					+ names.column(field, true)
					+ " "
					+ names.type(context.getTypes().get(field.getType()),
							database));
		}
		for (INormalizedManyToOneRelationship rel : entity.relationships()
				.items()) {
			iout.println(",");
			iout.println(""
					+ names.column(rel, false, model)
					+ " "
					+ names.type(
							context.getTypes().get(
									IDatabaseConstants.BOOLEAN_TYPE), database)
					+ ",");
			iout.print("" + names.column(rel, true, model) + " " + idType);

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

	public void sql(INormalizedModel model, IDatabaseType database,
			SQLCodeGeneratorContext context, PrintWriter out) throws Exception {
		for (INormalizedEntity entity : model.items()) {
			createTable(entity, model, database, context, out);
			out.println("/");
			out.println();
		}
	}
}
