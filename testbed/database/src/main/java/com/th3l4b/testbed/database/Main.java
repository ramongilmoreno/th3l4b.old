package com.th3l4b.testbed.database;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.th3l4b.apps.shopping.base.codegen.srm.IItem;
import com.th3l4b.apps.shopping.base.codegen.srm.jdbc.AbstractShoppingJDBCContext;
import com.th3l4b.common.log.ConsoleLog;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.database.SQLCodeGenerator;
import com.th3l4b.srm.codegen.database.SQLCodeGeneratorContext;
import com.th3l4b.srm.database.BasicSetDatabaseTypesContext;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.database.IDatabaseTypesContext;
import com.th3l4b.srm.parser.ParserUtils;
import com.th3l4b.types.base.basicset.BasicSetTypesContext;

public class Main {

	public static void main(String[] args) throws Exception {
		main2(new String[] { "org.apache.derby.jdbc.EmbeddedDriver",
				"jdbc:derby:memory:testDB;create=true", "", "" });
	}

	public static void main2(String[] args) throws Exception {
		int i = 0;
		String driver = args[i++];
		String url = args[i++];
		String user = args[i++];
		String password = args[i++];

		// https://db.apache.org/derby/docs/10.7/devguide/cdevdvlpinmemdb.html
		Class.forName(driver);
		final Connection connection = DriverManager.getConnection(url, user,
				password);
		try {
			InputStream is = IItem.class.getResourceAsStream("Shopping.srm");
			IModel model;
			try {
				model = ParserUtils.parse(is);
			} finally {
				if (is != null) {
					is.close();
				}
			}
			INormalizedModel normalized = Normalizer.normalize(model);
			SQLCodeGenerator generator = new SQLCodeGenerator();
			IDatabaseTypesContext databaseTypes = BasicSetDatabaseTypesContext
					.get();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			SQLCodeGeneratorContext context = new SQLCodeGeneratorContext();
			context.setLog(new ConsoleLog());
			context.setDatabaseTypes(databaseTypes);
			context.setTypes(BasicSetTypesContext.get());
			IDatabaseType derbydb = databaseTypes
					.get(BasicSetDatabaseTypesContext.Databases.Derby.getName());
			generator.sql(normalized, derbydb, context, pw);
			pw.flush();

			String[] statements = sw.toString().split("/");
			for (String statement : statements) {
				statement = statement.trim();
				if (statement.length() == 0) {
					continue;
				}
				System.out.println("*********");
				System.out.println(statement);
				PreparedStatement stmt = connection.prepareStatement(statement);
				boolean success = stmt.execute();
				System.out.println("OK:" + success);
				System.out.println("*********");
			}
			// Create a JDBC context and try to install some items
			AbstractShoppingJDBCContext shopping = new AbstractShoppingJDBCContext() {
				@Override
				protected Connection getConnection() throws Exception {
					return connection;
				}
			};
			
			Iterable<IItem> items = shopping.getFinder().allItem();
			for (IItem item: items) {
				System.out.println(item);
			}
			
			

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}
