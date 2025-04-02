package iot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.model.Temperature;

@Repository
public interface TemperatureDao extends JpaRepository<Temperature, Long> {
    // Find by home name

}
