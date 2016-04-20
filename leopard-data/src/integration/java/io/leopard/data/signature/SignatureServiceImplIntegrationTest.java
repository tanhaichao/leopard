package io.leopard.data.signature;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.data4j.redis.Redis;

import org.junit.Test;
import org.mockito.Mockito;

public class SignatureServiceImplIntegrationTest {

	protected SignatureServiceImpl signatureService = newInstance();
	protected static Redis redis = Mockito.mock(Redis.class);

	protected static SignatureServiceImpl newInstance() {
		SignatureServiceImpl signatureService = new SignatureServiceImpl();
		signatureService.setRedis(redis);
		signatureService.setRedisKey("prefix");
		signatureService.setPublicKey("publicKey");
		signatureService.init();
		return signatureService;
	}

	@Test
	public void encode() {

	}

	@Test
	public void add() {
		signatureService.add("sha1");
		signatureService.add("sha2");
		signatureService.add("sha3");
		SystemUtil.sleep(1000 * 10);
	}

}