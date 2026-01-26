package com.example.smartHome.repository;

import com.example.smartHome.entity.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.house = null, d.room = null WHERE d.house.houseId = :houseId AND d.deletedAt IS NULL")
    void detachDevicesFromHouse(
            UUID houseId
    );

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.deletedAt = CURRENT_TIMESTAMP WHERE d.deviceId IN :deviceIds")
    void softDeleteDevicesByDeviceIds(
            @Param("deviceIds") List<UUID> deviceIds
    );

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.room = null WHERE d.room.roomId = :roomId AND d.deletedAt IS NULL")
    void detachDevicesFromRoom(
           @Param("roomId") UUID roomId
    );

    @Query("SELECT d FROM Device d WHERE d.ownedBy = :username AND d.house IS NULL AND d.room IS NULL AND d.deletedAt IS NULL")
    List<Device> getAllDevicesByOwnerNotAttachToHouseOrRoom(@Param("username") String username);

    List<Device> findByHouse_houseIdAndDeletedAtIsNull(UUID houseId);

    @Modifying
    @Transactional
    @Query("UPDATE Device d SET d.ownedBy = :username WHERE d.house.houseId = :houseId AND d.deletedAt IS NULL")
    void updateDeviceOwnership(@Param("username") String username, @Param("houseId") UUID houseId);

}
