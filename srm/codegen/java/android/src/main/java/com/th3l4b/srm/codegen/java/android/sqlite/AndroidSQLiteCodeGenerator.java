package com.th3l4b.srm.codegen.java.android.sqlite;

import java.io.PrintWriter;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.th3l4b.common.text.AbstractPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.srm.base.normalized.INormalizedModel;
import com.th3l4b.srm.codegen.base.FileUtils;

public class AndroidSQLiteCodeGenerator {
	public void helper(INormalizedModel model,
			AndroidSQLiteCodeGeneratorContext context) throws Exception {
		final AndroidSQLiteNames names = context.getSQLiteNames();
		final String pkg = names.packageForSQLite(context);
		final String clazz = names.helper(model, context);
		FileUtils.java(context, pkg, clazz, new AbstractPrintable() {
			@Override
			protected void printWithException(PrintWriter out) throws Exception {
				PrintWriter iout = IndentedWriter.get(out);
				PrintWriter iiout = IndentedWriter.get(iout);
				out.println("package " + pkg + ";");
				out.println();
				out.println("public class " + clazz + " extends "
						+ SQLiteOpenHelper.class.getName() + " {");
				out.println();
				iout.println("public " + clazz + "(" + Context.class.getName()
						+ " context, String name, "
						+ SQLiteDatabase.class.getName() + "."
						+ CursorFactory.class.getSimpleName()
						+ " factory, int version) {");
				iiout.println("super(context, name, factory, version);");
				iout.println("}");
				out.println();
				iout.println("public " + clazz + "(" + Context.class.getName()
						+ " context, String name, "
						+ SQLiteDatabase.class.getName() + "."
						+ CursorFactory.class.getSimpleName()
						+ " factory, int version, "
						+ DatabaseErrorHandler.class.getName()
						+ " errorHandler) {");
				iiout.println("super(context, name, factory, version, errorHandler);");
				iout.println("}");
				out.println();
				iout.println("");
				iout.println("public void onCreate("
						+ SQLiteDatabase.class.getName() + " database) {");
				iout.println("}");
				out.println();
				iout.println("public void onUpgrade("
						+ SQLiteDatabase.class.getName()
						+ " database, int oldv, int newv) {");
				iout.println("}");
				out.println("}");
				iiout.flush();
				iout.flush();
			}
		});

	}
}
