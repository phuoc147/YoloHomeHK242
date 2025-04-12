package iot.service;

import iot.dto.SensorDataDto;
import iot.model.Humidity;
import iot.model.Light;
import iot.model.Temperature;

public interface SensorRecordingService {
    public void recordTemperature(Temperature temperature, Long deviceId) throws Exception;

    public Temperature getCurrentTemperature(Long deviceId);

    public void recordLight(Light temperature, Long deviceId) throws Exception;

    public Light getCurrentLight(Long deviceId);

    public void recordHumidity(Humidity temperature, Long deviceId) throws Exception;

    public Humidity getCurrentHumidity(Long deviceId);

}
