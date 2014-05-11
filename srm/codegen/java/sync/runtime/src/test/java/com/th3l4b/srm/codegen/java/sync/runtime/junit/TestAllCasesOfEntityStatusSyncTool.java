package com.th3l4b.srm.codegen.java.sync.runtime.junit;

import org.junit.Test;

import com.th3l4b.srm.codegen.java.sync.runtime.EntityStatusSyncTool;
import com.th3l4b.srm.runtime.EntityStatus;

public class TestAllCasesOfEntityStatusSyncTool {

	@Test
	public void test() throws Exception {
		for (EntityStatus a : EntityStatus.values()) {
			if (!a.isTransitional()) {
				for (EntityStatus b : EntityStatus.values()) {
					if (!b.isTransitional()) {
						EntityStatusSyncTool.howToGet(a, b);
					}
				}
			}
		}
	}
}
