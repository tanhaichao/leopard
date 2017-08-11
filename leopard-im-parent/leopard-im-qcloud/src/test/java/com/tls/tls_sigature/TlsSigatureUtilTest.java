package com.tls.tls_sigature;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import org.junit.Test;

import com.tls.tls_sigature.tls_sigature.CheckTLSSignatureResult;
import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;

public class TlsSigatureUtilTest {

	@Test
	public void genSignature() throws IOException {
		File privKey = new File("/sources/niudoc/doc/qcloud/im-keys/private_key");
		GenTLSSignatureResult result = TlsSigatureUtil.genSignature(1400037756, "admin", privKey);
		System.err.println("result:" + result.urlSig);

	}

	@Test
	public void checkSignature() throws IOException, DataFormatException {
		File privKey = new File("/sources/niudoc/doc/qcloud/im-keys/private_key");
		GenTLSSignatureResult result = TlsSigatureUtil.genSignature(1400037756, "admin", privKey);

		File pubKey = new File("/sources/niudoc/doc/qcloud/im-keys/public_key");

		// check signature
		CheckTLSSignatureResult checkResult = TlsSigatureUtil.checkSignature(result.urlSig, 1400037756, "admin", pubKey);
		if (checkResult.verifyResult == false) {
			System.out.println("CheckTLSSignature failed: " + result.errMessage);
			return;
		}

		System.out.println("\n---\ncheck sig ok -- expire time " + checkResult.expireTime + " -- init time " + checkResult.initTime + "\n---\n");
	}

}