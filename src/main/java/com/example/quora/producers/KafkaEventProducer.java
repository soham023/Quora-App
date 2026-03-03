package com.example.quora.producers;

import com.example.quora.configurationsKafka.KafkaConfig;
import com.example.quora.events.ViewCountEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, Object> _kafkaTemplate){
        this.kafkaTemplate = _kafkaTemplate;
    }

    /* we will take a viewCountEvent object in function publishViewCount and then based on viewCountEvent object we can try to publish
    an event inside kafka
    */
    public void publishViewCount(ViewCountEvent viewCountEvent){
        // targetid wil be the key
        /* Why is targetId Used as Kafka Key?
        Kafka uses the key to decide partition.
        Important rule: Same key → always goes to same partition
        So, All views of Question Q567 → same partition
         Why? -> Ordering is preserved per partition.View count increments stay consistent
         */
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, viewCountEvent.getTargetId(), viewCountEvent)
                .whenComplete((result, err) ->{
                    if(err != null){
                        System.out.println("Error publishing view count event : " + err.getMessage());
                    }
                });

        /*
        Your kafkaTemplate.send() method returns: CompletableFuture<SendResult<K, V>>
        It means : The Kafka send operation is ASYNCHRONOUS.

        when you call this method ,
        1. It does NOT block.
        2. It does NOT wait for Kafka to confirm.
        3. It immediately returns a CompletableFuture


        What is CompletableFuture in KafkaTemplate?
        KafkaTemplate.send() is asynchronous and returns a CompletableFuture, which represents the result of the send operation
        that will complete once Kafka acknowledges the message. It allows non-blocking message publishing.

         */

    }

}
