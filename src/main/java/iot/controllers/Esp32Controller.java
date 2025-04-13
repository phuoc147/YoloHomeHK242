package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.dto.DeviceDto;
import iot.mqtt.DeviceControlMqtt;
import iot.mqtt.LightMqtt;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/devicecontrol")
public class Esp32Controller {

    @Autowired
    private LightMqtt lightMqtt;

    @Autowired
    private DeviceControlMqtt deviceControlMqtt;

    @PostMapping("/light/activate")
    public ResponseEntity<ApiResponse<Object>> toggleLight(
            @Valid @RequestBody DeviceDto.LightControlRequestDto requestBody) {
        String action = requestBody.getAction();
        if (action.equals("on")) {
            if (deviceControlMqtt.control("khoahuynh/feeds/V4", requestBody.getColor())) {
                return ResponseEntity.ok()
                        .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
            } else {
                return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                        .message("Failed to send command to the device").build());
            }

        } else {
            if (deviceControlMqtt.control("khoahuynh/feeds/V3", "")) {
                return ResponseEntity.ok()
                        .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
            } else {
                return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                        .message("Failed to send command to the device").build());
            }
        }
    }

    @PostMapping("/fan/activate")
    public ResponseEntity<ApiResponse<Object>> fanController(
            @Valid @RequestBody DeviceDto.FanControlRequestDto requestBody) {
        String action = requestBody.getAction();
        if (action.equals("on")) {
            if (deviceControlMqtt.control("khoahuynh/feeds/V6", requestBody.getLevel())) {
                return ResponseEntity.ok()
                        .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
            } else {
                return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                        .message("Failed to send command to the device").build());
            }

        } else {
            if (deviceControlMqtt.control("khoahuynh/feeds/V5", "")) {
                return ResponseEntity.ok()
                        .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
            } else {
                return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                        .message("Failed to send command to the device").build());
            }
        }
    }

    @PostMapping("/door/activate")
    public ResponseEntity<ApiResponse<Object>> doorController(
            @Valid @RequestBody DeviceDto.DoorControlRequestDto requestBody) {
        String action = requestBody.getAction();
        if (action.equals("on")) {
            if (deviceControlMqtt.control("khoahuynh/feeds/V6", "")) {
                return ResponseEntity.ok()
                        .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
            } else {
                return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                        .message("Failed to send command to the device").build());
            }

        } else {
            if (deviceControlMqtt.control("khoahuynh/feeds/V5", "")) {
                return ResponseEntity.ok()
                        .body(ApiResponse.<Object>builder().message("Turn on successfully").build());
            } else {
                return ResponseEntity.status(500).body(ApiResponse.<Object>builder()
                        .message("Failed to send command to the device").build());
            }
        }

    }

}
