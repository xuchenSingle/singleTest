/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
/**
* 类作用:    CmbsQueryOrderApplication
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone
* 类名称:    CmbsQueryOrderApplication
* 类描述:    查询订单application
* 创建人:    jianghao
* 创建时间:   2018年11月5日 上午10:43:29
*/
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.migu")
@EnableScheduling
@EnableFeignClients
public class CmbsQueryOrderApplication {
	
    /**
     *<restTemplate>.
     *<装配一个全局单例RestTemplate Spring Bean用于负载均衡远程调用>
     * @return [返回类型说明]
     * @exception/throws [违例类型] [违例说明]
     * @author jianghao
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
	public TransportClient client() throws UnknownHostException {
		
		InetSocketTransportAddress node=new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
		
		Settings setting=Settings.builder().put("cluster.name","xuchen").build();
				
				
		TransportClient client=new PreBuiltTransportClient(setting);
		client.addTransportAddress(node);
		return client;
	}

    public static void main(String[] args) {
        SpringApplication.run(CmbsQueryOrderApplication.class, args);
    }
}
