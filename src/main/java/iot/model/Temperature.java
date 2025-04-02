package iot.model;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "temperature")
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long temperatureId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id", referencedColumnName = "deviceId")
    private Device device;

    private String temperatureValue;
    private String humidityValue;
    private String status;

    @CreationTimestamp
    private String createdDate;
    @CreationTimestamp
    private String updatedDate;
}
