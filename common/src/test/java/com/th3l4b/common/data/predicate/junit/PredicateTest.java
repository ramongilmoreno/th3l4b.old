package com.th3l4b.common.data.predicate.junit;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.th3l4b.common.data.predicate.IPredicate;
import com.th3l4b.common.data.predicate.PredicateUtils;

public class PredicateTest {
	@Test
	public void test() throws Exception {
		ArrayList<Integer> l = createList();
		IPredicate<Integer> evenPredicate = new IPredicate<Integer>() {
			@Override
			public boolean accept(Integer t) throws Exception {
				return (t.intValue() % 2) == 0;
			}
		};
		Iterable<Integer> even = PredicateUtils.filter(l,
				evenPredicate);
		compare(even, 0, 2, 4, 6, 8);
		Iterable<Integer> odd = PredicateUtils.filter(l,
				PredicateUtils.not(evenPredicate));
		compare(odd, 1, 3, 5, 7, 9);

	}

	private void compare(Iterable<Integer> result, int... expected) {
		int i = 0;
		for (Integer in: result) {
			Assert.assertEquals("Failed on #" + i, in.intValue(), expected[i++]);
		}

	}

	private ArrayList<Integer> createList() {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			l.add(new Integer(i));
		}
		return l;
	}
}
