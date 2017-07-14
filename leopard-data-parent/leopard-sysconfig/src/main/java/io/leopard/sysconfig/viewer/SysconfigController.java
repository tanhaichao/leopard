package io.leopard.sysconfig.viewer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class SysconfigController {

	@RequestMapping("/sysconfig")
	@ResponseBody
	public SysconfigVO sysconfig() {
		
		return null;
	}
}
