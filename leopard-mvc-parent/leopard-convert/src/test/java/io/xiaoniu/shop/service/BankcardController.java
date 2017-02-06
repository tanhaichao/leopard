package io.xiaoniu.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import io.leopard.convert.ConvertJson;
import io.leopard.convert.ConvertUtil;
import io.xiaoniu.shop.Bankcard;
import io.xiaoniu.shop.BankcardVO;

@Controller
public class BankcardController {

	@Autowired
	private BankcardService bankcardService;

	public List<BankcardVO> list() {
		List<Bankcard> list = bankcardService.list();
		// return ConvertUtil.list(list, BankcardVO.class);
		return ConvertUtil.list(list, BankcardVO.class);
	}
}
