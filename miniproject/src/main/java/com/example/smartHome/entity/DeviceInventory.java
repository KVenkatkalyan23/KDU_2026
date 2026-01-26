package com.example.smartHome.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceInventory {

    @Id
    @Column(name = "kickston_id", length = 6)
    private String kickstonId;

    @Column(name = "device_username",unique = true, nullable = false, length = 100)
    private String deviceUsername;

    @Column(name = "device_password", nullable = false)
    private String devicePassword;

    @Column(name = "manufacture_date_time", nullable = false)
    private LocalDateTime manufactureDateTime;

    @Column(name = "manufacture_factory_place", nullable = false, length = 100)
    private String manufactureFactoryPlace;

    @Column(name = "is_registered")
    private Boolean isRegistered = false;

}
