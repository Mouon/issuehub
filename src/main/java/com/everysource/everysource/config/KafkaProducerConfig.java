//package com.everysource.everysource.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.transaction.KafkaTransactionManager;
//import org.springframework.retry.annotation.EnableRetry;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@Slf4j
//@EnableRetry
//@EnableTransactionManagement
//public class KafkaProducerConfig {
//
//    @Autowired
//    private KafkaProperties kafkaProperties;
//
//    @Bean
//    public KafkaTransactionManager<String, String> transactionManager(ProducerFactory<String, String> producerFactory) {
//        KafkaTransactionManager<String, String> kafkaTransactionManager = new KafkaTransactionManager<>(producerFactory);
//        // 트랜잭션 매니저 생성 로그 추가
//        log.info("Kafka 트랜잭션 매니저가 생성되었습니다.");
//        return kafkaTransactionManager;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // enable.idempotence 설정 추가
//        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "issue-transactional-id"); // 트랜잭션을 위한 설정 추가
//        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
//        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
//        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
