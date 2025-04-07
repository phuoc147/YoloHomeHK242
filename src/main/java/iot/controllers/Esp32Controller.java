package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.mqtt.LightMqtt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class LightRequestBody {
    private String deviceId;
    private String action; // "on" or "off"
}

@RestController
@RequestMapping("/device")
public class Esp32Controller {

    @Autowired
    private LightMqtt lightMqtt;

    @PostMapping("/light/activate")
    public ResponseEntity<ApiResponse<Object>> toggleLight(@RequestBody LightRequestBody requestBody) {
        String action = requestBody.getAction();
        String topic = "ohstem/" + requestBody.getDeviceId() + "/control";
        // Here you would add the logic to send the command to the ESP32 device
        // For example, using an HTTP client to send a request to the device's API

        // Simulating success response for demonstration purposes
        try {
            lightMqtt.publishCommand(topic, action);
            return ResponseEntity.ok()
                    .body(ApiResponse.<Object>builder().message("Command sent to the device successfully").build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                    .message("Failed to send command to the device").build());
        }
    }

}
