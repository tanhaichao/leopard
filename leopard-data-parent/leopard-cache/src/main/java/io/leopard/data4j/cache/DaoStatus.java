package io.leopard.data4j.cache;

public class DaoStatus {
	//	private static final Log logger = LogFactory.getLog(DaoStatus.class);

	//	public static final Object NONE = "NONE";

	private Object mysqlStatus = null;
	private Object memcachedStatus = null;
	private Object tcStatus = null;

	public Object getMysqlStatus() {
		return mysqlStatus;
	}

	public void setMysqlStatus(Object mysqlStatus) {
		this.mysqlStatus = mysqlStatus;
	}

	public Object getMemcachedStatus() {
		return memcachedStatus;
	}

	public void setMemcachedStatus(Object memcachedStatus) {
		this.memcachedStatus = memcachedStatus;
	}

	public Object getTcStatus() {
		return tcStatus;
	}

	public void setTcStatus(Object tcStatus) {
		this.tcStatus = tcStatus;
	}

	public boolean isEquals() {
		if (this.mysqlStatus == null) {
			if (this.memcachedStatus != null) {
				return false;
			}
			if (this.tcStatus != null) {
				return false;
			}
			return true;
		}
		else {
			if (!this.mysqlStatus.equals(this.memcachedStatus)) {
				return false;
			}
			if (!this.mysqlStatus.equals(this.tcStatus)) {
				return false;
			}
			return true;
		}
	}

}
