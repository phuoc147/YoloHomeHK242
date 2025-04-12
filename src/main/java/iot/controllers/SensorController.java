package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.dto.SensorDataDto;
import iot.model.Humidity;
import iot.model.Temperature;
import iot.service.SensorRecordingService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {

        @Autowired
        private SensorRecordingService sensorRecordingService;

        // Get current temperature
        @GetMapping("/temperature/stream")
        public ResponseEntity<ApiResponse<SensorDataDto.CurrentTemperatureResponseDto>> getCurrentTemperature(
                        @Valid @RequestBody SensorDataDto.CurrentTemperatureRequestDto request) {
                // Simulate a temperature reading
                Temperature temperature = sensorRecordingService.getCurrentTemperature(1L);
                if (temperature == null) {
                        return ResponseEntity.status(404)
                                        .body(ApiResponse.<SensorDataDto.CurrentTemperatureResponseDto>builder()
                                                        .message("Temperature not found for device ID: "
                                                                        + request.getDeviceId())
                                                        .build());
                }
                SensorDataDto.CurrentTemperatureResponseDto temperatureDto = SensorDataDto.CurrentTemperatureResponseDto
                                .builder()
                                .value(temperature.getValue())
                                .unit(temperature.getUnit())
                                .status(temperature.getStatus())
                                .date(temperature.getCreatedDate())
                                .build();
                return ResponseEntity.ok(ApiResponse.<SensorDataDto.CurrentTemperatureResponseDto>builder()
                                .message("Current temperature retrieved successfully")
                                .data(temperatureDto)
                                .build());
        }

        // Get current humidity
        @GetMapping("/humidity/stream")
        public ResponseEntity<ApiResponse<SensorDataDto.CurrentHumidityResponseDto>> getCurrentHumidity(
                        @Valid @RequestBody SensorDataDto.CurrentHumidityRequestDto request) {
                // Simulate a humidity reading
                Humidity humidity = sensorRecordingService.getCurrentHumidity(1L);
                if (humidity == null) {
                        return ResponseEntity
                                        .status(404).body(
                                                        ApiResponse.<SensorDataDto.CurrentHumidityResponseDto>builder()
                                                                        .message("Humidity not found for device ID: "
                                                                                        + request.getDeviceId())
                                                                        .build());
                }
                SensorDataDto.CurrentHumidityResponseDto humidityDto = SensorDataDto.CurrentHumidityResponseDto
                                .builder()
                                .value(humidity.getValue())
                                .unit(humidity.getUnit())
                                .status(humidity.getStatus())
                                .date(humidity.getCreatedDate())
                                .build();
                return ResponseEntity.ok(ApiResponse.<SensorDataDto.CurrentHumidityResponseDto>builder()
                                .message("Current humidity retrieved successfully")
                                .data(humidityDto)
                                .build());

        }

        // Get current light
        @GetMapping("/light/stream")
        public ResponseEntity<ApiResponse<SensorDataDto.CurrentLightResponseDto>> getCurrentHumidity(
                        @Valid @RequestBody SensorDataDto.CurrentLightRequestDto request) {
                // Simulate a humidity reading
                Humidity humidity = sensorRecordingService.getCurrentHumidity(1L);
                if (humidity == null) {
                        return ResponseEntity
                                        .status(404).body(
                                                        ApiResponse.<SensorDataDto.CurrentLightResponseDto>builder()
                                                                        .message("Light not found for device ID: "
                                                                                        + request.getDeviceId())
                                                                        .build());
                }
                SensorDataDto.CurrentLightResponseDto humidityDto = SensorDataDto.CurrentLightResponseDto
                                .builder()
                                .value(humidity.getValue())
                                .unit(humidity.getUnit())
                                .status(humidity.getStatus())
                                .date(humidity.getCreatedDate())
                                .build();
                return ResponseEntity.ok(ApiResponse.<SensorDataDto.CurrentLightResponseDto>builder()
                                .message("Light humidity retrieved successfully")
                                .data(humidityDto)
                                .build());
        }

}
