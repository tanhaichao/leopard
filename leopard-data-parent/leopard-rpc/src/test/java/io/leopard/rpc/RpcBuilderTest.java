package io.leopard.rpc;

import java.util.List;

import org.junit.Test;

import io.leopard.json.Json;

public class RpcBuilderTest {

	@Test
	public void queryForLongs() {
		RpcBuilder builder = new RpcBuilder("http://live.check.hetunlive.com", "/webservice/fans/list.do?uid=10307021");
		List<Long> uidList = builder.queryForLongs();
		Json.print(uidList, "uidList");
	}

}