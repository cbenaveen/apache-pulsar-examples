package org.apachepulsar.example.schemas.complextypes;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apachepulsar.example.schemas.complextypes.model.SensorData;

import java.util.Objects;

public class SensorDataProducer {
    public static final String PULSAR_SERVICE_URL = "pulsar://localhost:6650";
    public static final String TOPIC_NAME = "sensor-data";

    public static PulsarClient getPulsarClient() throws PulsarClientException {
        return PulsarClient.builder().serviceUrl(PULSAR_SERVICE_URL).build();
    }

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = null;

        try {
            pulsarClient = getPulsarClient();
            Producer<SensorData> producer = pulsarClient.newProducer(JSONSchema.of(SensorData.class))
                    .topic(TOPIC_NAME).create();

            SensorData temSensorData = new SensorData("temperature", "parking-area-temperature-sensor",
                    "5");
            SensorData lightSensorData = new SensorData("light", "parking-area-light-sensor",
                    "50");
            producer.newMessage().value(temSensorData).send();
            System.out.println("temp sensor data has been sent " + temSensorData);

            producer.newMessage().value(lightSensorData).send();
            System.out.println("Light sensor data has been sent " + lightSensorData);
        } finally {
            if (Objects.nonNull(pulsarClient)) {
                pulsarClient.close();
            }
        }
    }
}
