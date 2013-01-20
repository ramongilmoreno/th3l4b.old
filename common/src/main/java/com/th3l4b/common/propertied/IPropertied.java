package com.th3l4b.common.propertied;

import java.util.Map;

public interface IPropertied {
	Map<String, String> getProperties () throws Exception;
	Map<String, Object> getAttributes () throws Exception;
}
