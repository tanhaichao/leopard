package io.leopard.web4j.passport;

import io.leopard.burrow.DefaultBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@DefaultBean
public class PassportValidateLeiImpl extends AbstractPassportValidateLei {

	@Override
	public PassportUser validate(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}
