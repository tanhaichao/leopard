package io.leopard.web.freemarker.template;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleDate;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import io.leopard.web.freemarker.TemplateVariable;

public abstract class AbstractTemplateMethod implements TemplateMethodModelEx, TemplateVariable {
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		// arguments.toArray();
		Object[] args = new Object[arguments.size()];
		int i = 0;
		for (Object obj : arguments) {
			if (obj != null) {
				if (obj instanceof SimpleDate) {
					args[i] = ((SimpleDate) obj).getAsDate();
				}
				else if (obj instanceof SimpleNumber) {
					args[i] = ((SimpleNumber) obj).getAsNumber();
				}
				else if (obj instanceof SimpleNumber) {
					args[i] = ((SimpleNumber) obj).getAsNumber();
				}
				else if (obj instanceof SimpleScalar) {
					args[i] = ((SimpleScalar) obj).toString();
				}
				else if (obj instanceof StringModel) {
					args[i] = ((StringModel) obj);
				}
				else {
					System.err.println("obj:" + obj.getClass());
					args[i] = obj.toString();
				}
			}
			i++;
		}
		HttpServletRequest request = RequestHolder.getRequest();

		try {
			return this.exec(request, args);
		}
		catch (TemplateModelException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TemplateModelException(e);
		}
	}

	public abstract Object exec(HttpServletRequest request, Object... args) throws TemplateModelException, TemplateException, IOException;

}
