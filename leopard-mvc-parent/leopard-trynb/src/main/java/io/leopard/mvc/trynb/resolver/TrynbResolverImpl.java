package io.leopard.mvc.trynb.resolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.mvc.trynb.model.TrynbInfo;

public class TrynbResolverImpl implements TrynbResolver {

	private List<TrynbResolver> list = new ArrayList<TrynbResolver>();

	public TrynbResolverImpl() {
		Iterator<TrynbResolver> iterator = ServiceLoader.load(TrynbResolver.class).iterator();
		while (iterator.hasNext()) {
			TrynbResolver resolver = iterator.next();
			// System.out.println("resolver:" + resolver);
			list.add(resolver);
		}
	}

	@Override
	public ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception exception, TrynbInfo trynbInfo) {
		for (TrynbResolver trynbResolver : list) {
			ModelAndView view = trynbResolver.resolveView(request, response, handler, exception, trynbInfo);
			if (view != null) {
				return view;
			}
		}
		String page = trynbInfo.getPage();
		if (StringUtils.isEmpty(page)) {
			page = "/error";
		}
		ModelAndView view = new ModelAndView(page);
		view.addObject("message", trynbInfo.getMessage());
		return view;
	}

}
