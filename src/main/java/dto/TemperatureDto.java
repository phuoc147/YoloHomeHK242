package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TemperatureDto {
    private Double value;
    private String unit; // e.g. Celsius, Fahrenheit
    private String status;
    private String date;
}
