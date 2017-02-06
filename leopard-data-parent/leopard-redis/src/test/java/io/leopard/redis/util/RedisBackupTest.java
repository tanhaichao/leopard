package io.leopard.redis.util;

import io.leopard.redis.util.RedisBackup;

import org.junit.Assert;
import org.junit.Test;

public class RedisBackupTest {

	protected RedisBackup redisBackup = new RedisBackup();

	@Test
	public void RedisBackup() {
		// AUTO
	}

	@Test
	public void setRedis() {

	}

	@Test
	public void getInterval() {
		// if (SystemUtils.IS_OS_WINDOWS) {
		// Assert.assertEquals(2, redisBackup.getInterval());
		// }
		// else {
		Assert.assertEquals(10 * 60, redisBackup.getInterval());
		// }
	}

	// @Test
	// public void getPeriod() {
	// Assert.assertTrue(this.redisBackup.getPeriod() instanceof PerDayPeriod);
	// }

	@Test
	public void getLastBackupTime() {

	}

	@Test
	public void isLastTime() {

	}

	@Test
	public void bgsave() {

	}

	@Test
	public void bgrewriteaof() {

	}

	@Test
	public void backup() {

	}

	// @Test
	// public void isEnabled() {
	// Assert.assertTrue(this.redisBackup.isEnabled());
	// }

	@Test
	public void start() {

	}

	// @Test
	// public void getThreadCount() {
	// Assert.assertEquals(1, this.redisBackup.getThreadCount());
	// }

	@Test
	public void run() {

	}

}