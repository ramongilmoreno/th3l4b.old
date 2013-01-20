package com.th3l4b.common.named;

import com.th3l4b.common.propertied.IPropertied;

public interface INamed extends IPropertied, INamedConstants {
	String getName() throws Exception;
}
