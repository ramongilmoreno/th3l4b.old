package com.th3l4b.common.data;

public interface IGetter<R, S> {
	R get (S source) throws Exception;
}
