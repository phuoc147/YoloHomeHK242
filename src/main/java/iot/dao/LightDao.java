package iot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import iot.model.Light;
import iot.model.Temperature;

@Repository
public interface LightDao extends JpaRepository<Light, Long> {
    // Get latest temperature by deviceId
    @Query(value = "SELECT * FROM light WHERE device_id = ?1 ORDER BY created_date DESC LIMIT 1", nativeQuery = true)
    Light getLatestLightByDeviceId(Long deviceId); // Assuming deviceId is a field in the
                                                   // Device entity
    // Get 24 degrees

    @Query(value = "SELECT * FROM light t WHERE t.created_date LIKE CONCAT(?1, '%') AND DATE_FORMAT(t.created_date, '%i:%s') = '00:00' ORDER BY t.created_date ASC", nativeQuery = true)
    List<Light> findByCreatedDateOrderByTime(String createdDate);
}