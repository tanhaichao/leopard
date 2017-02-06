package io.xiaoniu.shop;

public class BankcardVO {

	private String bankcardId;
	private BankVO bank;
	private String account;

	public String getBankcardId() {
		return bankcardId;
	}

	public void setBankcardId(String bankcardId) {
		this.bankcardId = bankcardId;
	}

	public BankVO getBank() {
		return bank;
	}

	public void setBank(BankVO bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
