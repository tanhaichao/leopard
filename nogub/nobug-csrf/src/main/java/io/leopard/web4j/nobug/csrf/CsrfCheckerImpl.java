package io.leopard.web4j.nobug.csrf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

public class CsrfCheckerImpl implements CsrfChecker {

	private List<CsrfChecker> list = new ArrayList<CsrfChecker>();

	public CsrfCheckerImpl() {
		list.add(new CsrfCheckerOfficeIpImpl());
		list.add(new CsrfCheckerAdminFolderImpl());
		list.add(new CsrfCheckerExcludeUriImpl());
		list.add(new CsrfCheckerNoCsrfImpl());
	}

	@Override
	public boolean isSafe(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response, TokenVerifier tokenVerifier) {
		for (CsrfChecker checker : list) {
			if (checker.isSafe(handlerMethod, request, response, tokenVerifier)) {
				return true;
			}
		}
		return false;
	}

}
