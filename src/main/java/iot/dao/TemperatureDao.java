package iot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iot.model.Temperature;

@Repository
public interface TemperatureDao extends JpaRepository<Temperature, Long> {
    // Get latest temperature by deviceId
    @Query(value = "SELECT * FROM temperature t WHERE t.device_id = ?1 ORDER BY t.created_date DESC LIMIT 1", nativeQuery = true)
    Temperature getLatesTemperatureByDeviceId(Long deviceId); // Assuming deviceId is a field in the
                                                              // Device entity

}
