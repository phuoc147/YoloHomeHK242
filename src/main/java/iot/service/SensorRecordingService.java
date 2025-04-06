package iot.service;

import dto.TemperatureDto;
import iot.model.Temperature;

public interface SensorRecordingService {
    public void recordTemperature(Temperature temperature) throws Exception;

    public Temperature getCurrentTemperature(String deviceId);
}
