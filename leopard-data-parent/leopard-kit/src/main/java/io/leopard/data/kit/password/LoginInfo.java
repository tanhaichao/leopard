package io.leopard.data.kit.password;

/**
 * 登录信息
 * 
 * @author 谭海潮
 *
 */
public class LoginInfo {

	/**
	 * 用户ID
	 */
	private long uid;

	/**
	 * token
	 */
	private String token;

	/**
	 * 登录账号
	 */
	private String acccount;

	/**
	 * 用户对象
	 */
	private Object user;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAcccount() {
		return acccount;
	}

	public void setAcccount(String acccount) {
		this.acccount = acccount;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

}
