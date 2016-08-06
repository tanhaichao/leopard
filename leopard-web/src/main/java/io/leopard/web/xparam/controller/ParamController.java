package io.leopard.web.xparam.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.json.Json;

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
	public String mvc(long shopId, Date time, Month month, OnlyDate date, Mvcparam param) {
		logger.info("time:" + time);
		logger.info("month:" + month);
		logger.info("date:" + date);
		logger.info("param:" + Json.toJson(param));
		return "shopId:" + shopId;
	}
}
