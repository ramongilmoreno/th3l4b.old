package com.th3l4b.srm.base;

import java.io.PrintWriter;

import com.th3l4b.common.named.DefaultNamed;
import com.th3l4b.common.named.INamed;
import com.th3l4b.common.text.IPrintable;
import com.th3l4b.common.text.IndentedWriter;
import com.th3l4b.common.text.TextUtils;

public class BaseRelationship extends DefaultNamed implements IModelConstants,
		IPrintable {

	protected INamed _direct;
	protected INamed _reverse;

	public String getTo() throws Exception {
		return getProperties().get(PROPERTY_RELATIONSHIP_TO);
	}

	public void setTo(String to) throws Exception {
		getProperties().put(PROPERTY_RELATIONSHIP_TO, to);
	}

	public INamed getDirect() throws Exception {
		return _direct;
	}

	public void setDirect(INamed direct) throws Exception {
		_direct = direct;
	}

	public INamed getReverse() throws Exception {
		return _reverse;
	}

	public void setReverse(INamed reverse) throws Exception {
		_reverse = reverse;
	}

	@Override
	public String toString() {
		try {
			return "" + getName() + " -> " + getTo();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void print(PrintWriter out) {
		out.println("" + toString());
		try {
			print("Direct", getDirect(), out);
			print("Reverse", getReverse(), out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void print(String title, INamed direct, PrintWriter out) {
		if (direct != null) {
			out.println(title);
			PrintWriter iout = IndentedWriter.get(out);
			TextUtils.print(direct, iout);
			iout.flush();
		}
	}
}
