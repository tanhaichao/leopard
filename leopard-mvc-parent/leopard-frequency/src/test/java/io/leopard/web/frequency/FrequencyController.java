package io.leopard.web.frequency;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.leopard.jetty.JettyServer;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrequencyController {

	@RequestMapping("/welcome.do")
	@Frequency
	@ResponseBody
	public long welcome(Long uid) {
		return uid;
	}

	public static void main(String[] args) throws Exception {
		JettyServer.start("src/test/webapp");
	}
}
