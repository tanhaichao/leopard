package io.leopard.sysconfig;

import io.leopard.sysconfig.viewer.SysconfigVO;

/**
 * 系统配置解析器
 * 
 * @author 谭海潮
 *
 */
public interface SysconfigResolver {

	boolean update();

	SysconfigVO get();

	boolean publish();
}
