package com.example.quora.configurationsKafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka

public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    // we need to define where our kafka server is running
    // defaultValue - localhost:9092
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id:view-count-consumer}")
    private String groupId;

    @Bean
    // ProducerFactory ?
    // Generally Factory is referred to a class which has the responsibility of creation of an object
    // mapping can be done from to String to Object (like JSON)
    public ProducerFactory<String, Object> producerFactory() {

        // we'll create a map where we'll store all the kafka configurations
        Map<String, Object> configProps = new HashMap<>();

        // now you are going to decide all the kafka related properties
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        // key serialiser
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // value serialiser
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);



    }


//    @Bean
//    public JsonMessageConverter jsonMessageConverter() {
//        return new JsonMessageConverter();
//    }


}
