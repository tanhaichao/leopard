package io.leopard.web.xparam.api;

public interface UserinfoResolver {

	Userinfo getUserinfo(long uid);

	long getUid(String passport);
}
