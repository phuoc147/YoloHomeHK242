package iot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import iot.config.JsonConverter;
import iot.dao.DeviceDao;
import iot.dao.TemperatureDao;
import iot.model.Device;
import iot.model.Temperature;
import iot.service.SensorRecordingService;

@Service
public class SensorRecordingImpl implements SensorRecordingService {

    @Autowired
    TemperatureDao temperatureDao;

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final Logger logger = LogManager.getLogger(SensorRecordingImpl.class);

    @Override
    public void recordTemperature(Temperature temperature, Long deviceId) throws Exception {
        try {
            // Check valid deviceId
            System.out.println("Device ID: " + deviceId);
            Device device = deviceDao.findById(deviceId).orElseThrow(() -> new Exception("Device not found"));
            temperature.setDevice(device);
            temperatureDao.save(temperature);
            logger.info("Record temperature successfully with deviceId:" + deviceId);
        } catch (Exception e) {
            logger.error("Error recording temperature: " + e.getMessage());
        }
    }

    @Override
    public Temperature getCurrentTemperature(String deviceId) {
        try {
            String cache = redisTemplate.opsForValue().get("temperature:" + deviceId);
            Temperature temperature = null;
            if (cache == null) {
                System.out.println("Cache is null, get temperature from db");
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
