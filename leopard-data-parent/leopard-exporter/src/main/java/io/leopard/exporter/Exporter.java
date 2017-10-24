package io.leopard.exporter;

import java.util.List;

import io.leopard.jdbc.Jdbc;

/**
 * 数据导出器
 * 
 * @author 谭海潮
 *
 */
public interface Exporter {

	<T> List<T> export(Class<T> model, int start, int size);

	Jdbc getJdbc();
}
