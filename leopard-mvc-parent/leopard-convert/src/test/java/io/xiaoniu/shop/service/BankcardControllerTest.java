package io.xiaoniu.shop.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.leopard.json.Json;
import io.leopard.test.IntegrationTests;
import io.xiaoniu.shop.BankcardVO;

public class BankcardControllerTest extends IntegrationTests {

	@Autowired
	private BankcardController bankcardController;

	@Test
	public void list() {
		List<BankcardVO> bankcardList = bankcardController.list();
		Json.printList(bankcardList, "bankcardList");
	}

}