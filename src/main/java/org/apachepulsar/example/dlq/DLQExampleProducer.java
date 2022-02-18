package org.apachepulsar.example.dlq;

import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Producer implementation that will produce a Hello World message
 * to give topic.
 */
public class DLQExampleProducer {
    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "persistent://research-and-development/research/greeting";

    public static PulsarClient getPulsarClient() throws PulsarClientException {
        return PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL).build();
    }

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = null;

        try {
            pulsarClient = getPulsarClient();
            Producer<byte[]> producer = pulsarClient
                    .newProducer()
                    .topic(TOPIC_NAME)
                    .create();

            produce(producer, "Hi! There, How are you?");
            produce(producer, "Hi");
            produce(producer, "Hi! There, how are you? Hope you are doing fine");
            produce(producer, "H");
            produce(producer, "All is well!!!");

        } finally {
            if (Objects.nonNull(pulsarClient)) {
                pulsarClient.close();
            }
        }
    }

    private static void produce(Producer<byte[]> producer, final String msg) throws PulsarClientException {
        TypedMessageBuilder<byte[]> typedMessageBuilder = producer.newMessage();
        typedMessageBuilder.key("greeting");
        typedMessageBuilder.value(msg.getBytes(StandardCharsets.UTF_8));

        MessageId messageId = typedMessageBuilder.send();
        System.out.println("Message Id " + messageId);
    }
}
