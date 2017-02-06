package io.leopard.redis;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Redis Info指令数据结构.
 * 
 * @author 阿海
 * 
 */
public class RedisInfo {
	private Map<String, String> map = null;

	public RedisInfo(String info) {
		// this.map = map;
		this(parse(info));
	}

	protected static Map<String, String> parse(String info) {
		String regex = "(.*?):(.+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(info);
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (m.find()) {
			String key = m.group(1);
			String value = m.group(2);
			map.put(key, value);
		}
		return map;
	}

	// redis_version:2.2.11
	// redis_git_sha1:00000000
	// redis_git_dirty:0
	// arch_bits:64
	// multiplexing_api:epoll
	// process_id:16905
	// uptime_in_seconds:450985
	// uptime_in_days:5
	// lru_clock:1077814
	// used_cpu_sys:44.47
	// used_cpu_user:78.93
	// used_cpu_sys_childrens:1.66
	// used_cpu_user_childrens:3.18
	// connected_clients:3
	// connected_slaves:0
	// client_longest_output_list:0
	// client_biggest_input_buf:0
	// blocked_clients:0
	// used_memory:1863960
	// used_memory_human:1.78M
	// used_memory_rss:18255872
	// mem_fragmentation_ratio:9.79
	// use_tcmalloc:0
	// loading:0
	// aof_enabled:0
	// changes_since_last_save:0
	// bgsave_in_progress:0
	// last_save_time:1311008124
	// bgrewriteaof_in_progress:0
	// total_connections_received:255
	// total_commands_processed:2979504
	// expired_keys:100744
	// evicted_keys:0
	// keyspace_hits:1451533
	// keyspace_misses:100942
	// hash_max_zipmap_entries:512
	// hash_max_zipmap_value:64
	// pubsub_channels:0
	// pubsub_patterns:0
	// vm_enabled:0
	// role:master
	// allocation_stats:6=1,7=1,8=14762,9=343,10=721,11=1535578,12=120518,13=1427726,14=1021,15=2978288,16=9129693,17=130608,18=300428,19=2807499,20=8,21=66,22=41,23=217,24=5994665,25=274,26=7,27=1224,28=6,29=90197,30=5,31=2,32=31,33=744,34=6,35=1,36=5,37=254,38=7,39=14,40=256,41=3,42=2,43=2,44=2,45=2046,46=1,47=1,48=1061,49=271,50=1,51=1,52=1,53=90336,55=888,56=5,57=95,58=3,59=5,61=98,62=1,63=1,64=17,65=3,66=1,67=1,68=4,69=739,70=2,71=3,72=39,73=7,74=3,75=3,76=3,77=25,78=7,79=7,80=13,81=20,82=20,83=9,84=25,85=18,86=15,87=12,88=328,89=15,90=3,95=2,96=33,97=269,99=2378,103=4,109=982,110=20,111=91,112=900,113=9000,115=1,120=33,121=95,128=16,141=728,144=33,168=29,169=1,192=29,193=269,197=333,207=2045,216=27,217=981,223=8,225=7783,227=900,229=1307,240=27,241=95,>=256=2846998
	// db0:keys=3,expires=0
	public RedisInfo(Map<String, String> map) {
		this.map = map;
	}

	public long getUsedMemory() {
		return Long.parseLong(map.get("used_memory"));
	}

	public String getUsedMemoryHuman() {
		return map.get("used_memory_human");
	}

	public int getKeyCount() {
		// db0:keys=3,expires=0
		String dbinfo = map.get("db0").trim();
		return getKeyCount(dbinfo);
	}

	protected int getKeyCount(String dbinfo) {
		// System.out.println("dbinfo:" + dbinfo);
		String regex = "keys=([0-9]+),";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(dbinfo);
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		}
		else {
			return -1;
		}
	}

	public int getExpiredKeys() {
		return Integer.parseInt(map.get("expired_keys"));
	}

	public int getEvictedKeys() {
		return Integer.parseInt(map.get("evicted_keys"));
	}

	public float getUsedCpuUser() {
		return Float.parseFloat(map.get("used_cpu_user"));
	}

	public float getUsedCpuSys() {
		return Float.parseFloat(map.get("used_cpu_sys"));
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
