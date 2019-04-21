package com.migu.redstone.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.migu.redstone.kafkaHandler.KafkaSendResultHandler;



/**
* 类作用:    kafka生产者工厂和kafka模板配置
* 项目名称:   redstone-sms
* 包:       com.migu.redstone.kafka.config
* 类名称:    KafkaProducerConfig
* 类描述:    kafka生产者工厂和kafka模板配置
* 创建人:    jianghao
* 创建时间:   2018年11月02日 上午10:33:37
*/
@Configuration
@EnableConfigurationProperties(KafkaProducerProperties.class)
@EnableKafka
public class KafkaProducerConfig {
    /**
     * properties
     */
    @Autowired
    KafkaProducerProperties properties;
    
    @Autowired
	private KafkaSendResultHandler kafkaSendResultHandler;

    /**
     *
      *〈一句话功能简述〉
      *〈功能详细描述〉
      * @return props
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getServers());
        props.put(ProducerConfig.RETRIES_CONFIG, properties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, properties
                .getBatchSize());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, properties
                .getBufferMemory());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     *
      *〈一句话功能简述〉
      *〈功能详细描述〉
      * @return new DefaultKafkaProducerFactory
     */
    private ProducerFactory<Object, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     *
      *〈一句话功能简述〉
      *〈功能详细描述〉
      * @return new KafkaTemplate<String, String>
     */
    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<Object, Object> kafkaTemplate() {
    	KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<Object, Object>(producerFactory());
		kafkaTemplate.setProducerListener(kafkaSendResultHandler);
		return kafkaTemplate;
    }
}
