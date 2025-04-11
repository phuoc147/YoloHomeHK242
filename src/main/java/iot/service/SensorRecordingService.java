package iot.service;

import dto.TemperatureDto;
import iot.model.Temperature;

public interface SensorRecordingService {
    public void recordTemperature(Temperature temperature, Long deviceId) throws Exception;

    public Temperature getCurrentTemperature(Long deviceId);
}
