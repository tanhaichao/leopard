package io.leopard.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * 搜索器
 * 
 * @author 谭海潮
 *
 */
public interface Searcher {

	boolean createIndex(String index);

	TransportClient getClient();

	/**
	 * 添加记录
	 * 
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param id ID
	 * @param json 数据
	 * @return
	 */
	boolean add(String index, String type, String id, String json);

	/**
	 * 根据主键查询
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	GetResponse get(String index, String type, String id);

}
