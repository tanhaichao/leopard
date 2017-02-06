package io.leopard.redis.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.redis.Redis;

/**
 * redis备份.
 * 
 * @author 阿海
 * 
 */
public class RedisBackup {

	protected Log logger = LogFactory.getLog(this.getClass());

	private static final String KEY = "system:last_backup_time";

	protected Date backupTime;

	private Redis redis;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public void setBackupTime(String backupTime) {
		String[] strs = backupTime.split(":");

		int hour = Integer.parseInt(strs[0]);
		int minute = Integer.parseInt(strs[1]);

		Calendar cal = Calendar.getInstance();
		// 每天的02:00.am开始执行
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);

		this.backupTime = cal.getTime();
	}

	/**
	 * 间隔多久算上次备份的时间(10分钟)
	 * 
	 * @return
	 */
	protected int getInterval() {
		// if (System.getProperty("os.name").startsWith("Windows")) {
		// return 2;
		// }
		return 10 * 60;
	}

	/**
	 * 获取最后备份时间.
	 * 
	 * @return
	 */
	public Date getLastBackupTime() {
		long time = System.currentTimeMillis();
		String lastBackupTime = redis.getSet(KEY, Long.toString(time));
		if (lastBackupTime == null || lastBackupTime.length() == 0) {
			return null;
		}
		long backupTime = Long.parseLong(lastBackupTime);
		if (backupTime <= 0) {
			return null;
		}
		return new Date(backupTime);
	}

	/**
	 * 是否上次备份时间.
	 * 
	 * @param lastBackupTime
	 * @return
	 */
	protected boolean isLastTime(Date lastBackupTime) {
		if (lastBackupTime == null) {
			return true;
		}
		long diff = System.currentTimeMillis() - lastBackupTime.getTime();
		if (diff > (this.getInterval() * 1000L)) {
			// 10分钟前的时间算是上次备份时间
			return true;
		}
		return false;
	}

	protected boolean bgsave() {
		String result = this.redis.bgsave();
		logger.info("redis backup,bgsave server:" + this.redis.getServerInfo() + " message:" + result);
		return true;
	}

	protected boolean bgrewriteaof() {
		String result = this.redis.bgrewriteaof();
		logger.info("redis backup,bgrewriteaof server:" + this.redis.getServerInfo() + " message:" + result);
		return true;
	}

	/**
	 * 备份.
	 * 
	 * @return
	 */
	public boolean backup() {
		Date lastBackupTime = this.getLastBackupTime();
		boolean isLastTime = this.isLastTime(lastBackupTime);
		if (!isLastTime) {
			// 别的redis连接已经备份过.
			return false;
		}
		this.bgsave();
		// this.bgrewriteaof();
		return true;
	}

	public void run(Redis redis) {
		logger.info("run redis:" + redis.getServerInfo());
		this.setRedis(redis);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

			}

		};
		new Timer().scheduleAtFixedRate(task, backupTime, 1 * 1000 * 60 * 60 * 24);// 每天执行
	}
}
