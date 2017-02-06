package io.xiaoniu.shop.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.convert.ConvertUtil;
import io.leopard.convert.Converter;
import io.xiaoniu.shop.Bank;
import io.xiaoniu.shop.BankVO;
import io.xiaoniu.shop.Bankcard;
import io.xiaoniu.shop.service.BankService;

@Component
public class BankConverter implements Converter<Bankcard, BankVO> {

	@Autowired
	private BankService bankService;

	@Override
	public BankVO get(Bankcard bankcard) {
		String bankId = bankcard.getBankId();
		Bank bank = bankService.get(bankId);

		System.out.println("bank:" + bank);
		return ConvertUtil.bean(bank, BankVO.class);
	}

}
