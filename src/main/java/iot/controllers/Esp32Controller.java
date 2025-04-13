package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.mqtt.FanMqtt;
import iot.mqtt.LightMqtt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class LightRequestBody {
    private String deviceId;
    private String action; // "on" or "off"
}

@Getter
@Setter
class FanRequestBody {
    private String deviceId;
    private String action; // "on" or "off"
    private String level; // Speed level (1-3)
}

// @Component
// @ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true")
@RestController
@RequestMapping("/device")
public class Esp32Controller {

    // @Autowired
    // private LightMqtt lightMqtt;

    // @Autowired
    // private FanMqtt fanMqtt;

    // @PostMapping("/light/activate")
    // public ResponseEntity<ApiResponse<Object>> toggleLight(@RequestBody LightRequestBody requestBody) {
    //     String action = requestBody.getAction();
    //     String topic = "ohstem/" + requestBody.getDeviceId() + "/control";
    //     // Here you would add the logic to send the command to the ESP32 device
    //     // For example, using an HTTP client to send a request to the device's API

    //     // Simulating success response for demonstration purposes
    //     try {
    //         lightMqtt.publishCommand(topic, action);
    //         return ResponseEntity.ok()
    //                 .body(ApiResponse.<Object>builder().message("Command sent to the device successfully").build());
    //     } catch (Exception e) {
    //         return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
    //                 .message("Failed to send command to the device").build());
    //     }
    // }

    // @PostMapping("/fan/activate")
    // public ResponseEntity<ApiResponse<Object>> fanController(@RequestBody FanRequestBody requestBody) {
    //     System.out.println("Fan request body: " + requestBody.getLevel());
    //     String action = requestBody.getAction();
    //     if (action.equals("on")) {
    //         fanMqtt.turnOn("khoahuynh/feeds/V6", requestBody.getLevel());
    //         return ResponseEntity.ok()
    //                 .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
    //     } else {
    //         fanMqtt.turnOff("khoahuynh/feeds/V6");
    //         return ResponseEntity.ok()
    //                 .body(ApiResponse.<Object>builder().message("Turn off successfully").build());
    //     }
    // }

}
