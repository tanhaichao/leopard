package io.leopard.web.editor;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;

public class BooleanEditor extends CustomBooleanEditor {

	public BooleanEditor() {
		super(false);
	}

	@Override
	public Object getValue() {
		Object value = super.getValue();
		if (value == null) {
			value = false;
		}
		return value;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.length() == 0) {
			super.setAsText("false");
		}
		else {
			super.setAsText(text);
		}
	}
}
