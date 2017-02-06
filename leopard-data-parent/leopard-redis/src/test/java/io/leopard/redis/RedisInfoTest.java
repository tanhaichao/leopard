package io.leopard.redis;

import io.leopard.redis.RedisInfo;

import org.junit.Assert;
import org.junit.Test;

public class RedisInfoTest {

	@Test
	public void getUsedMemory() {

	}

	@Test
	public void RedisInfo() {
		String info = "redis_version:2.4.8\n";
		info += "redis_git_sha1:00000000\n";
		info += "redis_git_dirty:0\n";
		info += "arch_bits:64\n";
		info += "multiplexing_api:epoll\n";
		info += "gcc_version:4.4.1\n";
		info += "process_id:31451\n";
		info += "uptime_in_seconds:11152520\n";
		info += "uptime_in_days:129\n";
		info += "lru_clock:1287417\n";
		info += "used_cpu_sys:3303.28\n";
		info += "used_cpu_user:1415.62\n";
		info += "used_cpu_sys_children:0.00\n";
		info += "used_cpu_user_children:0.00\n";
		info += "connected_clients:21\n";
		info += "connected_slaves:0\n";
		info += "client_longest_output_list:0\n";
		info += "client_biggest_input_buf:0\n";
		info += "blocked_clients:0\n";
		info += "used_memory:928488\n";
		info += "used_memory_human:906.73K\n";
		info += "used_memory_rss:2383872\n";
		info += "used_memory_peak:1537312\n";
		info += "used_memory_peak_human:1.47M\n";
		info += "mem_fragmentation_ratio:2.57\n";
		info += "mem_allocator:jemalloc-2.2.5\n";
		info += "loading:0\n";
		info += "aof_enabled:1\n";
		info += "changes_since_last_save:73714\n";
		info += "bgsave_in_progress:0\n";
		info += "last_save_time:1364870451\n";
		info += "bgrewriteaof_in_progress:0\n";
		info += "total_connections_received:22420\n";
		info += "total_commands_processed:149187\n";
		info += "expired_keys:2829\n";
		info += "evicted_keys:0\n";
		info += "keyspace_hits:22229\n";
		info += "keyspace_misses:54190\n";
		info += "pubsub_channels:0\n";
		info += "pubsub_patterns:0\n";
		info += "latest_fork_usec:0\n";
		info += "vm_enabled:0\n";
		info += "role:master\n";
		info += "aof_current_size:5005742\n";
		info += "aof_base_size:2277360\n";
		info += "aof_pending_rewrite:0\n";
		info += "aof_buffer_length:0\n";
		info += "aof_pending_bio_fsync:0\n";
		info += "db0:keys=137,expires=16";

		RedisInfo redisInfo = new RedisInfo(info);
		Assert.assertEquals(137, redisInfo.getKeyCount());
		Assert.assertEquals(928488, redisInfo.getUsedMemory());
		Assert.assertEquals(2829, redisInfo.getExpiredKeys());
		Assert.assertEquals(0, redisInfo.getEvictedKeys());
		Assert.assertEquals(1415.62f, redisInfo.getUsedCpuUser(), 0);
		Assert.assertEquals(3303.28f, redisInfo.getUsedCpuSys(), 0);
		Assert.assertEquals("906.73K", redisInfo.getUsedMemoryHuman());

		Assert.assertNotNull(redisInfo.toString());

		Assert.assertEquals(-1, redisInfo.getKeyCount(""));
	}

	@Test
	public void getKeyCount() {

	}
}