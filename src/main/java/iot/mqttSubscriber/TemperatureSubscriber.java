package iot.mqttSubscriber;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class TemperatureSubscriber {

    @Autowired
    private MqttClient mqttClient;

    // @Autowired
    // private StringRedisTemplate redisTemplate;

    @PostConstruct
    public void subscribe() throws MqttException {
        try {
            mqttClient.subscribe("temperature", (topic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Received message on topic: " + topic);
                System.out.println("Received message: " + payload);

                // Expected message format: "device123:26.5"
                // String[] parts = payload.split(":");
                // if (parts.length != 2)
                // return;

                // String deviceId = parts[0];
                // String temperature = parts[1];

                // // Store latest temperature in Redis
                // String redisKey = "temperature:" + deviceId;

                // redisTemplate.opsForValue().set(redisKey, temperature);
            });
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error subscribing to topic: " + e.getMessage());
        }
    }
}