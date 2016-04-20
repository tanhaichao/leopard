package io.leopard.data4j.log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.helpers.LogLog;

public class DailyAutoRollingFileAppender extends DailyRollingFileAppender implements LogRollOver {

	private Field nextCheckField;
	private Field nowField;
	private Field rcField;
	private Field scheduledFilenameField;

	@Override
	public void activateOptions() {
		super.activateOptions();
		try {
			this.initFields();
		}
		catch (Exception e) {
			LogLog.error("DailyAutoRollingFileAppender init failed." + e.getMessage(), e);
		}
		LogRollingTimer.start(this);
		// System.out.println(this.getScheduledFilename());
	}

	@Override
	protected void reset() {
		super.reset();
		LogRollingTimer.stop(this);
	}

	protected void initFields() throws Exception {
		nextCheckField = DailyRollingFileAppender.class.getDeclaredField("nextCheck");
		nowField = DailyRollingFileAppender.class.getDeclaredField("now");
		rcField = DailyRollingFileAppender.class.getDeclaredField("rc");
		scheduledFilenameField = DailyRollingFileAppender.class.getDeclaredField("scheduledFilename");

		nextCheckField.setAccessible(true);
		nowField.setAccessible(true);
		rcField.setAccessible(true);
		scheduledFilenameField.setAccessible(true);
	}

	protected String getScheduledFilename() {
		try {
			return (String) scheduledFilenameField.get(this);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void autoRollOver() throws Exception {
		long nextCheck = nextCheckField.getLong(this);
		// System.out.println("autoRollOver:" + nextCheck + " fileName:" + fileName + " fileanme:" + fileanme);

		long n = System.currentTimeMillis();
		if (n >= nextCheck) {
			Date now = (Date) nowField.get(this);
			now.setTime(n);
			nextCheck = this.getNextCheck(now);
			nextCheckField.set(this, nextCheck);

			String scheduledFilename = this.getScheduledFilename();

			try {
				this.invokeSuperRollOver();
			}
			catch (Exception ioe) {
				LogLog.error("rollOver() failed.", ioe);
			}

			try {
				this.gzip(scheduledFilename);
			}
			catch (Exception e) {
				LogLog.error("gzip failed.", e);
			}

		}
		// System.out.println("autoRollOver:" + nextCheck + " end.");
	}

	protected boolean isNeedGzipFile() {
		if (super.fileName.endsWith("all.log")) {
			return true;
		}
		if (super.fileName.endsWith("redis.log")) {
			return true;
		}
		return false;
	}

	protected void gzip(String scheduledFilename) {
		{
			if (!SystemUtils.IS_OS_LINUX) {
				return;
			}
			if (!this.isNeedGzipFile()) {
				return;
			}
			File file = new File(scheduledFilename);
			if (!file.exists()) {
				return;
			}
		}
		String gzipFilename = scheduledFilename + ".gz";
		File file = new File(gzipFilename);
		if (file.exists()) {
			return;
		}
		System.out.println("gzip:" + scheduledFilename);
		// String cmd = "/bin/gzip -f " + scheduledFilename;
		// SystemUtil.execShell(cmd, true);

		// 未做日志压缩
	}

	protected void invokeSuperRollOver() throws Exception {
		Method method = DailyRollingFileAppender.class.getDeclaredMethod("rollOver");
		method.setAccessible(true);
		method.invoke(this, new Object[] {});
	}

	protected long getNextCheck(Date now) throws Exception {
		Object rc = rcField.get(this);
		Method method = rc.getClass().getDeclaredMethod("getNextCheckMillis", Date.class);
		method.setAccessible(true);
		long nextCheck = (Long) method.invoke(rc, now);
		return nextCheck;
	}

	@Override
	protected void closeFile() {
		// System.out.println("closeFile:" + super.fileName);
		super.closeFile();
	}

	@Override
	public String getFilename() {
		return this.fileName;
	}
}
