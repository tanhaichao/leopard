package io.leopard.web.xparam.resolver;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import io.leopard.burrow.util.DateUtil;
import io.leopard.lang.TimeRange;

/**
 * 时间范围对象参数解析器
 * 
 * @author 阿海
 *
 */
@Component
public class TimeRangeHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> type = parameter.getParameterType();
		return type.equals(TimeRange.class);
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
		// boolean enable = UnderlineHandlerMethodArgumentResolver.isEnable();
		// String startTimeName = "startTime";
		// String endTimeName = "endTime";
		// if (enable) {
		// startTimeName = UnderlineHandlerMethodArgumentResolver.camelToUnderline(startTimeName);
		// endTimeName = UnderlineHandlerMethodArgumentResolver.camelToUnderline(endTimeName);
		// }
		String startTime = getParameter(req, "startTime");
		String endTime = getParameter(req, "endTime");

		TimeRange range = new TimeRange();
		if (StringUtils.isNotEmpty(startTime)) {
			range.setStartTime(toDate(startTime));
		}
		if (StringUtils.isNotEmpty(endTime)) {
			range.setEndTime(toDate(endTime));
		}
		return range;
	}

	private static String getParameter(HttpServletRequest request, String name) {
		String value = RequestBodyParser.getParameter(request, name);
		if (UnderlineNameConfiger.isEnable() && value == null) {
			value = RequestBodyParser.getParameter(request, UnderlineNameConfiger.camelToUnderline(name));
		}
		return value;
	}

	/**
	 * 时间戳
	 */
	private static String TIMESTAMP_REGEX = "1[0-9]{12}";

	private static String DATE_REGEX = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";

	protected static Date toDate(String datetime) {
		if (datetime.matches(TIMESTAMP_REGEX)) {
			long time = Long.parseLong(datetime);
			return new Date(time);
		}
		if (datetime.matches(DATE_REGEX)) {
			datetime += " 00:00:00";
		}

		// if (SystemUtils.IS_OS_WINDOWS) {// TODO 测试代码
		// try {
		// return DateUtil.toDate(datetime);
		// }
		// catch (NumberFormatException e) {
		// return null;
		// }
		// }
		// TODO ahai startTime=1488297600 java.lang.StringIndexOutOfBoundsException: String index out of range: 13

		return DateUtil.toDate(datetime);
	}

}
