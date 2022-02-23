package org.apachepulsar.example.schemas.complextypes;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apachepulsar.example.schemas.complextypes.model.SensorData;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SensorDataConsumer {
    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "sensor-data";

    public static void main(String[] args) {
        try(PulsarClient pulsarClient = PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL)
                .build()) {
            Consumer<SensorData> consumer = pulsarClient.newConsumer(JSONSchema.of(SensorData.class))
                    .subscriptionType(SubscriptionType.Failover)
                    .subscriptionName("FailOver-Consumer-Example")
                    .topic(TOPIC_NAME)
                    .subscribe();

            while(true) {
                Message<SensorData> message = consumer.receive();

                System.out.println("Message Received: Message Id: " + message.getMessageId()
                        + ", Value = " + message.getValue());

                consumer.acknowledge(message);
            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
