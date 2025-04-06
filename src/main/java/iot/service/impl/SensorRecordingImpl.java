package iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import iot.config.JsonConverter;
import iot.dao.TemperatureDao;
import iot.model.Temperature;
import iot.service.SensorRecordingService;

@Service
public class SensorRecordingImpl implements SensorRecordingService {

    @Autowired
    TemperatureDao temperatureDao;

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Async
    public void recordTemperature(Temperature temperature) throws Exception {
        try {
            // Save to database
            temperatureDao.save(temperature);
        } catch (Exception e) {
            throw new Exception("Error recording temperature: " + e.getMessage(), e);
        }
    }

    @Override
    public Temperature getCurrentTemperature(String deviceId) {
        try {
            redisTemplate.opsForValue().get(deviceId);
            String cache = redisTemplate.opsForValue().get(deviceId);
            Temperature temperature = null;
            if (cache == null) {
                temperature = temperatureDao.getLatesTemperatureByDeviceId(Long.parseLong(deviceId));
            } else {
                temperature = jsonConverter.getObjectMapper().readValue(cache, Temperature.class);
            }
            return temperature;
        } catch (Exception e) {
            return null;
        }
    }

}
