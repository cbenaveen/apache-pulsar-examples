package org.apachepulsar.example.dlq;

import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DLQConsumer {
    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "persistent://research-and-development/research/greeting";
    public static final String DQL_TOPIC_NAME = "persistent://research-and-development/research/greeting-dlq";

    public static void main(String[] args) {
        try(PulsarClient pulsarClient = PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL)
                .build()) {
            DeadLetterPolicy deadLetterPolicy = DeadLetterPolicy.builder()
                    .deadLetterTopic(DQL_TOPIC_NAME)
                    .maxRedeliverCount(5)
                    .build();

            Consumer<byte[]> consumer = pulsarClient.newConsumer()
                    .subscriptionType(SubscriptionType.Shared)
                    .subscriptionName("DLQ-Consumer")
                    .deadLetterPolicy(deadLetterPolicy)
                    .topic(TOPIC_NAME)
                    .subscribe();

            while(true) {
                Message<byte[]> message = consumer.receive(30, TimeUnit.SECONDS);
                String messageValue = new String(message.getData(), StandardCharsets.UTF_8);

                String messageKey = message.getKey();
                Map<String, String> properties = message.getProperties();

                System.out.println("Message Received: Message Id: " + message.getMessageId()
                        + ", Key " + messageKey + ", Value = "
                        + messageValue + ", Properties = " + properties);

                if (messageValue.length() < 3) {
                    consumer.negativeAcknowledge(message);
                } else {
                    consumer.acknowledge(message);
                }

            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
