package io.leopard.data.env;

/**
 * 应用程序初始化
 * 
 * @author 谭海潮
 *
 */
public interface AppInitializer {

	/**
	 * 在注册各种Bean之前就调用
	 */
	void init();
}
