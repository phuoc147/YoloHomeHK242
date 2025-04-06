package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureDto {
    private Double value;
    private String unit; // e.g. Celsius, Fahrenheit
    private String status;
    private String date;
}
