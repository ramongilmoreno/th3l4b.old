package com.th3l4b.types.base.basicset;

public enum BasicSetTypesEnum {
	_id, _integer, _decimal, _boolean, _date, _timestamp, _label, _string, _text;

	public String getName() {
		String n = name();
		return n.substring(1);
	}
}
