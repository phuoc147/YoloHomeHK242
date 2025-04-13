package iot.service;

import java.util.List;

import dto.TemperatureDto;
import iot.model.Temperature;

public interface SensorRecordingService {
    public void recordTemperature(Temperature temperature, Long deviceId) throws Exception;

    public Temperature getCurrentTemperature(Long deviceId);

    public List<Temperature> getTemperaturesByDate(String date);
    

}
