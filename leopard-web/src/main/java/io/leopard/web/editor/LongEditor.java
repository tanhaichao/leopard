package io.leopard.web.editor;

import java.beans.PropertyEditorSupport;

public class LongEditor extends PropertyEditorSupport {

	public Object getValue() {
		Object value = super.getValue();
		// new Exception("getValue:" + value).printStackTrace();
		if (value == null) {
			value = new Long(0);
		}
		return value;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.length() == 0) {
			super.setValue(0);
		}
		else {
			super.setValue(Long.parseLong(text));
		}
	}

}
