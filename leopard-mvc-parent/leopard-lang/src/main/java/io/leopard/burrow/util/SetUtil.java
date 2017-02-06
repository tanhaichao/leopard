package io.leopard.burrow.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * set工具类
 * 
 * @author Administrator
 * 
 */
public class SetUtil {
	/**
	 * 将形如1,2,3,4,5,6这样的字符串转换成int，存入set</br>
	 * 
	 * @param content
	 *            内容
	 * @return 生成的set<Integer>
	 */
	public static Set<Integer> makeIntSet(String content) {
		String[] strs = StringUtils.split(content, ",");
		Set<Integer> set = new LinkedHashSet<Integer>();
		for (String str : strs) {
			str = str.trim();
			int num = Integer.parseInt(str);
			set.add(num);
		}
		return set;
	}

	/**
	 * 将形如1,2,3,4,5,6这样的字符串转换成long，存入set</br>
	 * 
	 * @param content
	 *            内容
	 * @return 生成的set<Long>
	 */
	public static Set<Long> makeLongSet(String content) {
		String[] strs = StringUtils.split(content, ",");
		Set<Long> set = new LinkedHashSet<Long>();
		for (String str : strs) {
			str = str.trim();
			long num = Long.parseLong(str);
			set.add(num);
		}
		return set;
	}

	/**
	 * 将形如1,2,3,4,5,6这样的字符串转换成long，存入String</br>
	 * 
	 * @param content
	 *            内容
	 * @return 生成的set<String>
	 */
	public static Set<String> makeSet(String content) {
		String[] strs = StringUtils.split(content, ",");
		Set<String> set = new LinkedHashSet<String>();
		for (String str : strs) {
			str = str.trim();
			set.add(str);
		}
		return set;
	}

	/**
	 * 如果set为空，生成一个LinkedHashSet对象
	 * 
	 * @param set
	 * @return
	 */
	public static <T> Set<T> defaultSet(Set<T> set) {
		if (set == null) {
			return new LinkedHashSet<T>();
		}
		return set;
	}

	/**
	 * 返回set的大小，如果set为null，返回 0</br>
	 * 
	 * @param set
	 * @return
	 */
	public static int size(Set<?> set) {
		if (set == null) {
			return 0;
		}
		else {
			return set.size();
		}
	}

	/**
	 * 将Set<Integer>转换成数组</br>
	 * 
	 * @param set
	 * @return
	 */
	public static String[] toArray(Set<Integer> set) {
		String[] result = new String[set.size()];
		int index = 0;
		for (Integer num : set) {
			result[index] = Integer.toString(num);
			index++;
		}
		return result;
	}

	/**
	 * 将Set<String>转换成数组</br>
	 * 
	 * @param set
	 * @return
	 */
	public static String[] toArray2(Set<String> set) {
		String[] result = new String[set.size()];
		set.toArray(result);
		return result;
	}

	/**
	 * 判断一个set是否为非null</br>
	 * 
	 * @param set
	 * @return
	 */
	public static boolean isNotEmpty(Set<?> set) {
		if (set == null || set.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * 判断一个set是否为null</br>
	 * 
	 * @param set
	 * @return
	 */
	public static boolean isEmpty(Set<?> set) {
		if (set == null || set.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 生成带前缀的set内容</br>
	 * 
	 * @param prefix
	 *            前缀
	 * @param start
	 * @param size
	 * @return
	 */
	public static Set<String> makeSet(String prefix, int start, int size) {
		Set<String> set = new LinkedHashSet<String>();
		int end = start + size;
		for (int i = start; i < end; i++) {
			set.add(prefix + i);
		}
		return set;
	}

	/**
	 * 获取第一个元素.
	 * 
	 * @param set
	 * @return
	 */
	public static <T> T getFirstElement(Set<T> set) {
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		return set.iterator().next();
	}

	/**
	 * 交集.
	 * 
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static <T> Set<T> inter(Set<T> set1, Set<T> set2) {
		if (isEmpty(set1) || isEmpty(set2)) {
			return null;
		}
		Set<T> result = new LinkedHashSet<T>();
		result.addAll(set1);
		result.retainAll(set2);
		return result;
	}

	/**
	 * 差集
	 * 
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static <T> Set<T> diff(Set<T> set1, Set<T> set2) {
		if (isEmpty(set1)) {
			return set2;
		}
		if (isEmpty(set2)) {
			return set1;
		}
		Set<T> interSet = inter(set1, set2);
		Set<T> diffSet = new HashSet<T>();// 差集
		diffSet.addAll(set1);
		diffSet.addAll(set2);
		diffSet.removeAll(interSet);
		return diffSet;
	}

	public static <T> T elementAt(Set<T> set, int index) {
		Iterator<T> iterator = set.iterator();
		T result = null;
		for (int i = 0; i <= index; i++) {
			result = iterator.next();
		}
		return result;
	}

	public static List<Long> toLongList(Set<String> set) {
		if (set == null) {
			return null;
		}
		List<Long> list = new ArrayList<Long>();
		for (String str : set) {
			list.add(Long.parseLong(str));
		}
		return list;
	}

	public static List<String> toList(Set<String> set) {
		if (set == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String str : set) {
			list.add(str);
		}
		return list;
	}
}
