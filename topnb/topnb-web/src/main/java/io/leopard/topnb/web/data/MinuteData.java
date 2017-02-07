package io.leopard.topnb.web.data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接池统计数据.
 * 
 * @author 阿海
 *
 */
public class MinuteData extends BaseData {

	private Map<String, MinuteCounter> counterMap = new ConcurrentHashMap<String, MinuteCounter>();

	public MinuteCounter getCounter(String name) {
		MinuteCounter minuteCounter = counterMap.get(name);
		if (minuteCounter == null) {
			minuteCounter = new MinuteCounter();
			counterMap.put(name, minuteCounter);
		}
		return minuteCounter;
	}

	public synchronized void addMinute(String name, double time, int count) {
		getCounter(name).add(time, count);
	}

	@Override
	public List<CountDto> listAll() {
		System.out.println("DATA:" + DATA);
		return super.listAll();
	}
}
