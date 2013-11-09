package com.th3l4b.srm.database;

public class BasicSetDatabaseTypesContext {

	public static enum Databases {
		Oracle, MySQL, SQLite, Derby;
		private String _name;

		Databases() {
			_name = name();
		}

		Databases(String name) {
			_name = name;
		}

		public String getName() {
			return _name;
		}
	}

	private static DefaultDatabaseTypesContext _default;

	public static IDatabaseTypesContext get() {
		if (_default == null) {
			_default = new DefaultDatabaseTypesContext();
			try {
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							Databases.Oracle.getName());
					_default.add(database);
				}
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							Databases.MySQL.getName());
					_default.add(database);
				}
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							Databases.SQLite.getName());
					_default.add(database);
				}
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							Databases.Derby.getName());
					_default.add(database);
				}
				return _default;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return _default;
	}
}
