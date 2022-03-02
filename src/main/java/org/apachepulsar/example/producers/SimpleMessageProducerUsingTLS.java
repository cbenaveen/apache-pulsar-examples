package org.apachepulsar.example.producers;

import org.apache.pulsar.client.api.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * Producer implementation that will produce a Hello World message
 * to give topic.
 */
public class SimpleMessageProducerUsingTLS {
    /**
     * In developer machine you would connect to pulsar://localhost:6650
     * in QA environment you would connect to pulsar://qa.pulsar.proxy:6650
     * in Stage environment you would connect to pulsar://stage.pulsar.proxy:6650
     * in Prod environment you would connect to pulsar://prod.pulsar.proxy:7650
     */
//    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "non-persistent://research-and-development/research/greeting-nonpersistent";

    public static PulsarClient getPulsarClient() throws PulsarClientException {
        Map<String, String> getenv = System.getenv();
        final String pulsarServiceHost = getenv.get("PULSAR_SERVICE_URL");
        return PulsarClient.builder()
                .enableTls(true)
                .tlsTrustCertsFilePath("/home/naveen/workspace/apache-pulsar-2.9.1/ssl-certificates/broker.cert.pem")
                .enableTlsHostnameVerification(false) // false by default, in any case
                .allowTlsInsecureConnection(false) // false by default, in any case
                .serviceUrl("pulsar+ssl://localhost:6443/")
                .build();
    }

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = null;

        try {
            pulsarClient = getPulsarClient();
            Producer<byte[]> producer = pulsarClient

                    .newProducer()
                    .topic(TOPIC_NAME)
                    .create();

            for (int i = 0; i < 100000; i++) {
                TypedMessageBuilder<byte[]> typedMessageBuilder = producer.newMessage();
                typedMessageBuilder.key("hi-there");
                typedMessageBuilder.value(("Hi! There, How are you? Hope you are doing fine - " + i)
                        .getBytes(StandardCharsets.UTF_8));
                typedMessageBuilder.property("sender-name", "naveen");
                typedMessageBuilder.property("when", System.currentTimeMillis() + "");

                MessageId messageId = typedMessageBuilder.send();
                System.out.println("Message Id " + messageId);

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(pulsarClient)) {
                pulsarClient.close();
            }
        }
    }
}
