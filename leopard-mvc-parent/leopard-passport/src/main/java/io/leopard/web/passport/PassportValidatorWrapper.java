package io.leopard.web.passport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassportValidatorWrapper implements PassportValidator {

	private PassportValidator passportValidator;

	public PassportValidatorWrapper(PassportValidator passportValidator) {
		if (passportValidator == null) {
			throw new IllegalArgumentException("参数passportValidator不能为空.");
		}
		this.passportValidator = passportValidator;
	}

	public PassportValidator getPassportValidator() {
		return passportValidator;
	}

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		return passportValidator.isNeedCheckLogin(request, handler);
	}

	@Override
	public Object validate(HttpServletRequest request, HttpServletResponse response) {
		return passportValidator.validate(request, response);
	}

	@Override
	public boolean showLoginBox(HttpServletRequest request, HttpServletResponse response) {
		return passportValidator.showLoginBox(request, response);
	}

	@Override
	public boolean login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return passportValidator.login(request, response);
	}

}
