package com.tls.tls_sigature;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;

public class TlsSigatureUtilTest {

	@Test
	public void genSignature() throws IOException {
		File privKey = new File("/sources/niudoc/doc/qcloud/im-keys/private_key");
		GenTLSSignatureResult result = TlsSigatureUtil.genSignature(1400037756, "admin", privKey);
		System.err.println("result:" + result.urlSig);
	}

}