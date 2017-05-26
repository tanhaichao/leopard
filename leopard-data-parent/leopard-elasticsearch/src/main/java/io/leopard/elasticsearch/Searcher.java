package io.leopard.elasticsearch;

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

}
