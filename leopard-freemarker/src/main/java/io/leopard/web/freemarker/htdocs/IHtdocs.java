package io.leopard.web.freemarker.htdocs;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHtdocs {

	String getHtdocsPath();

	void doFile(HttpServletRequest request, HttpServletResponse response, String filename) throws ServletException, IOException;

	long getExpires(String filename);

	InputStream readFile(HttpServletRequest request, String filename) throws IOException;

	byte[] toBytes(InputStream input) throws IOException;

}
