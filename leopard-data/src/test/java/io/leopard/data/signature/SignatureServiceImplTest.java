package io.leopard.data.signature;

import io.leopard.core.exception.invalid.SignatureInvalidException;
import io.leopard.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SignatureServiceImplTest {

	protected SignatureServiceImpl signatureService = newInstance();
	protected static Redis redis = Mockito.mock(Redis.class);

	protected static SignatureServiceImpl newInstance() {
		SignatureServiceImpl signatureService = new SignatureServiceImpl();
		signatureService.setRedis(redis);
		signatureService.setRedisKey("prefix");
		signatureService.setPublicKey("publicKey");
		signatureService.setMaxActive(10);
		signatureService.setUseBase16(false);
		signatureService.setServer("server");
		signatureService.setTimeout(3000);
		signatureService.setInitialPoolSize(1);
		signatureService.setCheckUsed(true);
		signatureService.init();
		return signatureService;
	}

	@Test
	public void checkSignature() {
		String encodeString = signatureService.encode("user");
		SignatureInfo signatureInfo = signatureService.checkSignature("user", encodeString);
		Assert.assertEquals("user", signatureInfo.getUser());
		try {
			signatureService.checkSignature("user1", encodeString);
			Assert.fail("怎么没有抛异常?");
		}
		catch (SignatureInvalidException e) {

		}
	}

	@Test
	public void checkSignature2() {
		String encodeString = signatureService.encode("1");
		SignatureInfo signatureInfo = signatureService.checkSignature(1L, encodeString);
		Assert.assertEquals("1", signatureInfo.getUser());
		try {
			signatureService.checkSignature(2L, encodeString);
			Assert.fail("怎么没有抛异常?");
		}
		catch (SignatureInvalidException e) {

		}
	}

	@Test
	public void encode() {

	}

}