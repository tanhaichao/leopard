package io.leopard.web4j.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

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
		if (StringUtils.isEmpty(text)) {
			super.setValue(0);
		}
		else {
			super.setValue(Integer.parseInt(text));
		}
	}

}
