package com.example.quora.configurationsKafka;


import com.example.quora.events.ViewCountEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
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

    /*
    groupId identifies a consumer group in Kafka. It allows multiple consumers to share partitions for load balancing
    and enables Kafka to track offsets per group. Different group IDs allow independent consumption of the same topic.

    Consumers don’t work alone. They always belong to a consumer group.
    */
    @Value("${spring.kafka.consumer.group-id:view-count-consumer}")
    private String groupId;

    public static final String TOPIC_NAME = "view-count-topic";

    @Bean
    // ProducerFactory ? -> used to create a Producer
    // Generally Factory is referred to a class which has the responsibility of creation of an object
    // mapping can be done from to String to Object (like JSON)
    // When you are dumping into Kafka, you need to serialize your data
    public ProducerFactory<String, Object> producerFactory() {

        // we'll create a map where we'll store all the kafka configurations
        Map<String, Object> configProps = new HashMap<>();

        // now you are going to decide all the kafka related properties
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        // key serialiser
        // When you are dumping your data into kafka -> you need to serialise your data.
        // key here is a string, so I want a key serialiser
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // value serializer
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);

    }


    // When you are reading from Kafka, you need to deserialize your data
    @Bean
    public ConsumerFactory<String, ViewCountEvent> consumerFactory() {

        JsonDeserializer<ViewCountEvent> deserializer =
                new JsonDeserializer<>(ViewCountEvent.class);

        deserializer.addTrustedPackages("com.example.quora.events");
        // we'll create a map where we'll store all the kafka configurations
        Map<String, Object> configProps = new HashMap<>();

        // now you are going to decide all the kafka related properties
        // when you are reading from kafka, you need to deserialize your data.
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        // you need to decide your consumer groupId because you are going to have multiple consumers
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // key deserializer
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // value deserializer
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                deserializer
        );

    }

    // We want to send messages then we need a KafkaTemplate Object
    // so in order to avoid again and again unnecessary creating those objects we can define a bean here.
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }



    // There is a listener factory that you need to create so that your listeners are configured
    // because in consumer factory you have decided what all consumers do you have
    /* ConcurrentKafkaListenerContainerFactory is a Spring Kafka factory class that creates listener containers
       for methods annotated with @KafkaListener.*/
/*    It is a Spring Kafka factory that creates concurrent Kafka listener containers
    for methods annotated with @KafkaListener, enabling multi-threaded message consumption. */
    // It configures how your Kafka consumers behave when using @KafkaListener.

    /*
     Spring internally:
        Uses ConcurrentKafkaListenerContainerFactory
        Creates a Kafka listener container
        That container:
            1. Creates Kafka consumers
            2. Subscribes to topics
            3. Polls messages
            4. Calls your method
    */


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ViewCountEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ViewCountEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        /*
      factory.setConsumerFactory(consumerFactory());
        This tells the listener:
        “Use this configuration to create Kafka consumers”
        Your consumerFactory() contains:
              1.  Bootstrap servers
              2.  Group ID
              3.  Key deserializer
              4.  Value deserializer
              5.  Auto commit config
        etc.
        So this defines how consumers connect to Kafka.
    */
        factory.setConsumerFactory(consumerFactory());
        /*
         factory.setConcurrency(3);  (Important)

            This is the powerful part.

            It means:
            Create 3 concurrent Kafka consumer threads.
            So:
            --> 1 @KafkaListener
            --> 3 consumer threads

            Parallel message processing

            If topic has:
                3 partitions → perfect
                6 partitions → each thread handles multiple
                1 partition → only 1 thread will be active
            Important rule:
                Kafka allows only 1 consumer per partition per group
                So concurrency works based on number of partitions.
        */
        factory.setConcurrency(3);
        return factory;
    }

//    @Bean
//    public JsonMessageConverter jsonMessageConverter() {
//        return new JsonMessageConverter();
//    }


}
