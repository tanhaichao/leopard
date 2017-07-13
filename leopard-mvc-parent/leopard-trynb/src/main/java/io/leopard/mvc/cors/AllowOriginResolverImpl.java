package io.leopard.mvc.cors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class AllowOriginResolverImpl implements AllowOriginResolver {

	@Override
	public List<String> resolve(String cors) {
		if (StringUtils.isEmpty(cors) || "false".equals(cors)) {
			return new ArrayList<String>();
		}
		String[] blocks = cors.split(",");
		List<String> originList = new ArrayList<String>();
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = blocks[i].trim();
			originList.add(blocks[i]);
		}
		return originList;
	}

}
