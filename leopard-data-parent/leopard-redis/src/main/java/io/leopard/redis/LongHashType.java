package io.leopard.redis;


public class LongHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		String param = key.substring(key.lastIndexOf(":") + 1);
		// System.out.println("param:" + param);
		long id = Long.parseLong(param);
		return id;
	}

}
