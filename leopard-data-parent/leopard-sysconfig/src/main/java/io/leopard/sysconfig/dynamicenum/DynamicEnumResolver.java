package io.leopard.sysconfig.dynamicenum;

import io.leopard.sysconfig.viewer.DynamicEnumDataVO;

/**
 * 动态枚举解析器
 * 
 * @author 谭海潮
 *
 */
public interface DynamicEnumResolver {

	boolean update();

	DynamicEnumDataVO get();

	boolean publish();

}
