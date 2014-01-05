package com.th3l4b.types.base.basicset;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.th3l4b.common.text.SplitMap;
import com.th3l4b.types.base.DefaultType;
import com.th3l4b.types.base.DefaultTypesContext;
import com.th3l4b.types.base.ITypesContext;

/**
 * Implementation of the basi list of types.
 */
public class BasicSetTypesContext {

	private static ITypesContext _default;

	public static ITypesContext get() {
		if (_default == null) {
			DefaultTypesContext d = new DefaultTypesContext();
			InputStream is = BasicSetTypesContext.class
					.getResourceAsStream(BasicSetTypesEnum.class
							.getSimpleName() + ".properties");
			Properties p = new Properties();
			try {
				p.load(is);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					is.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			Map<String, Map<String, String>> properties = SplitMap
					.split(p, ".");
			try {
				for (BasicSetTypesEnum t : BasicSetTypesEnum.values()) {
					DefaultType type = new DefaultType();
					String name = t.getName();
					type.setName(name);
					Map<String, String> map = properties.get(name);
					if (map != null) {
						type.getProperties().putAll(map);
					}
					d.add(type);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			_default = d;
		}
		return _default;
	}
}
