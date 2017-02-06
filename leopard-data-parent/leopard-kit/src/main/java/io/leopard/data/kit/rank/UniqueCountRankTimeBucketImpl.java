package io.leopard.data.kit.rank;

import java.util.Date;

public class UniqueCountRankTimeBucketImpl extends CountRankTimeBucketImpl implements UniqueCountRank {

	protected String getUniqueKey() {
		return this.key + ":unique";
	}

	protected String getUniqueId(String member, String id) {
		return member + ":" + id;
	}

	@Override
	public long incr(String member, String id, long count, Date posttime) {
		String key = getUniqueKey();
		String uniqueId = this.getUniqueId(member, id);

		Double time = redis.zscore(key, uniqueId);
		System.out.println("time:" + time + " id:" + id);
		if (isCurrentTimeBucket(time)) {
			// 当前时间段已经操作过
			return 0;
		}
		long result = this.incr(member, count, posttime);

		redis.zadd(key, System.currentTimeMillis(), uniqueId);
		return result;
	}

	protected long getTimeBucketMillis() {
		if (timeBucket == TimeBucket.DAY) {
			return 1000L * 60 * 60 * 24;
		}
		else {
			throw new IllegalArgumentException("未知时间段[" + timeBucket + "].");
		}
	}

	protected boolean isCurrentTimeBucket(Double time) {
		if (time == null) {
			return false;
		}

		long timeBucketMillis = this.getTimeBucketMillis();
		long millls = System.currentTimeMillis() - time.longValue();
		return millls < timeBucketMillis;
	}

	@Override
	public boolean delete(String member, String id) {
		String key = getUniqueKey();
		String uniqueId = this.getUniqueId(member, id);
		Double time = redis.zscore(key, uniqueId);
		if (!isCurrentTimeBucket(time)) {
			// 当前时间段已经操作过
			return false;
		}
		Long num = redis.zrem(key, uniqueId);
		return num != null && num > 0;
	}

}
