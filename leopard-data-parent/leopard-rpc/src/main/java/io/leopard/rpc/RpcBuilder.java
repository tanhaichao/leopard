package io.leopard.rpc;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.json.Json;

public class RpcBuilder {

	private String domain;
	private String uri;
	private String url;

	private long timeout;

	private Map<String, Object> params = new LinkedHashMap<String, Object>();

	public RpcBuilder(String domain, String uri) {
		this(domain, uri, 10000);
	}

	public RpcBuilder(String domain, String uri, long timeout) {
		this.domain = domain;
		this.uri = uri;
		this.url = domain + uri;
	}

	public RpcBuilder setDomain(String domain) {
		this.domain = domain;
		this.url = domain + uri;
		return this;
	}

	public RpcBuilder setUri(String uri) {
		this.uri = uri;
		this.url = domain + uri;
		return this;
	}

	public void setString(String name, String value) {
		if (value == null) {
			return;
		}
		this.params.put(name, value);
	}

	public void setFloat(String name, float value) {
		this.params.put(name, value);
	}

	public void setDouble(String name, double value) {
		this.params.put(name, value);
	}

	public void setLong(String name, long value) {
		this.params.put(name, value);
	}

	public void setDate(String name, Date date) {
		if (date == null) {
			return;
		}
		this.params.put(name, date.getTime());
	}

	public void setInt(String name, int value) {
		this.params.put(name, value);
	}

	public void setList(String name, List<?> list) {
		this.params.put(name, Json.toJson(list));
	}

	public <T> T doPost(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) RpcClient.doPostForData(url, params, timeout);
		String json = Json.toJson(map);
		return Json.toObject(json, clazz, true);
	}

	public <T> List<T> doPostForList(Class<T> clazz) {
		return RpcClient.doPostForList(url, params, clazz, timeout);
	}

	@Deprecated
	public Boolean getForBoolean() {
		return (Boolean) RpcClient.doPostForObject(url, params, timeout);
	}

	@Deprecated
	public Long getForLong() {
		return (Long) RpcClient.doPostForObject(url, params, timeout);
	}

	@Deprecated
	public Double getForDouble() {
		return (Double) RpcClient.doPostForObject(url, params, timeout);
	}

	public String queryForString() {
		return (String) RpcClient.doPostForData(url, params, timeout);
	}

	public Boolean queryForBoolean() {
		return (Boolean) RpcClient.doPostForObject(url, params, timeout);
	}

	public Long queryForLong() {
		Number number = (Number) RpcClient.doPostForData(url, params, timeout);
		if (number == null) {
			return null;
		}
		return number.longValue();
	}

	public Integer queryForInteger() {
		return (Integer) RpcClient.doPostForData(url, params, timeout);
	}

	public Float queryForFloat() {
		return (Float) RpcClient.doPostForObject(url, params, timeout);
	}

	public Double queryForDouble() {
		return (Double) RpcClient.doPostForObject(url, params, timeout);
	}

	public List<Integer> queryForInts() {
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>) RpcClient.doPostForData(url, params, timeout);
		return list;
	}

	public List<String> queryForStrings() {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) RpcClient.doPostForData(url, params, timeout);
		return list;
	}

	public List<Long> queryForLongs() {
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) RpcClient.doPostForData(url, params, timeout);
		return list;
	}
}
