package io.leopard.redis.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisListImpl implements IRedisList {

	private Map<String, List<String>> data = new ConcurrentHashMap<String, List<String>>();

	protected List<String> getList(String key) {
		List<String> list = this.data.get(key);
		if (list == null) {
			list = new ArrayList<String>();
			data.put(key, list);
		}
		return list;
	}

	protected List<String> revlist(String key) {
		List<String> result = new ArrayList<String>();
		List<String> list = this.getList(key);
		for (String element : list) {
			result.add(0, element);
		}
		return result;
	}

	@Override
	public Long rpush(String key, String... strings) {
		for (String str : strings) {
			this.getList(key).add(0, str);
		}
		return this.llen(key);
	}

	@Override
	public Long lpush(String key, String... strings) {
		for (String str : strings) {
			this.getList(key).add(str);
		}
		return this.llen(key);
	}

	@Override
	public Long llen(String key) {
		return (long) this.getList(key).size();
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		List<String> result = new ArrayList<String>();
		List<String> list = this.revlist(key);
		long index = 0;
		for (String element : list) {
			boolean checkIndex = this.checkIndex(start, end, index);
			// System.out.println("checkIndex:" + checkIndex + " index:" + index
			// + " element:" + element);
			if (checkIndex) {
				result.add(element);
			}
			index++;
		}
		return result;
	}

	protected boolean checkIndex(long start, long end, long index) {
		if (index < start) {
			return false;
		}
		if (end == -1) {
			return true;
		}
		if (index <= end) {
			return true;
		}
		return false;
	}

	@Override
	public String ltrim(String key, long start, long end) {
		List<String> result = new ArrayList<String>();
		List<String> list = this.revlist(key);
		long index = 0;
		for (String element : list) {
			boolean checkIndex = this.checkIndex(start, end, index);
			// System.out.println("checkIndex:" + checkIndex + " index:" + index
			// + " element:" + element);
			if (checkIndex) {
				result.add(0, element);
			}
			index++;
		}
		if (result.isEmpty()) {
			this.data.remove(key);
		}
		else {
			this.data.put(key, result);
		}
		return "OK";
	}

	@Override
	public String lindex(String key, long index) {
		List<String> list = this.getList(key);
		if (index >= list.size()) {
			return null;
		}
		else if (index < 0) {
			index = list.size() + index;
		}
		return list.get((int) index);
	}

	@Override
	public String lset(String key, long index, String value) {
		List<String> list = this.revlist(key);
		// System.out.println("list:" + list);
		if (index < 0) {
			index = list.size() + index;
		}
		list.set((int) index, value);
		List<String> result = new ArrayList<String>();
		for (String str : list) {
			result.add(0, str);
		}
		this.data.put(key, result);
		return "OK";
	}

	@Override
	public Long lrem(String key, long count, String value) {
		List<String> list = this.getList(key);
		if (list == null || list.size() == 0) {
			return 0L;
		}
		long deletedCount = 0;
		if (count == 0) {
			deletedCount = this.lrem0(key, count, value);
		}
		else if (count > 0) {
			deletedCount = this.lrem1(key, count, value);
		}
		else if (count < 0) {
			deletedCount = this.lrem2(key, -count, value);
		}
		else {
			throw new IllegalArgumentException("未知类型[" + count + "].");
		}

		// System.out.println("list:" + list.size() + " result:" +
		// result.size());
		return deletedCount;
	}

	protected long lrem0(String key, long count, String value) {
		List<String> list = this.getList(key);
		List<String> result = new ArrayList<String>();
		long deletedCount = 0;
		for (String member : list) {
			if (member.equals(value)) {
				deletedCount++;
			}
			else {
				result.add(0, member);
			}

		}
		this.data.put(key, result);
		return deletedCount;
	}

	protected long lrem1(String key, long count, String value) {
		List<String> list = this.getList(key);
		List<String> result = new ArrayList<String>();
		long deletedCount = 0;
		for (String member : list) {
			System.out.println("member:" + member);
			if (member.equals(value)) {
				if (deletedCount >= count) {
					result.add(0, member);
				}
				else {
					// System.out.println("member:" + member + " deletedCount:"
					// + deletedCount);
					deletedCount++;
				}
			}
			else {
				result.add(member);
			}
		}
		System.out.println("result:" + result);
		this.data.put(key, result);
		return deletedCount;
	}

	protected long lrem2(String key, long count, String value) {
		List<String> list = this.getList(key);
		List<String> result = new ArrayList<String>();
		System.out.println("count:" + count);
		long deletedCount = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			String member = list.get(i);
			if (member.equals(value)) {
				if (deletedCount >= count) {
					result.add(member);
				}
				else {
					deletedCount++;
				}
			}
			else {
				result.add(member);
			}
			// if (result.size() >= max) {
			// System.out.println("max:" + max + " size:" + result.size());
			// break;
			// }
		}
		this.data.put(key, result);
		return deletedCount;
	}

	@Override
	public String lpop(String key) {
		List<String> list = this.getList(key);
		if (list.isEmpty()) {
			return null;
		}
		String element = list.remove(list.size() - 1);
		return element;
	}

	@Override
	public String rpop(String key) {
		List<String> list = this.getList(key);
		if (list.isEmpty()) {
			return null;
		}
		String element = list.remove(0);
		return element;
	}

	@Override
	public Boolean exists(String key) {
		return data.containsKey(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		if (!this.exists(key)) {
			return 0L;
		}
		return 1L;
	}

	@Override
	public Long del(String key) {
		Object value = data.remove(key);

		if (value == null) {
			return 0L;
		}
		else {
			return 1L;
		}
	}

	@Override
	public boolean flushAll() {
		data.clear();
		return true;
	}

	@Override
	public Long lpushx(String key, String string) {
		if (!data.containsKey(key)) {
			return 0L;
		}
		this.lpush(key, string);
		return (long) this.getList(key).size();
	}

	@Override
	public Long rpushx(String key, String string) {
		if (!data.containsKey(key)) {
			return 0L;
		}
		this.rpush(key, string);
		return (long) this.getList(key).size();
	}
}
