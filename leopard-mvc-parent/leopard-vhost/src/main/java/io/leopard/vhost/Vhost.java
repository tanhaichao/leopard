package io.leopard.vhost;

/**
 * 虚拟主机
 * 
 * @author 谭海潮
 *
 */
public @interface Vhost {
	
	/**
	 * 域名
	 * 
	 * @return
	 */
	String[] value() default {};

}
