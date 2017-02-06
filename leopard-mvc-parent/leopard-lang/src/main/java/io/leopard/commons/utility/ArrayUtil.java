package io.leopard.commons.utility;

/**
 * <b>数组操作工具类</b>
 * 
 * @author Administrator
 * 
 */
public class ArrayUtil {

	/**
	 * 截取数组个数</br> 注意：截取位置从0开始
	 * 
	 * @param args
	 *            完整的数组列表
	 * @param size
	 *            需要截取的个数
	 * @return 截取后的数组
	 */
	public static Object[] sub(Object[] args, int size) {
		Object[] args2 = new Object[size];
		System.arraycopy(args, 0, args2, 0, size);
		return args2;
	}

	/**
	 * 移除数组的最后一个元素</br>
	 * 
	 * @param args
	 *            数组
	 * @return 移除后的数组
	 */
	public static Object[] removeLast(Object[] args) {
		Object[] args2 = new Object[args.length - 1];
		System.arraycopy(args, 0, args2, 0, args2.length);
		return args2;
	}

	/**
	 * 移除数组的第一个元素</br>
	 * 
	 * @param args
	 *            数组
	 * @return 移除后的数组
	 */
	public static String[] removeFirst(String[] args) {
		String[] args2 = new String[args.length - 1];
		System.arraycopy(args, 1, args2, 0, args2.length);
		return args2;
	}

	/**
	 * 插入元素到数组的第一个位置</br> 注意：该方法只对字符串有效</br>
	 * 
	 * @param array
	 *            数组
	 * @param element
	 *            元素
	 * @return 插入后的数组
	 */
	public static String[] insertFirst(String[] array, String element) {
		String[] args = new String[array.length + 1];
		System.arraycopy(array, 0, args, 1, array.length);
		args[0] = element;
		return args;
	}

	/**
	 * 生成数组</br> 格式为：perfix+i</br> 例如：prefix传入leopard,数量传入3;</br> 返回的数组为：["leopard0","leopard1","leopard2"]</br>
	 * 
	 * @param prefix
	 *            数组内容前缀
	 * @param count
	 *            数量
	 * @return 生成的数组
	 */
	public static String[] make(String prefix, int count) {
		String[] members = new String[count];
		for (int i = 0; i < members.length; i++) {
			members[i] = prefix + i;
		}
		return members;
	}

	public static String[] append(String[] args, String value) {
		String[] args2 = new String[args.length + 1];
		for (int i = 0; i < args.length; i++) {
			args2[i] = args[i];
		}
		args2[args2.length - 1] = value;
		return args2;
	}
}
