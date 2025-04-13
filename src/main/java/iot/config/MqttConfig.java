package iot.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

// @Configuration
@Component
@ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true")
@Configuration
public class MqttConfig {


    @Value("${mqtt.broker}")
    private String BROKER_URL;

    @Value("${mqtt.subscriber.username}")
    private String username;

    @Value("${mqtt.subscriber.password}")
    private String password;

    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        System.out.println("Connecting to MQTT broker at " + BROKER_URL);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        // Set username and password
        options.setUserName(username);
        options.setPassword(password.toCharArray());

        client.connect(options);
        return client;
    }
}
