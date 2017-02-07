package io.leopard.web.mvc.option;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OptionController {

	@RequestMapping("enum/{id}")
	@ResponseBody
	public Map<Object, Object> onum(@PathVariable String id) {
		Map<Object, Object> data = OptionData.getData(id);
		if (data == null) {
			throw new NullPointerException("枚举[" + id + "]不存在.");
		}
		return data;
	}
}
