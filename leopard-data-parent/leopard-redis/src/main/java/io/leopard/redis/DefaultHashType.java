package io.leopard.redis;

public class DefaultHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		// long hashCode = StringUtil.getHashCode(key);
		// return hashCode;

		return key.hashCode();
	}

}
