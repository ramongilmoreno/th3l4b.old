package com.th3l4b.srm.database;

public class BasicSetDatabaseTypesContext {

	private static DefaultDatabaseTypesContext _default;

	public static IDatabaseTypesContext get() {
		if (_default == null) {
			_default = new DefaultDatabaseTypesContext();
			try {
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							"Oracle");
					_default.add(database);
				}
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							"MySQL");
					_default.add(database);
				}
				{
					DefaultDatabaseType database = new DefaultDatabaseType(
							"SQLite");
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
