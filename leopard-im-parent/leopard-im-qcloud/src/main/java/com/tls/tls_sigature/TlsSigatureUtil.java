package com.tls.tls_sigature;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;

/**
 * 签名生成
 * 
 * @author 谭海潮
 *
 */
public class TlsSigatureUtil {

	public static GenTLSSignatureResult genSignature(long appId, String identifier, File privKey) throws IOException {
		String privStr = FileUtils.readFileToString(privKey);
		GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(1400000955, identifier, privStr);
		return result;
	}
}
