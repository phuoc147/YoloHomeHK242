package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.TemperatureDto;
import iot.model.Temperature;
import iot.service.SensorRecordingService;
import lombok.Getter;

import java.util.List;

@Getter
class CurrentTemperatureRequestBody {
    private Long deviceId;
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
        Temperature temperature = sensorRecordingService.getCurrentTemperature(1L);
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

    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse<List<TemperatureDto>>> getAllTemperaturesByDate(
            @RequestParam(name = "date") String date) {
    
        List<Temperature> list = sensorRecordingService.getTemperaturesByDate(date);
    
        List<TemperatureDto> dtoList = list.stream().map(temp -> TemperatureDto.builder()
                .value(temp.getValue())
                .unit(temp.getUnit())
                .status(temp.getStatus())
                .date(temp.getCreatedDate()) // or getUpdatedDate
                .build()).toList();
    
        return ResponseEntity.ok(ApiResponse.<List<TemperatureDto>>builder()
                .message("List of temperatures for: " + date)
                .data(dtoList)
                .build());
    }
    

}
