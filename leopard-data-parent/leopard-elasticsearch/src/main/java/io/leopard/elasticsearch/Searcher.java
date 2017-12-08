package io.leopard.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;

/**
 * 搜索器
 * 
 * @author 谭海潮
 *
 */
public interface Searcher {

	boolean createIndex(String indexName);

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
	boolean add(String indexName, String type, String id, String json);

	/**
	 * 根据主键查询
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	GetResponse get(String indexName, String type, String id);

	SearchHits search(QueryBuilder query, int start, int size);

	boolean clean(String indexName);

}
