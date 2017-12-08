package io.leopard.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class SearcherImpl implements Searcher {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected String host;

	protected int port;

	protected String server;

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
		Settings settings = Settings.builder().build();
		this.client = new PreBuiltTransportClient(settings);
		InetSocketTransportAddress transportAddress = new InetSocketTransportAddress(inetAddress, port);
		client.addTransportAddress(transportAddress);
	}

	@Override
	public TransportClient getClient() {
		return this.client;
	}

	@Override
	public boolean createIndex(String index) {
		Settings indexSettings = Settings.builder().put("number_of_shards", 5).put("number_of_replicas", 1).build();
		CreateIndexRequest indexRequest = new CreateIndexRequest(index, indexSettings);
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
	public boolean add(String index, String type, String id, String json) {
		IndexResponse response = client.prepareIndex(index, type, id).setSource(json, XContentType.JSON).execute().actionGet();
		return true;
	}

	@Override
	public GetResponse get(String index, String type, String id) {
		GetResponse response = client.prepareGet(index, type, id).execute().actionGet();
		return response;
	}
}
