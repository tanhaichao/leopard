package io.leopard.web.mvc.option;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Onum;
import io.leopard.lang.inum.SubEnum;

public class OptionData {

	private static Map<String, OptionInfo> data = new LinkedHashMap<>();

	public static List<OptionInfo> list() {
		return new ArrayList<>(data.values());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void load(String id, String className) {
		Class<? extends Enum> clazz;
		try {
			clazz = (Class<? extends Enum>) Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// System.err.println("load id:" + id + " className:" + className);

		{
			OptionInfo info = data.get(id);
			if (info != null) {
				throw new RuntimeException("枚举ID[" + id + "]已被clazz[" + info.getClassName() + "]使用..");
			}
		}

		List<OptionVO> data = toData(clazz);

		OptionInfo info = new OptionInfo();
		info.setId(id);
		info.setClassName(className);
		info.setClazz(clazz);
		info.setData(data);
		put(id, info);
	}

	@SuppressWarnings("unchecked")
	private static List<OptionVO> toData(@SuppressWarnings("rawtypes") Class<? extends Enum> clazz) {
		boolean isSubEnum = SubEnum.class.isAssignableFrom(clazz);
		// System.err.println("clazz:" + clazz.getSimpleName() + " isSubEnum:" + isSubEnum);

		Map<Object, Enum<?>> map = EnumUtil.toMap(clazz);
		List<OptionVO> constantList = new ArrayList<OptionVO>();
		for (Entry<Object, Enum<?>> entry : map.entrySet()) {
			Object key = entry.getKey();

			@SuppressWarnings("rawtypes")
			Onum onum = (Onum) entry.getValue();
			String desc = (String) onum.getDesc();
			// System.err.println("put key:"+key+" desc:"+desc);

			OptionVO option = new OptionVO();
			option.setKey(key);
			option.setDesc(desc);

			if (isSubEnum) {
				List<OptionVO> childList = getChildList(onum);
				option.setChildList(childList);
			}
			constantList.add(option);
		}
		return constantList;
	}

	@SuppressWarnings("rawtypes")
	protected static List<OptionVO> getChildList(@SuppressWarnings("rawtypes") Onum onum) {
		SubEnum subEnum = (SubEnum) onum;
		List subList = subEnum.getSubList();
		System.err.println("getChildList:" + onum.getClass().getName());
		List<OptionVO> childList = new ArrayList<OptionVO>();
		for (Object child : subList) {
			Onum childConstant = (Onum) child;
			OptionVO option = new OptionVO();
			Object key = childConstant.getKey();
			String desc = (String) childConstant.getDesc();
			option.setKey(key);
			option.setDesc(desc);
			childList.add(option);
		}
		return childList;
	}

	public static void put(String id, OptionInfo info) {
		data.put(id, info);
	}

	public static List<OptionVO> getData(String id) {
		OptionInfo info = data.get(id);
		if (info == null) {
			return null;
		}
		return info.getData();
	}
}
