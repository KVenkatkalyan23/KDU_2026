package com.example.smartHome.repository;

import com.example.smartHome.entity.DeviceInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeviceInventoryRepository extends JpaRepository<DeviceInventory,String> {

    @Modifying
    @Transactional
    @Query("UPDATE DeviceInventory di SET di.isRegistered = false WHERE di.kickstonId = :kickstonId")
    public void setRegisterToFalseByKickstonId(@Param("kickstonId") String kickstonId);

    @Modifying
    @Transactional
    @Query("UPDATE DeviceInventory di SET di.isRegistered = false WHERE di.kickstonId IN :kickstonIds")
    void setRegisterToFalseByKickstonIds(
            @Param("kickstonIds") List<String> kickstonIds
    );

}
