package iot.mqtt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true")

public class FanMqtt {
    @Autowired
    private MqttClient mqttClient;

    private final Logger logger = LogManager.getLogger(FanMqtt.class);

    public void turnOn(String topic, String fanSpeed) {
        try {
            MqttMessage mqttMessage = new MqttMessage(fanSpeed.getBytes());
            mqttClient.publish(topic, mqttMessage);
            logger.info("Published: " + fanSpeed + " to topic: " + topic);
        } catch (MqttException e) {
            logger.error("Error publishing to topic: " + e.getMessage());
        }
    }

    public void turnOff(String topic) {
        try {
            String message = "4";
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttClient.publish(topic, mqttMessage);
            logger.info("Published: " + message + " to topic: " + topic);
        } catch (MqttException e) {
            logger.error("Error publishing to topic: " + e.getMessage());
        }
    }
}
