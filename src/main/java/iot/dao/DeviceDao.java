package iot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.model.Device;

@Repository
public interface DeviceDao extends JpaRepository<Device, Long> {
    // Find by home name

}
