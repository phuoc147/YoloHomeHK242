package iot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true")
public class LightMqtt {
    @Autowired
    private MqttClient mqttClient;

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
