package io.leopard.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface ModelAndViewRender {

	void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
