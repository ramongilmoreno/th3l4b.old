package com.th3l4b.common.named;

import com.th3l4b.common.data.IGetter;
import com.th3l4b.common.data.nullsafe.NullSafe;

public class NamedUtils {

	public static IGetter<String, INamed> NAME_GETTER = NullSafe.getter(new IGetter<String, INamed>() {
		@Override
		public String get(INamed source) throws Exception {
			return source.getName();
		}
	});
	
}
