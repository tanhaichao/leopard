package com.tls.tls_sigature;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import org.apache.commons.io.FileUtils;

import com.tls.tls_sigature.tls_sigature.CheckTLSSignatureResult;
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
		// System.err.println(privStr);
		GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(appId, identifier, privStr);
		return result;
	}

	public static CheckTLSSignatureResult checkSignature(String urlSig, long appId, String identifier, File publicKey) throws IOException, DataFormatException {
		String pubStr = FileUtils.readFileToString(publicKey);
		// System.err.println(pubStr);
		CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(urlSig, appId, identifier, pubStr);
		return checkResult;
	}

}
