package io.leopard.apns;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import io.leopard.core.exception.IORuntimeException;
import io.leopard.data.env.EnvUtil;

public class ApnsClientImpl implements ApnsClient {
	// https://github.com/notnoop/java-apns

	private ApnsService apnsService;

	/**
	 * 密码
	 */
	@Value("apns.password")
	private String password;

	@PostConstruct
	public void init() {
		boolean production = EnvUtil.getEnv().equals(EnvUtil.ENV_PROD);
		String folder;
		if (production) {
			folder = "apns-prod";
		}
		else {
			folder = "apns-dev";
		}
		InputStream input;
		try {
			input = new ClassPathResource("/" + folder + "/push.p12").getInputStream();
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
		if (production) {
			apnsService = APNS.newService().withCert(input, password).withProductionDestination().asBatched().build();
		}
		else {
			apnsService = APNS.newService().withCert(input, password).withSandboxDestination().asBatched().build();
		}
		IOUtils.closeQuietly(input);
	}

	@Override
	public boolean send(String deviceToken, int badge, String body) {
		String payload = APNS.newPayload().alertBody(body).badge(badge).build();
		apnsService.push(deviceToken, payload);
		return true;

	}

	@Override
	public boolean sendByType(String deviceToken, int badge, String body, int type, String data) {
		String payload = APNS.newPayload().alertBody(body).badge(badge).customField("type", type).customField("data", data).build();
		apnsService.push(deviceToken, payload);
		return true;
	}

}
