package com.th3l4b.testbed.integrated.mongo.junit;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.testbed.integrated.model.generated.mongo.parsers.MongoNamesTest2Parser;
import com.th3l4b.testbed.integrated.model.generated.mongo.parsers.MongoNamesTestParser;

/**
 * Names as defined in the IntegratedTest.srm model.
 */
public class MongoNamesTest {
	@Test
	public void testCollectionNames() {
		String mongoEntity1 = "MongoEntity";
		String mongoEntity2 = "MongoEntity2";
		Assert.assertEquals(mongoEntity1, MongoNamesTestParser.COLLECTION_NAME);
		Assert.assertEquals("MongoProperty", MongoNamesTestParser.COLUMNS[0]);
		Assert.assertEquals(mongoEntity2, MongoNamesTestParser.COLUMNS[1]);
		Assert.assertEquals(mongoEntity2, MongoNamesTest2Parser.COLLECTION_NAME);
	}
}
