package io.leopard.sysconfig.viewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.sysconfig.SysconfigResolver;

@Controller
public class SysconfigController {

	@Autowired
	private SysconfigResolver sysconfigResolver;

	@RequestMapping("/sysconfig")
	@ResponseBody
	public SysconfigVO sysconfig() {
		return sysconfigResolver.get();
	}
}
