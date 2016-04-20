package io.leopard.data.signature;

import io.leopard.burrow.util.DateTime;
import io.leopard.burrow.util.DateUtil;
import io.leopard.burrow.util.EncryptUtil;
import io.leopard.commons.utility.Base16;
import io.leopard.commons.utility.Base64;
import io.leopard.core.exception.invalid.SignatureInvalidException;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

//@RunWith(LeopardMockRunner.class)
//@PrepareForTest({ SystemUtil.class })
public class SignatureTimeoutImplTest {

	protected SignatureTimeoutImpl signatureTimeoutImpl = new SignatureTimeoutImpl("key", 60, false);

	@Test
	public void encode() {
		String encode = signatureTimeoutImpl.encode("user", DateUtil.toDate("2013-01-01 00:00:00"));
		Assert.assertEquals("dXNlciwsMjAxMy0wMS0wMSAwMDowMDowMCwsMEY0MTQ5NzdCOTU1NTI0MjQ4N0JCOEJFMEVCOThDQzNFQTI4RDhFMA==", encode.replaceAll("\\s", ""));
	}

	@Test
	public void encode16() {
		SignatureTimeoutImpl signatureTimeoutImpl = new SignatureTimeoutImpl("key", 60, true);
		String encode = signatureTimeoutImpl.encode("user", DateUtil.toDate("2013-01-01 00:00:00"));
		System.out.println(encode);
		Assert.assertEquals("757365722c2c323031332d30312d30312030303a30303a30302c2c30463431343937374239353535323432343837424238424530454239384343334541323844384530", encode.replaceAll("\\s", ""));
	}

	@Test
	public void decode() {
		{
			try {
				signatureTimeoutImpl.decode("");
			}
			catch (SignatureInvalidException e) {
				Assert.assertTrue(e.getMessage().equals("参数key不能为空."));
			}
		}

		// ///
		{
			String encodeString = Base64.decode("1,,2");
			try {
				signatureTimeoutImpl.decode(encodeString);
			}
			catch (SignatureInvalidException e) {
				Assert.assertTrue(e.getMessage().startsWith("解密出错"));
			}
		}
		// ///
		{
			String encodeString = signatureTimeoutImpl.encode("user", new Date());

			SignatureInfo signatureInfo = signatureTimeoutImpl.decode(encodeString);
			Assert.assertNotNull(signatureInfo);
		}

	}

	@Test
	public void decode1() {
		try {
			signatureTimeoutImpl.decode("");
			Assert.fail("怎么没有抛异常?");
		}
		catch (SignatureInvalidException e) {

		}
	}

	@Test
	public void decode2() {
		String encode = Base64.encode("abc");
		try {
			signatureTimeoutImpl.decode(encode);
			Assert.fail("怎么没有抛异常?");
		}
		catch (SignatureInvalidException e) {
			Assert.assertTrue(e.getMessage().startsWith("解密出错["));
		}
	}

	@Test
	public void decode3() {
		String encode = Base64.encode("user" + SignatureTimeoutImpl.SPLIT + "20130101101010" + SignatureTimeoutImpl.SPLIT + "abc");
		try {
			signatureTimeoutImpl.decode(encode);
			Assert.fail("怎么没有抛异常?");
		}
		catch (SignatureInvalidException e) {
			Assert.assertTrue(e.getMessage().startsWith("解密出错，参数sha1不正确"));
		}
	}

	@Test
	public void SignatureTimeoutImpl() {
		// AUTO
	}

	@Test
	public void baseDecode() {
		SignatureTimeoutImpl signatureBase64 = new SignatureTimeoutImpl("key", 60);
		Assert.assertEquals("abc", signatureBase64.baseDecode(Base64.encode("abc")));

		SignatureTimeoutImpl signatureBase16 = new SignatureTimeoutImpl("key", 60, true);
		Assert.assertEquals("abc", signatureBase16.baseDecode(Base16.encode("abc")));
	}

	@Test
	public void decodeValid() {
		SignatureTimeoutImpl signature = new SignatureTimeoutImpl("key", 60);
		{
			try {
				signature.decodeValid("user", "posttime", "sha1");
				Assert.fail("怎没没有抛异常?");
			}
			catch (SignatureInvalidException e) {
				Assert.assertTrue(e.getMessage().equals("解密出错，参数sha1不正确."));
			}
		}

		{
			Date posttime = DateUtil.addTime(-61);
			String userinfo = "user" + SignatureTimeoutImpl.SPLIT + DateUtil.getTime(posttime);
			String sha1 = EncryptUtil.sha1(userinfo + signature.publicKey); // 使用SHA1加密算法

			// System.out.println("DateTime.getTime(posttime):" +
			// DateTime.getTime(posttime));
			try {
				signature.decodeValid("user", DateTime.getTime(posttime), sha1);
				Assert.fail("怎没没有抛异常?");
			}
			catch (SignatureInvalidException e) {
				// e.printStackTrace();
				Assert.assertTrue(e.getMessage().startsWith("解密出错，key已过期"));
			}
		}
		{
			Date posttime = DateUtil.addTime(-1);
			String userinfo = "user" + SignatureTimeoutImpl.SPLIT + DateUtil.getTime(posttime);
			String sha1 = EncryptUtil.sha1(userinfo + signature.publicKey); // 使用SHA1加密算法

			// System.out.println("DateTime.getTime(posttime):" +
			// DateTime.getTime(posttime));

			SignatureInfo signatureInfo = signature.decodeValid("user", DateTime.getTime(posttime), sha1);
			Assert.assertNotNull(signatureInfo);

		}
	}
}