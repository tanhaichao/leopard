package io.leopard.data.signature;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.util.NumberUtil;
import io.leopard.burrow.util.ObjectUtil;
import io.leopard.redis.Redis;

public class SignatureDaoRedisImpl implements SignatureDao {

	private Redis redis;

	private String redisKey;

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	private static final int TIMEOUT_SECONDS = 30 * 60;// 半个小时过期

	protected String getKey(String member) {
		return this.redisKey + ":" + member;
	}

	@Override
	public boolean add(String member) {
		AssertUtil.assertNotEmpty(redisKey, "参数redisKey不能为空.");
		String key = this.getKey(member);
		this.redis.set(key, "", TIMEOUT_SECONDS);
		return true;
	}

	@Override
	public boolean exist(String member) {
		String key = this.getKey(member);
		String value = this.redis.get(key);
		// return StringUtils.isNotEmpty(value);
		return ObjectUtil.isNotNull(value);
	}

	@Override
	public boolean remove(String member) {
		String key = this.getKey(member);
		Long result = this.redis.del(key);
		return NumberUtil.toBool(result);
	}

}
