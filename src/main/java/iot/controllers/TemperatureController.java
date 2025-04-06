package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.TemperatureDto;
import iot.model.Temperature;
import iot.service.SensorRecordingService;
import lombok.Getter;

@Getter
class CurrentTemperatureRequestBody {
    private String deviceId;
}

@RestController
@RequestMapping("/api/temperature")
public class TemperatureController {

    @Autowired
    private SensorRecordingService sensorRecordingService;

    // Get current temperature
    @GetMapping("/stream")
    public ResponseEntity<ApiResponse<TemperatureDto>> getCurrentTemperature(
            @RequestBody CurrentTemperatureRequestBody request) {
        // Simulate a temperature reading
        Temperature temperature = sensorRecordingService.getCurrentTemperature(request.getDeviceId());
        if (temperature == null) {
            return ResponseEntity.status(404).body(ApiResponse.<TemperatureDto>builder()
                    .message("Temperature not found for device ID: " + request.getDeviceId()).build());
        }
        TemperatureDto temperatureDto = TemperatureDto.builder()
                .value(temperature.getValue())
                .unit(temperature.getUnit())
                .status(temperature.getStatus())
                .date(temperature.getCreatedDate())
                .build();
        return ResponseEntity.ok(ApiResponse.<TemperatureDto>builder()
                .message("Current temperature retrieved successfully")
                .data(temperatureDto)
                .build());
    }
}
