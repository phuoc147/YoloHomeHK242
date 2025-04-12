package iot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iot.model.Light;

@Repository
public interface LightDao extends JpaRepository<Light, Long> {
    // Get latest temperature by deviceId
    @Query(value = "SELECT * FROM light WHERE device_id = ?1 ORDER BY created_date DESC LIMIT 1", nativeQuery = true)
    Light getLatesTemperatureByDeviceId(Long deviceId); // Assuming deviceId is a field in the
                                                        // Device entity

}