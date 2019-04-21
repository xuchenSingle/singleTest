//package com.migu.redstone.order.controller;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticSearchConfig {
//
//	@Bean
//	public TransportClient client() throws UnknownHostException {
//		
//		InetSocketTransportAddress node=new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
//		
//		Settings setting=Settings.builder().put("cluster.name","master").build();
//				
//				
//		TransportClient client=new PreBuiltTransportClient(setting);
//		client.addTransportAddress(node);
//		return client;
//	}
//}
