package io.leopard.web.view;

import io.leopard.web.view.AbstractView;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AbstractViewTest {

	@Test
	public void renderMergedOutputModel() throws Exception {
		AbstractView testView = new AbstractView() {
			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public String getBody(HttpServletRequest request, HttpServletResponse response) {
				return "ok";
			}
		};

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = Mockito.spy(new MockHttpServletResponse());
		Map<String, Object> model = new HashMap<String, Object>();

		testView.getView().render(model, request, response);
		Mockito.verify(response, Mockito.times(1)).getWriter();

	}

	@Test
	public void test2() throws Exception {
		AbstractView testView = new AbstractView() {
			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public String getBody(HttpServletRequest request, HttpServletResponse response) {
				return null;
			}
		};
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = Mockito.spy(new MockHttpServletResponse());
		Map<String, Object> model = new HashMap<String, Object>();

		testView.getView().render(model, request, response);
		Mockito.verify(response, Mockito.times(0)).getWriter();

	}

	@Test
	public void AbstractView() {

	}

}