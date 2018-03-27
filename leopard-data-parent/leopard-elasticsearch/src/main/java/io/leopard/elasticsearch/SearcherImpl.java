package io.leopard.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class SearcherImpl implements Searcher {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected String host;

	protected int port;

	protected String server;

	protected String clusterName;

	protected TransportClient client;

	public SearcherImpl() {

	}

	public SearcherImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String c) {
		this.clusterName = clusterName;
	}

	public void init() {

		if (server != null && server.length() > 0) {
			String[] serverInfo = server.split(":");
			this.host = serverInfo[0].trim();
			try {
				this.port = Integer.parseInt(serverInfo[1].trim());
			}
			catch (NumberFormatException e) {
				logger.error("elasticsearch server:" + server);
				throw e;
			}
		}

		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(host);
		}
		catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		Settings settings = getSettingsBuilder().build();
		this.client = new PreBuiltTransportClient(settings);
		TransportAddress transportAddress = new TransportAddress(inetAddress, port);
		client.addTransportAddress(transportAddress);
	}

	protected Settings.Builder getSettingsBuilder() {
		if (clusterName == null || clusterName.length() == 0) {
			return Settings.builder();
		}
		else {
			return Settings.builder().put("cluster.name", this.clusterName);
		}
	}

	@Override
	public TransportClient getClient() {
		return this.client;
	}

	@Override
	public boolean createIndex(String indexName) {
		Settings indexSettings = getSettingsBuilder().put("number_of_shards", 5).put("number_of_replicas", 1).build();
		CreateIndexRequest indexRequest = new CreateIndexRequest(indexName, indexSettings);
		CreateIndexResponse response = client.admin().indices().create(indexRequest).actionGet();
		return true;
		// Json.printFormat(response, "response");
	}

	public void destroy() {
		if (client == null) {
			return;
		}
		this.client.close();
	}

	@Override
	public boolean delete(String indexName, String type, String id) {
		DeleteResponse deleteresponse = client.prepareDelete(indexName, type, id).execute().actionGet();
		return true;
	}

	@Override
	public boolean add(String indexName, String type, String id, String json) {
		IndexResponse response = client.prepareIndex(indexName, type, id).setSource(json, XContentType.JSON).execute().actionGet();
		return true;
	}

	@Override
	public SearchHits search(QueryBuilder query, int start, int size) {
		SearchResponse response = client.prepareSearch().setQuery(query).setFrom(start).setSize(size).execute().actionGet();
		return response.getHits();
	}

	@Override
	public SearchHits search(QueryBuilder query, String orderField, int start, int size) {
		SearchResponse response = client.prepareSearch().setQuery(query).addSort(orderField, SortOrder.DESC).setFrom(start).setSize(size).execute().actionGet();
		return response.getHits();
	}

	@Override
	public boolean clean(String indexName) {
		try {
			DeleteIndexResponse response = client.admin().indices().prepareDelete(indexName).execute().actionGet();
		}
		catch (IndexNotFoundException e) {
			return false;
		}
		return true;
	}

	@Override
	public GetResponse get(String indexName, String type, String id) {
		GetResponse response = client.prepareGet(indexName, type, id).execute().actionGet();
		return response;
	}
}
