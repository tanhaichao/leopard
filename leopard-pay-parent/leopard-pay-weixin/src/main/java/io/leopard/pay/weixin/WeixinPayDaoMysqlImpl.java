package io.leopard.pay.weixin;

import java.util.Date;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;

public class WeixinPayDaoMysqlImpl implements WeixinPayDao {

	private Jdbc jdbc;

	public WeixinPayDaoMysqlImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public boolean add(String paymentId, String outTradeNo) {
		InsertBuilder builder = new InsertBuilder("weixin_pay_log");
		builder.setString("paymentId", paymentId);
		builder.setString("outTradeNo", outTradeNo);
		builder.setDate("posttime", new Date());
		return jdbc.insertForBoolean(builder);
	}

	@Override
	public String getPaymentId(String outTradeNo) {
		String sql = "select paymentId from weixin_pay_log where outTradeNo=? order by posttime desc limit 1";
		return jdbc.queryForString(sql, outTradeNo);
	}

}
