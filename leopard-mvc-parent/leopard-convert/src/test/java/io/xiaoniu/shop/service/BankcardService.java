package io.xiaoniu.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.xiaoniu.shop.Bankcard;

@Component
public class BankcardService {

	public List<Bankcard> list() {
		List<Bankcard> list = new ArrayList<Bankcard>();
		for (int i = 0; i < 3; i++) {
			Bankcard bankcard = new Bankcard();
			bankcard.setBankcardId("bankcardId:" + i);
			bankcard.setAccount("account" + i);
			bankcard.setBankId("bankid" + i);

			list.add(bankcard);
		}
		return list;
	}
}
