package io.leopard.burrow.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * List转换工具类
 * 
 * @author Administrator
 * 
 */
public class ListUtil {

	
	/**
	 * 返回list的size,如果list等于null，返回0</br>
	 * 
	 * @param list
	 * @return
	 */
	public static int size(Object[] list) {
		if (list == null) {
			return 0;
		}
		else {
			return list.length;
		}
	}

	/**
	 * 返回list的size,如果list等于null，返回0</br>
	 * 
	 * @param list
	 * @return
	 */
	public static int size(long[] list) {
		if (list == null) {
			return 0;
		}
		else {
			return list.length;
		}
	}

	/**
	 * 返回list的size,如果list等于null，返回0</br>
	 * 
	 * @param list
	 * @return
	 */
	public static int size(List<?> list) {
		if (list == null) {
			return 0;
		}
		else {
			return list.size();
		}
	}

	/**
	 * List的内容去重</br>
	 * 
	 * @param list
	 *            需要去重的list
	 * @return 去重后的list
	 */
	public static List<String> uniq(List<String> list) {
		if (list == null) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		for (String str : list) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return result;
	}

	/**
	 * 判断List是否为非空</br>
	 * 
	 * @param list
	 *            List
	 * @return 若非空返回true
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
		// if (list == null || list.isEmpty()) {
		// return false;
		// }
		// else {
		// return true;
		// }
	}

	/**
	 * 判断List是否为空
	 * 
	 * @param list
	 *            List
	 * @return 若空返回true
	 */
	public static boolean isEmpty(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 把null或者不包含元素的List转成null
	 * 
	 * @param list
	 *            List对象
	 * @return 转换后的List对象
	 */
	public static List<String> toStringResult(List<String> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}

	/**
	 * 把null转换成长度为0的数组
	 * 
	 * @param strs
	 *            字符串数组
	 * @return 转换后的数组
	 */
	public static String[] defaultStrings(String[] strs) {
		if (strs == null) {
			return new String[0];
		}
		return strs;
	}

	/**
	 * 把null转成包含0个元素的List
	 * 
	 * @param list
	 *            List对象
	 * @return 转换后的List
	 */
	public static <T> List<T> defaultList(List<T> list) {
		if (list == null) {
			return new ArrayList<T>();
		}
		return list;
	}

	/**
	 * 去除List中的null元素
	 * 
	 * @param list
	 *            List
	 * @return 去除null元素后的List
	 */
	public static <T> List<T> noNull(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		// 删除空记录
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next == null) {
				iterator.remove();
			}
		}
		return list;
	}

	/**
	 * 将字符串集合转成字符串List
	 * 
	 * @param set
	 *            字符串集合
	 * @return 转换后的List
	 */
	public static List<String> toList(Set<String> set) {
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String str : set) {
			list.add(str);
		}
		return list;
	}

	// public static Set<String> toSet(List<String> list) {
	// if (list == null || list.isEmpty()) {
	// return null;
	// }
	// Set<String> set = new LinkedHashSet<String>();
	// for (String element : list) {
	// set.add(element);
	// }
	// return set;
	// }

	/**
	 * 将List转成Set
	 * 
	 * @param list
	 *            List
	 * @return Set
	 */
	public static <KEYTYPE> Set<KEYTYPE> toSet(List<KEYTYPE> list) {
		if (isEmpty(list)) {
			return null;
		}
		Set<KEYTYPE> set = new LinkedHashSet<KEYTYPE>();
		for (KEYTYPE element : list) {
			set.add(element);
		}
		return set;
	}

	/**
	 * 将整型List转成整形Set
	 * 
	 * @param list
	 *            List
	 * @return Set
	 */
	public static Set<Integer> toIntSet(List<Integer> list) {
		if (isEmpty(list)) {
			return null;
		}
		Set<Integer> set = new LinkedHashSet<Integer>();
		for (Integer element : list) {
			set.add(element);
		}
		return set;
	}

	/**
	 * 将整数添加到一个新的List
	 * 
	 * @param num
	 *            整数
	 * @return 新的List
	 */
	public static List<Integer> toIntList(int num) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(num);
		return list;
	}

	/**
	 * 将多个整数(用“,”隔开)添加到一个新的整型List
	 * 
	 * @param content
	 *            包含多个整数的字符串
	 * @return 整型List
	 */
	public static List<Integer> toIntList(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String[] strs = StringUtils.split(content, ",");
		List<Integer> list = new ArrayList<Integer>();
		for (String str : strs) {
			int num = Integer.parseInt(str.trim());
			list.add(num);
		}
		return list;
	}

	/**
	 * 将文本内容(用“,”隔开)添加到一个新的字符串List
	 * 
	 * @param content
	 *            文本
	 * @return 字符串List
	 */
	public static List<String> toList(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String[] strs = StringUtils.split(content, ",");
		List<String> list = new ArrayList<String>();
		for (String str : strs) {
			list.add(str);
		}
		return list;
	}

	/**
	 * 将文本内容(用“,”隔开)添加到一个新的字符串List
	 * 
	 * @param content
	 *            文本
	 * @param trim
	 *            忽略头尾空白
	 * @return 字符串List
	 */
	public static List<String> toList(String content, boolean trim) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		if (!trim) {
			return toList(content);
		}
		content = content.trim();
		String[] strs = StringUtils.split(content, ",");
		List<String> list = new ArrayList<String>();
		for (String str : strs) {
			str = str.trim();
			list.add(str);
		}
		return list;
	}

	/**
	 * 字符串集合转成整型List
	 * 
	 * @param members
	 *            集合
	 * @return 转换后的整型List
	 */
	public static List<Integer> toIntList(Set<String> members) {
		if (SetUtil.isEmpty(members)) {
			return null;
		}
		List<Integer> result = new ArrayList<Integer>();
		Iterator<String> iterator = members.iterator();
		while (iterator.hasNext()) {
			String str = iterator.next();
			Integer newsId = Integer.parseInt(str);
			result.add(newsId);
		}
		return result;
	}

	/**
	 * 对整型集合中的元素添加前缀，保存到字符串数组并返回该数组
	 * 
	 * @param prefix
	 *            前缀
	 * @param idSet
	 *            整型集合
	 * @return 转换后的字符串数组
	 */
	public static String[] getIntKeys(String prefix, Set<Integer> idSet) {
		String[] keys = new String[idSet.size()];
		int index = 0;
		for (Integer id : idSet) {
			keys[index] = prefix + ":" + id;
			index++;
		}
		return keys;
	}

	/**
	 * 对字符串集合中的元素添加前缀，保存到数组并返回该数组
	 * 
	 * @param prefix
	 *            前缀
	 * @param usernameSet
	 *            字符串集合
	 * @return 转换后的字符串数组
	 */
	public static String[] getKeys(String prefix, Set<String> usernameSet) {
		String[] keys = new String[usernameSet.size()];
		int index = 0;
		for (String username : usernameSet) {
			keys[index] = prefix + ":" + username;
			index++;
		}
		return keys;
	}

	/**
	 * 整型数组转成字符串数组
	 * 
	 * @param nums
	 *            整型数组
	 * @return 字符串数组
	 */
	public static String[] toStringArray(int[] nums) {
		if (nums == null) {
			return null;
		}

		String[] fields = new String[nums.length];
		int index = 0;
		for (Integer num : nums) {
			fields[index] = Integer.toString(num);
			index++;
		}
		return fields;
	}

	/**
	 * 整型List转成字符串数组
	 * 
	 * @param list
	 *            整型List
	 * @return 字符串数组
	 */
	public static String[] toStringArray(List<Integer> list) {
		if (isEmpty(list)) {
			return null;
		}
		String[] fields = new String[list.size()];
		int index = 0;
		for (Integer num : list) {
			fields[index] = Integer.toString(num);
			index++;
		}
		return fields;
	}

	/**
	 * 字符串List转成字符串数组
	 * 
	 * @param list
	 *            字符串List
	 * @return 字符串数组
	 */
	public static String[] toArray(List<String> list) {
		if (isEmpty(list)) {
			return null;
		}
		String[] fields = new String[list.size()];
		int index = 0;
		for (String str : list) {
			fields[index] = str;
			index++;
		}
		return fields;
	}

	/**
	 * 字符串List转成整型List，可包含null元素
	 * 
	 * @param members
	 *            字符串List
	 * @return 整型List
	 */
	public static List<Integer> toIntList(List<String> members) {
		if (isEmpty(members)) {
			return null;
		}
		List<Integer> result = new ArrayList<Integer>();
		Iterator<String> iterator = members.iterator();
		while (iterator.hasNext()) {
			String str = iterator.next();
			if (str == null) {
				result.add(null);
			}
			else {
				int member = Integer.parseInt(str);
				result.add(member);
			}
		}
		return result;
	}

	/**
	 * 字符串List转成整型List，可包含null元素
	 * 
	 * @param members
	 *            字符串List
	 * @return 整型List
	 */
	public static List<Long> toLongList(List<String> members) {
		if (isEmpty(members)) {
			return null;
		}
		List<Long> result = new ArrayList<Long>();
		Iterator<String> iterator = members.iterator();
		while (iterator.hasNext()) {
			String str = iterator.next();
			if (str == null) {
				result.add(null);
			}
			else {
				long member = Long.parseLong(str);
				result.add(member);
			}
		}
		return result;
	}

	/**
	 * 生成包含指定前缀，从指定起始编号开始，大小为指定长度的字符串List
	 * 
	 * @param prefix
	 *            前缀
	 * @param start
	 *            起始编号
	 * @param size
	 *            长度
	 * @return 字符串List
	 */
	public static List<String> makeList(String prefix, int start, int size) {
		List<String> list = new ArrayList<String>();
		int end = start + size;
		for (int i = start; i < end; i++) {
			list.add(prefix + i);
		}
		return list;
	}

	/**
	 * 将文本(用“,”隔开)转换成长整型List
	 * 
	 * @param content
	 *            文本
	 * @return 长整型List
	 */
	public static List<Long> makeLongList(String content) {
		String[] strs = StringUtils.split(content, ",");
		List<Long> list = new ArrayList<Long>();
		for (String str : strs) {
			str = str.trim();
			long num = Long.parseLong(str);
			list.add(num);
		}
		return list;
	}

	public static List<Long> makeLongList(String content, String splitRegex) {
		String[] strs = content.split(splitRegex);
		List<Long> list = new ArrayList<Long>();
		for (String str : strs) {
			str = str.trim();
			long num = Long.parseLong(str);
			list.add(num);
		}
		return list;
	}

	/**
	 * 将文本(用“,”隔开)转换成整型List
	 * 
	 * @param content
	 *            文本
	 * @return 整型List
	 */
	public static List<Integer> makeIntList(String content) {
		String[] strs = StringUtils.split(content, ",");
		List<Integer> list = new ArrayList<Integer>();
		for (String str : strs) {
			str = str.trim();
			int num = Integer.parseInt(str);
			list.add(num);
		}
		return list;
	}

	/**
	 * 将文本(用“,”隔开)转换成Double List
	 * 
	 * @param content
	 *            文本
	 * @return 整型List
	 */
	public static List<Double> makeDoubleList(String content) {
		String[] strs = StringUtils.split(content, ",");
		List<Double> list = new ArrayList<Double>();
		for (String str : strs) {
			str = str.trim();
			double num = Double.parseDouble(str);
			list.add(num);
		}
		return list;
	}

	public static List<Double> makeDoubleList(double... nums) {
		List<Double> list = new ArrayList<Double>();
		for (double num : nums) {
			list.add(num);
		}
		return list;
	}

	/**
	 * 将文本(用“,”隔开)转换成字符串List
	 * 
	 * @param content
	 *            文本
	 * @return 字符串List
	 */
	public static List<String> makeList(String content) {
		String[] strs = StringUtils.split(content, ",");
		List<String> list = new ArrayList<String>();
		for (String str : strs) {
			str = str.trim();
			list.add(str);
		}
		return list;
	}

	/**
	 * 将整型List转换成字符串List
	 * 
	 * @param numList
	 *            整型List
	 * @return 字符串List
	 */
	public static List<String> toStringList(List<Integer> numList) {
		List<String> strList = new ArrayList<String>();
		for (Integer num : numList) {
			strList.add(Integer.toString(num));
		}
		return strList;
	}

	public static <T> List<T> sub(List<T> list, int start, int max) {
		if (ListUtil.isEmpty(list)) {
			return list;
		}
		List<T> newList = new ArrayList<T>();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i < start) {
				continue;
			}
			T element = list.get(i);
			newList.add(element);
			count++;
			if (count >= max) {
				break;
			}
		}
		return newList;
	}

	public static <T> List<T> sub(List<T> list, int max) {
		if (ListUtil.isEmpty(list)) {
			return list;
		}
		List<T> newList = new ArrayList<T>();
		int count = 0;
		for (T element : list) {
			newList.add(element);
			count++;
			if (count >= max) {
				break;
			}
		}
		return newList;
	}

	public static <T> List<T> removeAll(Collection<T> collection, Collection<T> remove) {
		List<T> list = new ArrayList<T>();
		for (Iterator<T> iter = collection.iterator(); iter.hasNext();) {
			T obj = iter.next();
			if (remove.contains(obj) == false) {
				list.add(obj);
			}
		}
		return list;
	}
}
