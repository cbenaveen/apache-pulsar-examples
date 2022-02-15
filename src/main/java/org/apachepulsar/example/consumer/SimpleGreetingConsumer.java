package org.apachepulsar.example.consumer;

import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class SimpleGreetingConsumer {
    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "persistent://research-and-development/research/greeting";

    public static PulsarClient getPulsarClient() throws PulsarClientException {
        return PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL).build();
    }

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = null;

        try {
            pulsarClient = getPulsarClient();

            Consumer<byte[]> consumer = pulsarClient.newConsumer()
                    .topic(TOPIC_NAME)
                    .subscriptionName("Greeting-Consumer")
                    .subscriptionType(SubscriptionType.Exclusive)
                    .subscribe();

            while(true) {
                Message<byte[]> message = consumer.receive();
                String messageValue = new String(message.getData(), StandardCharsets.UTF_8);
                String messageKey = message.getKey();
                Map<String, String> properties = message.getProperties();

                System.out.println("Message Received: Message Id: " + message.getMessageId()
                        + ", Key " + messageKey + ", Value = "
                        + messageValue + ", Properties = " + properties);
                consumer.acknowledge(message.getMessageId());
            }

        } finally {
            if (Objects.nonNull(pulsarClient)) {
                pulsarClient.close();
            }
        }
    }
}
