package io.xiaoniu.shop.service;

import org.springframework.stereotype.Component;

import io.xiaoniu.shop.Bank;

@Component
public class BankService {

	public Bank get(String bankId) {
		Bank bank = new Bank();
		bank.setBankId(bankId);
		bank.setName("name:" + bankId);
		return bank;
	}
}
