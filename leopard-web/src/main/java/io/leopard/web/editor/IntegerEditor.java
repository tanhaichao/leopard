package io.leopard.web.editor;

import java.beans.PropertyEditorSupport;

public class IntegerEditor extends PropertyEditorSupport {

	@Override
	public Object getValue() {
		Object value = super.getValue();
		if (value == null) {
			value = new Integer(0);
		}
		return value;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.length() == 0) {
			super.setValue(0);
		}
		else {
			super.setValue(Integer.parseInt(text));
		}
	}

}
