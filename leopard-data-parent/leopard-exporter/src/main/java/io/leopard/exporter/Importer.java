package io.leopard.exporter;

import java.util.List;

/**
 * 数据导入器
 * 
 * @author 谭海潮
 *
 */
public interface Importer {

	int execute(Class<?> model, int size);

	<T> List<T> execute(Class<T> model, int start, int size);
}
