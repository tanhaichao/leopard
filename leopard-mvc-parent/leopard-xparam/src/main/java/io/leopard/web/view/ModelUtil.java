package io.leopard.web.view;

import io.leopard.json.Json;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

/**
 * MVC视图.
 * 
 * @author 阿海
 * 
 */
public class ModelUtil {

	/**
	 * 获取模型的属性值.
	 * 
	 * @param model
	 *            模型
	 * @param name
	 *            属性名
	 * @return
	 */
	protected static Object get(ModelAndView model, String name) {
		Object obj = model.getModel().get(name);
		if (obj == null) {
			if (!model.getModel().containsKey(name)) {
				throw new RuntimeException("Model没有[" + name + "]属性.");
			}
		}
		return obj;
	}

	/**
	 * 将模型转成对象.
	 * 
	 * @param model
	 *            模型
	 * @param clazz
	 *            类型
	 * @return
	 */
	public static <T> T toObject(ModelAndView model, Class<T> clazz) {
		String json = toJson(model);

		return Json.toObject(json, clazz);
	}

	/**
	 * 将模型转成json数据.
	 * 
	 * @param model
	 * @return
	 */
	public static String toJson(ModelAndView model) {
		String json = (String) get(model, "message");
		if (json == null || json.length() == 0) {
			throw new RuntimeException("获取不到json数据.");
		}
		return json;
	}

	/**
	 * 判断controller请求是否成功更新数据..
	 * 
	 * @param model
	 * @return
	 */
	public static boolean isSuccess(ModelAndView model) {
		Boolean success = (Boolean) get(model, "success");
		return success;
	}

	/**
	 * 获取整型值.
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	public static int getInt(ModelAndView model, String name) {
		Integer num = (Integer) get(model, name);
		// return NumberUtil.toInt(num);
		if (num == null) {
			return 0;
		}
		else {
			return num;
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * 获取List.
	 * @param model
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static <T> List<T> getList(ModelAndView model, Class<T> clazz, String name) {
		Object obj = get(model, name);
		return (List<T>) obj;
	}

	/**
	 * 获取List.
	 * 
	 * @param model
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static <T> List<T> getListForJson(ModelAndView model, Class<T> clazz, String name) {
		String json = (String) get(model, name);
		return (List<T>) Json.toListObject(json, clazz);
	}
}
