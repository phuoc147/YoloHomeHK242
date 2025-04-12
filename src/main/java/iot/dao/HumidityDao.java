package iot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iot.model.Humidity;

@Repository
public interface HumidityDao extends JpaRepository<Humidity, Long> {
    // Get latest temperature by deviceId
    @Query(value = "SELECT * FROM humidity WHERE device_id = ?1 ORDER BY created_date DESC LIMIT 1", nativeQuery = true)
    Humidity getLatesTemperatureByDeviceId(Long deviceId); // Assuming deviceId is a field in the
                                                           // Device entity

}