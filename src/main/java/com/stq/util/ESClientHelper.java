package com.stq.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

/**
 * Es Java Transport Client
 * ESClientHelper 按照Elasticsearch API，在Java端使用是ES服务需要创建Java
 * Client，但是每一次连接都实例化一个client，对系统的消耗很大， 即使在使用完毕之后将client
 * close掉，由于服务器不能及时回收socket资源，极端情况下会导致服务器达到最大连接数。为了解决上述问题并提高client利用率，可以参考使用池化技术复用client。
 * 
 */
public class ESClientHelper {
	
	private static ESClientHelper instance;
	
	private static final String ES_IP = "127.0.0.1";
	
	private static final Integer ES_PORT = 9333;
	
	private static final String ES_CLUSTER_NAME = "tarantula";
	
	private static final String ES_USER = "elastic";
	
	private static final String ES_PASSWORD = "changeme";
	/**
	 * 作为从 map 中获取 es client 使用的标识 
	 */
	private static final String  ES_NAME = "es";
	
	private Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();
	
	public static synchronized ESClientHelper getInstance() {
		if (instance == null) {
			instance = new ESClientHelper();
		}
		return instance;
	}

	private ESClientHelper() {
		init();
	}

	/**
	 * 初始化默认的client
	 */
	public void init() {
		final Settings setting = Settings.builder().put("cluster.name", ES_CLUSTER_NAME)
                .put("xpack.security.transport.ssl.enabled", false)
                .put("xpack.security.user", ES_USER+":"+ES_PASSWORD)
                .put("client.transport.sniff", false).build();
		addClient(setting);
	}

	public Client getClient() {
		return clientMap.get(ES_NAME);
	}

	@SuppressWarnings("resource")
	public void addClient(Settings setting) {
		Client client;
		try {
			client = new PreBuiltXPackTransportClient(setting)
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_IP), ES_PORT));
			clientMap.put(ES_NAME, client);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}