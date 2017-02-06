package io.leopard.web.captcha.kit;

import java.util.Date;

import org.springframework.stereotype.Repository;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;

@Repository
public class CaptchaDaoMysqlImpl implements CaptchaDao {

	private Jdbc jdbc;

	private String tableName;

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public boolean add(Captcha captcha) {
		InsertBuilder builder = new InsertBuilder(tableName);
		builder.setString("captchaId", captcha.getCaptchaId());
		builder.setString("type", captcha.getType());
		builder.setString("target", captcha.getTarget());
		builder.setString("account", captcha.getAccount());
		builder.setString("captcha", captcha.getCaptcha());
		builder.setBool("used", captcha.isUsed());
		builder.setDate("posttime", captcha.getPosttime());
		builder.setDate("expiryTime", captcha.getExpiryTime());
		builder.setDate("lmodify", captcha.getLmodify());
		return jdbc.insertForBoolean(builder);
	}

	@Override
	public Captcha last(String account, String type, String target) {
		String sql = "select * from " + tableName + " where account=? and type=? and target=? and used=0 order by posttime desc limit 1";
		return this.jdbc.query(sql, Captcha.class, account, type, target);
	}

	@Override
	public boolean updateUsed(String captchaId, boolean used) {
		String sql = "update " + tableName + " set used=? where captchaId=?";
		return this.jdbc.updateForBoolean(sql, used, captchaId);
	}

	@Override
	public boolean updateLmodify(String captchaId, Date lmodify) {
		String sql = "update " + tableName + " set lmodify=? where captchaId=?";
		return this.jdbc.updateForBoolean(sql, lmodify, captchaId);
	}
}
