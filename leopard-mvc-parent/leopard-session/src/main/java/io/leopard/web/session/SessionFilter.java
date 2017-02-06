package io.leopard.web.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "aleopardSessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {

	public SessionFilter() {
		System.err.println("new SessionFilter.");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// System.out.print("SessionFilter doFilter:" + request);
		LeopardRequestWrapper httpRequestWraper = new LeopardRequestWrapper(request, response);
		chain.doFilter(httpRequestWraper, response);
	}

	@Override
	public void destroy() {
		// System.out.print("SessionFilter destroy.");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// System.out.print("SessionFilter init:" + filterConfig);
	}

}
