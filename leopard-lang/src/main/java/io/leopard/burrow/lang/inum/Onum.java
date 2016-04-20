package io.leopard.burrow.lang.inum;

/**
 * 枚举接口(key类型自定义,value类型自定义)
 * 
 * @author Administrator
 * 
 * @param <KEYTYPE>
 * @param <VALUETYPE>
 * @param <BEAN>
 */
public interface Onum<KEYTYPE, VALUETYPE> {

	/**
	 * 枚举key.
	 * 
	 * @return
	 */
	KEYTYPE getKey();

	/**
	 * 枚举描述信息
	 * 
	 * @return
	 */
	VALUETYPE getDesc();

	// /**
	// * key转枚举.
	// *
	// * @param key
	// * @return
	// */
	// BEAN toEnum(KEYTYPE key);
}
