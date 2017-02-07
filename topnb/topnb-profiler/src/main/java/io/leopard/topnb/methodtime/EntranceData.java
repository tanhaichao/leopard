package io.leopard.topnb.methodtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import io.leopard.topnb.web.data.BaseData;
import io.leopard.topnb.web.data.CountDto;
import io.leopard.topnb.web.data.Data;
import io.leopard.web.freemarker.template.RequestHolder;

/**
 * 入口数据.
 * 
 * @author ahai
 * 
 */
public class EntranceData implements Data {

	private final BaseData data = new BaseData();

	@Override
	public boolean add(String name, double time, int count) {
		HttpServletRequest request = RequestHolder.getRequest();
		// System.err.println("add:" + entryName);
		if (request != null) {
			String entryName = request.getRequestURI();
			getDataByEntryName(entryName).add(name, time, count);
			this.incrCount(entryName);
		}
		data.add(name, time, count);
		return true;
	}

	private final Map<String, Long> countMap = new ConcurrentHashMap<String, Long>();
	private final Map<String, BaseData> map = new ConcurrentHashMap<String, BaseData>();

	public BaseData getBaseData(String entryName) {
		if (entryName == null || entryName.length() == 0) {
			return data;
		}
		else {
			return this.getDataByEntryName(entryName);
		}
	}

	protected BaseData getDataByEntryName(String entryName) {
		BaseData baseData = map.get(entryName);
		if (baseData == null) {
			baseData = new BaseData();
			map.put(entryName, baseData);
		}
		return baseData;
	}

	protected void incrCount(String entryName) {
		long count = this.getCount(entryName);
		count++;
		countMap.put(entryName, count);
		// System.err.println("incrCount:" + entryName + " count:" + count);
	}

	public long getCount(String entryName) {
		Long count = countMap.get(entryName);
		if (count == null) {
			return 0;
		}
		return count;
	}

	public void clear() {
		this.data.clear();
		countMap.clear();
	}

	@Override
	public CountDto remove(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, CountDto> clearAndReturn() {
		throw new UnsupportedOperationException();
	}

}
