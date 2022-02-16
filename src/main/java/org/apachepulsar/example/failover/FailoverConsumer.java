package org.apachepulsar.example.failover;

import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FailoverConsumer {
    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "persistent://research-and-development/research/greeting";

//    public static PulsarClient getPulsarClient() throws PulsarClientException {
//        return PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL).build();
//    }

    public static void main(String[] args) {
        try(PulsarClient pulsarClient = PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL)
                .build()) {
            Consumer<byte[]> consumer = pulsarClient.newConsumer()
                    .subscriptionType(SubscriptionType.Failover)
                    .subscriptionName("FailOver-Consumer-Example")
                    .topic(TOPIC_NAME)
                    .subscribe();

            while(true) {
                Message<byte[]> message = consumer.receive();
                String messageValue = new String(message.getData(), StandardCharsets.UTF_8);
                String messageKey = message.getKey();
                Map<String, String> properties = message.getProperties();

                System.out.println("Message Received: Message Id: " + message.getMessageId()
                        + ", Key " + messageKey + ", Value = "
                        + messageValue + ", Properties = " + properties);

                consumer.acknowledge(message);
            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
