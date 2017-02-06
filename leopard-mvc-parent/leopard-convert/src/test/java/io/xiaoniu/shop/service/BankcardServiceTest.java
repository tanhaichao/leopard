package io.xiaoniu.shop.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.leopard.json.Json;
import io.leopard.test.IntegrationTests;
import io.xiaoniu.shop.Bankcard;

public class BankcardServiceTest extends IntegrationTests {

	@Autowired
	private BankcardService bankcardService;

	@Test
	public void list() {
		List<Bankcard> bankcardList = bankcardService.list();
		Json.printList(bankcardList, "bankcardList");
	}

}