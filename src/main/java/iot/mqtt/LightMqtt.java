package iot.mqtt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import iot.config.JsonConverter;
import iot.model.Light;
import iot.model.Temperature;
import jakarta.annotation.PostConstruct;

@Component
public class LightMqtt {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private JsonConverter jsonConverter;

    @PostConstruct
    public void subscribe() throws MqttException {
        try {
            // Subscribe to the topic "temperature/device_id"
            mqttClient.subscribe("khoahuynh/feeds/V3", 1, (topic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Received message on topic: " + topic);
                System.out.println("Payload: " + payload);

                // SubscribedTemperatureData subscribedTemperatureData =
                // jsonConverter.getObjectMapper()
                // .readValue(payload, SubscribedTemperatureData.class);
                // String deviceId = subscribedTemperatureData.getDevice_id();
                Double lightValue = Double.parseDouble(payload);

                Light light = Light.builder()
                        .unit("%")
                        .value(lightValue)
                        .build();
                light.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                System.out.println("Created date: " + light.getCreatedDate());
                // // Store the temperature in db
                // sensorRecordingService.recordTemperature(temperature, 1L);

                // // // Store in Redis
                String key = "light:" + "1";
                redisTemplate.opsForValue().set(key, jsonConverter.getObjectMapper()
                        .writeValueAsString(light));

            });
        } catch (Exception e) {
            System.out.println("Error subscribing to topic: " + e.getMessage());
        }
    }

    public void publishCommand(String topic, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttClient.publish(topic, mqttMessage);
    }

    // @PostConstruct
    public void publish() {
        try {
            String topic = "khoahuynh/feeds/V4";
            String message = "Tat den";
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttClient.publish(topic, mqttMessage);
            System.out.println("Published: " + message);
        } catch (MqttException e) {
            System.out.println("Error publishing to topic: " + e.getMessage());
        }
    }
}
