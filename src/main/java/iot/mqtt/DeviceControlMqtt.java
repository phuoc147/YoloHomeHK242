package iot.mqtt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceControlMqtt {
    @Autowired
    private MqttClient mqttClient;

    private final Logger logger = LogManager.getLogger(DeviceControlMqtt.class);

    public boolean control(String topic, String payload) {
        try {
            MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
            mqttClient.publish(topic, mqttMessage);
            logger.info("Published: " + payload + " to topic: " + topic);
            return true;
        } catch (MqttException e) {
            logger.error("Error publishing to topic: " + e.getMessage());
            return false;
        }
    }
}
