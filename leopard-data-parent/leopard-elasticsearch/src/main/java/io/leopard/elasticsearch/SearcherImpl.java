package io.leopard.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class SearcherImpl implements Searcher {

	private String host;

	private int port;

	private TransportClient client;

	public SearcherImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void init() {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(host);
		}
		catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		Settings settings = Settings.builder().build();
		try {
			this.client = new PreBuiltTransportClient(settings);
		}
		finally {
			client.close();
		}
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
}
