package io.leopard.apns;

public interface ApnsClient {

	boolean send(String deviceToken, int badge, String body);

	boolean sendByType(String deviceToken, int badge, String body, int type, String data);


}
