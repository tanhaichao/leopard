package io.leopard.web.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OptionsHandler {

	boolean doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
