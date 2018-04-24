package io.leopard.monitor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.util.ListUtil;
import io.leopard.jdbc.LeopardBeanFactoryAware;
import io.leopard.monitor.alarm.AlarmModule;
import io.leopard.monitor.alarm.AlarmService;
import io.leopard.monitor.model.MonitorConfig;
import io.leopard.monitor.model.RedisInfo;
import io.leopard.redis.Redis;
import io.leopard.redis.RedisImpl;

public class RedisMonitor implements IMonitor {

	protected Log logger = LogFactory.getLog(this.getClass());

	// @Autowired
	private AlarmService alarmService = AlarmModule.getAlarmService();
	@Autowired
	private MonitorConfig monitorConfig;

	private final Map<String, Redis> redisMap = new ConcurrentHashMap<String, Redis>();

	@Override
	public void monitor() {
		List<RedisInfo> redisInfoList = monitorConfig.getRedisInfoList();
		redisInfoList = ListUtil.defaultList(redisInfoList);
		for (RedisInfo redisInfo : redisInfoList) {
			try {
				this.check(redisInfo);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	protected Redis getRedis(String server) {
		Redis redis = redisMap.get(server);
		if (redis != null) {
			return redis;
		}
		redis = new RedisImpl(server, 1, 0, false, 0);
		redis.init();
		redisMap.put(server, redis);
		return redis;
	}

	protected void check(RedisInfo redisInfo) {
		// 使用缓存
		Redis redis;
		if (StringUtils.isNotEmpty(redisInfo.getRedisRef())) {
			redis = (Redis) LeopardBeanFactoryAware.getBeanFactory().getBean(redisInfo.getRedisRef());
			AssertUtil.assertNotNull(redis, "redis对象[" + redisInfo.getRedisRef() + "]不存在.");
		}
		else {
			redis = this.getRedis(redisInfo.getServer());
		}
		long usedMemory = redis.getUsedMemory();
		long maxMemory = redisInfo.parseMaxMemory();
		if (usedMemory > maxMemory) {
			this.alarmService.send("Redis内存异常,当前[" + CapacityUtil.human(usedMemory) + "] 阀值[" + redisInfo.getMaxMemory() + "]");
		}
		// String message = "";
		// message += "redisInfo server:" + redis.getServerInfo();
		// message += " usedMemory:" + usedMemory + "(" + CapacityUtil.human(usedMemory) + ")";
		// message += " maxMemory:" + redisInfo.getMaxMemory();
		// message += " " + redisInfo.parseMaxMemory();
		// System.out.println(message);
	}

	@PreDestroy
	public void destroy() {
		Collection<Redis> redisList = redisMap.values();
		for (Redis redis : redisList) {
			redis.destroy();
		}
	}
}
