package io.leopard.topnb.web.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法耗时数据.
 * 
 * 入口:全部、入口1、入口2、入口3 时间:全部、今天、小时、分钟 速度(耗时)、全部、慢、特慢
 * 
 * @author 阿海
 * 
 */
public class BaseData implements Data {

	protected Map<String, CountDto> DATA = new ConcurrentHashMap<String, CountDto>();

	@Override
	public boolean add(String name, double time, int count) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("方法名称不能为空.");
		}

		if (DATA.size() >= 10000) {
			RuntimeException e = new RuntimeException("怎么方法数量会超过10000?");
			e.printStackTrace();
			return false;
		}
		// 访问频率比较高，速度比较快，且准确性不要求那么高，所以不加同步代码.
		CountDto countDto = DATA.get(name);
		if (countDto == null) {
			countDto = new CountDto();
			countDto.setName(name);
			DATA.put(name, countDto);
			// System.out.println("put:" + name + " DATA:" + DATA);
		}
		countDto.setTime(countDto.getTime() + time);
		countDto.setCount(countDto.getCount() + count);
		return true;
	}

	public Map<String, CountDto> getData() {
		return DATA;
	}

	public CountDto get(String name) {
		return DATA.get(name);
	}

	public List<CountDto> listAll() {
		// System.out.println("DATA:" + DATA);
		Iterator<CountDto> iterator = DATA.values().iterator();
		List<CountDto> countList = new ArrayList<CountDto>();
		while (iterator.hasNext()) {
			CountDto countDto = iterator.next();
			countList.add(countDto);
		}
		return countList;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Map<String, CountDto> clearAndReturn() {
		// 原子操作问题可以忽略?
		Map<String, CountDto> map = DATA;
		DATA = new ConcurrentHashMap<String, CountDto>();
		return map;
	}

	@Override
	public CountDto remove(String name) {
		return DATA.remove(name);
	}

	public void clear() {
		DATA.clear();
	}
}
