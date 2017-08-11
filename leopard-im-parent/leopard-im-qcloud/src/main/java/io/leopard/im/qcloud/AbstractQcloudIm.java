package io.leopard.im.qcloud;

import org.springframework.beans.factory.annotation.Value;

public class AbstractQcloudIm {

	@Value("${qcloud.im.appId}")
	private String appId;

}
