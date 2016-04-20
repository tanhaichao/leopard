package io.leopard.monitor;

import io.leopard.burrow.lang.Json;
import io.leopard.commons.utility.ListUtil;
import io.leopard.commons.utility.SystemUtil;

import java.util.List;

import org.junit.Test;

public class CpuMonitorIntegrationTest {

	@Test
	public void monitor() {
		CpuMonitor monitor = new CpuMonitor();

		for (int i = 0; i < 100; i++) {
			monitor.monitor();
			this.testGC(i);
			SystemUtil.sleep(1000);
		}
	}

	protected void testGC(int count) {
		User user = new User();
		user.setUsername("hctan");
		user.setNickname("ahai");

		count = count + 1;
		List<String> gameList = ListUtil.makeList("game", 0, count * 100);
		user.setGameList(gameList);

		for (int i = 0; i <= count; i++) {
			String json = Json.toJson(user);
			Json.toObject(json, User.class);
		}
	}

	public static class User {
		private String username;
		private String nickname;
		private List<String> gameList;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public List<String> getGameList() {
			return gameList;
		}

		public void setGameList(List<String> gameList) {
			this.gameList = gameList;
		}

	}

}