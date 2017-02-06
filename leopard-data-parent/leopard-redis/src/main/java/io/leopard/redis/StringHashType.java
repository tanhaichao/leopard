package io.leopard.redis;

public class StringHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		String param = key.substring(key.lastIndexOf(":") + 1);
		// System.out.println("param:" + param);
		return param.hashCode();
	}

}
