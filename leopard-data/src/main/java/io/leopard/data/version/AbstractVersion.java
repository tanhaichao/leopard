package io.leopard.data.version;

import io.leopard.burrow.util.NumberUtil;
import io.leopard.json.Json;
import io.leopard.redis.Redis;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容临时(审核、预览)版本实现.
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 * @param <KEYTYPE>
 */
public abstract class AbstractVersion<BEAN, KEYTYPE> {
	protected Redis redis;
	protected String redisKey;
	protected Class<BEAN> beanType;

	public List<BEAN> listVersion() {
		List<String> jsonList = redis.hvals(redisKey);
		if (jsonList == null || jsonList.isEmpty()) {
			return null;
		}
		List<BEAN> list = new ArrayList<BEAN>();
		for (String json : jsonList) {
			list.add(Json.toObject(json, beanType));
		}
		return list;
	}

	private static final ThreadLocal<Boolean> VERSION_INFO = new ThreadLocal<Boolean>();

	public static void setVersion(boolean isVersion) {
		VERSION_INFO.set(isVersion);
	}

	/**
	 * 是否使用临时版本?
	 * 
	 * @return
	 */
	public static boolean isVersion() {
		Boolean version = VERSION_INFO.get();
		if (version == null) {
			return false;
		}
		return version;
	}

	protected BEAN getBean(KEYTYPE key) {
		String json = redis.hget(redisKey, key.toString());
		try {
			return Json.toObject(json, beanType);
		}
		catch (RuntimeException e) {
			System.err.println("redis:" + redis.getServerInfo() + " key:" + redisKey);
			System.err.println("json:" + json);
			throw e;
		}
	}

	public boolean removeBean(Integer recallId) {
		Long result = redis.hdel(redisKey, recallId);
		return NumberUtil.toBool(result);
	}

	protected boolean update(KEYTYPE key, BEAN bean) {
		String json = Json.toJson(bean);
		Long result = this.redis.hset(redisKey, key.toString(), json);
		return NumberUtil.toBool(result);
	}

	/**
	 * 使用新版本内容替换列表.
	 * 
	 * @param beanList
	 */
	protected void filterList(List<BEAN> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return;
		}
		if (isVersion()) {
			for (BEAN bean : beanList) {
				this.replcale(bean);
			}
		}
	}

	/**
	 * 使用新版本内容替换.
	 * 
	 * @param bean
	 */
	protected void filter(BEAN bean) {
		if (bean == null) {
			return;
		}
		if (isVersion()) {
			this.replcale(bean);
		}
	}

	protected abstract void replcale(BEAN bean);

}
