package com.th3l4b.srm.codegen.database.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.srm.base.normalized.DefaultNormalizedEntity;
import com.th3l4b.srm.codegen.base.names.BaseNames;
import com.th3l4b.srm.codegen.database.SQLNames;

public class SQLNamesTest {
	@Test
	public void testSQL92Names () throws Exception {
		BaseNames baseNames = new BaseNames();
		SQLNames sqlNames = new SQLNames(baseNames);
		
		// Verify that table/column names start with a letter
		String[] candidateNames = {
			"a", "1a", "aaa", "_", "?a"	
		};
		String regex = "[a-zA-Z].*";
		for (String t: candidateNames) {
			DefaultNormalizedEntity ne = new DefaultNormalizedEntity();
			ne.setName(t);
			Assert.assertTrue(sqlNames.table(ne).matches(regex));
			Assert.assertTrue(sqlNames.column(t, null).matches(regex));
		}
	}
}
