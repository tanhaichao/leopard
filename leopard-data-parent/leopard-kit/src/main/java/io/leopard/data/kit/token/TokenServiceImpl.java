package io.leopard.data.kit.token;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import io.leopard.core.exception.forbidden.TokenWrongException;
import io.leopard.jdbc.Jdbc;
import io.leopard.util.EncryptUtil;

public class TokenServiceImpl implements TokenService {

	protected Log logger = LogFactory.getLog(this.getClass());

	protected Jdbc jdbc;

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	protected TokenDao tokenDao;

	@PostConstruct
	public void init() {
		TokenDaoMysqlImpl tokenDaoMysqlImpl = new TokenDaoMysqlImpl();
		tokenDaoMysqlImpl.setJdbc(jdbc);
		this.tokenDao = tokenDaoMysqlImpl;
	}

	@Override
	public String add(String account, String category, String target, String token) {
		Date expiryTime = new Date(System.currentTimeMillis() + 3600 * 1000L);// 1小时过期
		return this.add(account, category, target, token, expiryTime);
	}

	@Override
	public String add(String account, String category, String target, String token, Date expiryTime) {
		Assert.hasText(account, "参数account不能为空");
		Assert.hasText(category, "参数category不能为空");
		Assert.hasText(target, "参数target不能为空");
		Assert.hasText(token, "参数token不能为空");

		String tokenId = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();

		Date posttime = new Date();
		Token bean = new Token();
		bean.setTokenId(tokenId);
		bean.setAccount(account);
		bean.setCategory(category);
		bean.setTarget(target);
		bean.setPosttime(posttime);
		bean.setExpiryTime(expiryTime);
		bean.setLmodify(posttime);
		bean.setToken(token);
		bean.setUsed(false);
		
		tokenDao.add(bean);
		return tokenId;
	}

	@Override
	public Token check(String account, String category, String target, String token) throws TokenWrongException {
		Assert.hasText(account, "参数account不能为空");
		Assert.hasText(category, "参数category不能为空");
		Assert.hasText(target, "参数target不能为空");
		Assert.hasText(token, "参数token不能为空");

		Token bean = this.last(account, category, target);
		if (bean == null) {
			throw new TokenWrongException("token不存在.");
		}
		if (bean.isUsed()) {
			throw new TokenWrongException("token已使用.");
		}
		if (bean.getExpiryTime().before(new Date())) {
			throw new TokenWrongException("token已过期.");
		}
		if (!bean.getToken().equals(token)) {
			throw new TokenWrongException("token不正确.");
		}
		return bean;
	}

	protected String lastToken(String account, String category, String target) {
		Token captcha = this.last(account, category, target);
		if (captcha == null) {
			return null;
		}
		return captcha.getToken();
	}

	@Override
	public Token last(String account, String category, String target) {
		Jdbc jdbc = ((TokenDaoMysqlImpl) tokenDao).getJdbc();
		if (jdbc == null) {
			throw new NullPointerException("jdbc还没有初始化.");
		}

		Assert.hasText(account, "参数account不能为空");
		Assert.hasText(category, "参数category不能为空");
		Assert.hasText(target, "参数target不能为空");
		return this.tokenDao.last(account, category, target);
	}

	@Override
	public String makeToken(String account, String category, String target) {
		Date expiryTime = new Date(System.currentTimeMillis() + 3600 * 1000L);// 1小时过期
		return this.makeToken(account, category, target, expiryTime);
	}

	@Override
	public String makeToken(String account, String category, String target, Date expiryTime) {
		Assert.hasText(account, "参数account不能为空");
		Assert.hasText(category, "参数category不能为空");
		Assert.hasText(target, "参数target不能为空");

		String str = System.currentTimeMillis() + ":" + new Random().nextDouble();
		String token = EncryptUtil.md5(str);
		this.add(account, category, target, token, expiryTime);
		return token;
	}

	@Override
	public boolean updateUsed(String captchaId, boolean used) {
		Assert.hasText(captchaId, "参数captchaId不能为空");
		return tokenDao.updateUsed(captchaId, used);
	}
}
