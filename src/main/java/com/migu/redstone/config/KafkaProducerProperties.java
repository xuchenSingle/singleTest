package com.migu.redstone.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


/**
* 类作用:    kafka配置参数读取
* 项目名称:   redstone-sms
* 包:       com.migu.redstone.kafka.config
* 类名称:    KafkaConsumerProperties
* 类描述:    kafka配置参数读取
* 创建人:    jianghao
* 创建时间:   2018年11月02日 上午10:33:18
*/
@ConfigurationProperties(prefix = "kafka.producer")
@Data
public class KafkaProducerProperties {

    /**
     * servers
     */
    private String servers;
    /**
     * retries
     */
    private int retries;
    /**
     * batchSize
     */
    private int batchSize;
    /**
     * bufferMemory
     */
    private int bufferMemory;
    /**
     * autoCommitInterval
     */
    private String autoCommitInterval;
    /**
     * sessionTimeout
     */
    private String sessionTimeout;
    /**
     * autoOffsetReset
     */
    private String autoOffsetReset;
    /**
     * groupId
     */
    private String groupId;
    /**
     * concurrency
     */
    private int concurrency;
    /**
     * pollTimeout
     */
    private int pollTimeout;

}
