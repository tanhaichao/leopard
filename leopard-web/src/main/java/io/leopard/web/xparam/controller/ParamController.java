package io.leopard.web.xparam.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.json.Json;
import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;

@Controller
@RequestMapping("/leopard/xparam/")
public class ParamController {

	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 欢迎测试页3.
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public String mvc(long shopId, Date ptime, Date time, Month pmonth, OnlyDate pdate, Mvcparam param) {
		logger.info("time:" + time);
		logger.info("ptime:" + ptime);
		logger.info("pmonth:" + pmonth);
		logger.info("pdate:" + pdate);
		logger.info("param:" + Json.toJson(param));
		return "shopId:" + shopId;
	}
}
