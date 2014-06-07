package com.th3l4b.testbed.integrated.derby;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.th3l4b.common.log.ConsoleLog;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.base.normalized.Normalizer;
import com.th3l4b.srm.base.original.IModel;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.database.SQLCodeGenerator;
import com.th3l4b.srm.codegen.database.SQLCodeGeneratorContext;
import com.th3l4b.srm.database.BasicSetDatabaseTypesContext;
import com.th3l4b.srm.database.IDatabaseType;
import com.th3l4b.srm.database.IDatabaseTypesContext;
import com.th3l4b.srm.parser.ParserUtils;
import com.th3l4b.testbed.integrated.model.AbstractIntegratedTestTests;
import com.th3l4b.testbed.integrated.model.generated.IRegularEntity;
import com.th3l4b.testbed.integrated.model.generated.base.INameForIntegratedTestContext;
import com.th3l4b.testbed.integrated.model.generated.jdbc.AbstractNameForIntegratedTestJDBCContext;
import com.th3l4b.types.base.basicset.BasicSetTypesContext;

public class Main {

	public static PrintStream setupSchema(Class<?> root, String srm,
			final Connection connection) throws Exception, IOException,
			SQLException {
		InputStream is = root.getResourceAsStream(srm);
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
		SQLCodeGeneratorContext context = new SQLCodeGeneratorContext(
				new BaseNames());
		context.setLog(new ConsoleLog());
		context.setDatabaseTypes(databaseTypes);
		context.setTypes(BasicSetTypesContext.get());
		IDatabaseType derbydb = databaseTypes
				.get(BasicSetDatabaseTypesContext.Databases.Derby.getName());
		generator.sql(normalized, derbydb, context, pw);
		pw.flush();

		String[] statements = sw.toString().split("/");
		PrintStream out = System.out;
		for (String statement : statements) {
			statement = statement.trim();
			if (statement.length() == 0) {
				continue;
			}
			out.println("*********");
			out.println(statement);
			PreparedStatement stmt = connection.prepareStatement(statement);
			stmt.execute();
		}
		out.println("*********");
		return out;
	}

	public static void main(String[] args) throws Exception {
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
			// Try tests
			final AbstractNameForIntegratedTestJDBCContext context = new AbstractNameForIntegratedTestJDBCContext() {
				@Override
				protected Connection getConnection() throws Exception {
					return connection;
				}
			};

			AbstractIntegratedTestTests test = new AbstractIntegratedTestTests() {
				@Override
				protected INameForIntegratedTestContext getContext()
						throws Exception {
					return context;
				}
			};
			setupSchema(IRegularEntity.class, "NameForIntegratedTest.srm",
					connection);
			test.everything();
			System.out.println("Derby test OK!");
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}
