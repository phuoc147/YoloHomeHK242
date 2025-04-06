package iot.mqttSubscriber;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import iot.config.JsonConverter;
import iot.model.Device;
import iot.model.Temperature;
import iot.service.SensorRecordingService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

//Class for subcribed data
@Getter
class SubscribedTemperatureData {
    private String device_id;
    private Double temperature;
    // Getters and setters
}

@Component
public class TemperatureSubscriber {

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SensorRecordingService sensorRecordingService;

    @Autowired
    private JsonConverter jsonConverter;

    @PostConstruct
    public void subscribe() throws MqttException {
        try {
            // Subscribe to the topic "temperature/device_id"
            mqttClient.subscribe("temperature", 1, (topic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Received message on topic: " + topic);
                System.out.println("Payload: ");

                SubscribedTemperatureData subscribedTemperatureData = jsonConverter.getObjectMapper()
                        .readValue(payload, SubscribedTemperatureData.class);
                String deviceId = subscribedTemperatureData.getDevice_id();
                Double temperatureValue = subscribedTemperatureData.getTemperature();
                System.out.println("Device ID: " + deviceId);
                System.out.println("Temperature: " + temperatureValue);
                Temperature temperature = Temperature.builder()
                        .device(Device.builder().deviceId(Long.parseLong(deviceId)).build())
                        .unit("Celsius")
                        .value(temperatureValue)
                        .build();
                temperature.setCreatedDate(String.valueOf(System.currentTimeMillis()));
                // Store the temperature in db
                sensorRecordingService.recordTemperature(temperature);

                // Store in Redis
                redisTemplate.opsForValue().set(deviceId, jsonConverter.getObjectMapper()
                        .writeValueAsString(temperature));

            });
        } catch (Exception e) {
            System.out.println("Error subscribing to topic: " + e.getMessage());
        }
    }
}