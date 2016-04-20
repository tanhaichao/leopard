package io.leopard.web4j.view;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * 抽象视图
 * 
 * @author 阿海
 * 
 */
public abstract class AbstractView extends ModelAndView {

	// protected static Log logger2 = LogFactory.getLog(AbstractView.class);

	// private static XssFilter xssFilter = new XssFilterImpl();

	// public static void setXssFilter(XssFilter xssFilter) {
	// AbstractView.xssFilter = xssFilter;
	// }

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {

		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String body = AbstractView.this.getBody(request, response);

			if (body == null) {
				return;
			}
			// if (!xssChecked) {
			// xssFilter.filter(logger, body);
			// }

			response.setContentType(AbstractView.this.getContentType());
			response.setContentLength(body.getBytes().length);
			// Flush byte array to servlet output stream.
			Writer out = response.getWriter();
			out.write(body);
			out.flush();
		}
	};

	public AbstractView() {
		super.setView(view);
	}

	public abstract String getContentType();

	public abstract String getBody(HttpServletRequest request, HttpServletResponse response);

}
