package io.leopard.mvc.trynb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

public class ResultModifierImpl implements ResultModifier {

	private final static ResultModifier instance = new ResultModifierImpl();

	public static ResultModifier getInstance() {
		return instance;
	}

	private List<ResultModifier> list = new ArrayList<ResultModifier>();

	public ResultModifierImpl() {
		Iterator<ResultModifier> iterator = ServiceLoader.load(ResultModifier.class).iterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
			// System.err.println("ResultModifierImpl load:" + list.get(list.size() - 1).getClass().getName());
		}
	}

	@Override
	public void modify(HttpServletRequest request, HandlerMethod handler, Exception exception, Map<String, Object> map, Object data) {
		for (ResultModifier modifier : list) {
			modifier.modify(request, handler, exception, map, data);
		}
	}

}
