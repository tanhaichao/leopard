package io.leopard.web4j.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

public class LongEditor extends PropertyEditorSupport {

	public Object getValue() {
		Object value = super.getValue();
		if (value == null) {
			value = new Long(0);
		}
		return value;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			super.setValue(0);
		}
		else {
			super.setValue(Long.parseLong(text));
		}
	}

}
