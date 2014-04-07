package com.th3l4b.testbed.integrated.derby.junit;

import org.junit.Test;

import com.th3l4b.testbed.integrated.derby.Main;

public class MainTest {
	@Test
	public void test() throws Exception {
		Main.main(new String[] { "org.apache.derby.jdbc.EmbeddedDriver",
				"jdbc:derby:memory:testDB;create=true", "", "" });
	}
}
