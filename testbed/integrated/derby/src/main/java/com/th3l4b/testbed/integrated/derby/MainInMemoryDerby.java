package com.th3l4b.testbed.integrated.derby;

public class MainInMemoryDerby {
	public static void main(String[] args) throws Exception {
		Main.main(new String[] {
				"org.apache.derby.jdbc.EmbeddedDriver",
				// https://db.apache.org/derby/docs/10.7/devguide/cdevdvlpinmemdb.html
				"jdbc:derby:memory:sample;create=true",
				"user",
				"password"
		});
	}
}
