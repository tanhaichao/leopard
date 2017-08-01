package io.leopard.im.huanxin;

import java.util.Map;

import io.leopard.im.huanxin.model.Chatroom;

public class HuanxinChatroomImpl extends AbstractHuanxin implements HuanxinChatroom {

	@Override
	public String create(String name, String description, int maxusers, String owner) {
		String url = this.getUrl("/chatrooms");
		Chatroom chatroom = new Chatroom();
		chatroom.setName(name);
		chatroom.setDescription(description);
		chatroom.setMaxusers(maxusers);
		chatroom.setOwner(owner);
		String json = this.requestByToken("POST", url, chatroom);
		Map<String, Object> response = this.toResponseData(json);
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) response.get("data");
		return (String) data.get("id");
	}

}
