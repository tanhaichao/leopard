package io.leopard.jdbc.builder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * SQL解析器.
 * 
 * @author 阿海
 * 
 */
public class AbstractSqlParser {

	protected LinkedHashSet<String> parse(String fieldZone, String valueZone) {
		List<String> fieldList = this.parseField(fieldZone);
		List<Boolean> paramList = this.parseParams(valueZone);
		int fieldCount = fieldList.size();
		int paramCount = paramList.size();
		// AssertUtil.assertTrue(fieldCount == paramCount, "字段名称个数[" + fieldCount + "]和参数个数[" + paramCount + "]不一致?");
		if (fieldCount != paramCount) {
			throw new IllegalArgumentException("字段名称个数[" + fieldCount + "]和参数个数[" + paramCount + "]不一致?");
		}
		LinkedHashSet<String> set = new LinkedHashSet<String>();
		for (int i = 0; i < paramList.size(); i++) {
			if (paramList.get(i)) {
				set.add(fieldList.get(i));
			}
		}
		return set;
	}

	protected List<String> parseField(String fieldZone) {
		String[] strs = fieldZone.split(",");
		List<String> list = new ArrayList<String>();
		for (String str : strs) {
			str = str.trim();
			list.add(str);
		}
		return list;
	}

	protected List<Boolean> parseParams(String valueZone) {
		String[] strs = valueZone.split(",");
		List<Boolean> list = new ArrayList<Boolean>();
		for (String str : strs) {
			str = str.trim();
			boolean isParam = "?".equals(str);
			list.add(isParam);
		}
		return list;
	}
}
