package org.apachepulsar.example.producers;

import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Producer implementation that will produce a Hello World message
 * to give topic.
 */
public class SimpleMessageProducer {
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

            for (int i = 0; i < 100; i++) {
                TypedMessageBuilder<byte[]> typedMessageBuilder = producer.newMessage();
                typedMessageBuilder.key("hi-there");
                typedMessageBuilder.value(("Hi! There, How are you? Hope you are doing fine - " + i)
                        .getBytes(StandardCharsets.UTF_8));
                typedMessageBuilder.property("sender-name", "naveen");
                typedMessageBuilder.property("when", System.currentTimeMillis() + "");

                MessageId messageId = typedMessageBuilder.send();
                System.out.println("Message Id " + messageId);
            }
        } finally {
            if (Objects.nonNull(pulsarClient)) {
                pulsarClient.close();
            }
        }
    }
}
