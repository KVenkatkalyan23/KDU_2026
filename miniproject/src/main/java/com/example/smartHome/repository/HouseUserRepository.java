package com.example.smartHome.repository;

import com.example.smartHome.entity.HouseUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseUserRepository extends JpaRepository<HouseUser, UUID> {
    @Query("SELECT hu FROM HouseUser hu WHERE hu.house.houseId = :houseId AND hu.user = :username AND hu.deletedAt IS null")
    Optional<HouseUser> findByHouseIdAndUsername(@Param("houseId") UUID houseId,
                                                 @Param("username") String username);

    boolean existsByHouse_HouseIdAndUser(UUID houseId, String user);

    @Modifying
    @Transactional
    @Query("UPDATE HouseUser hu SET hu.deletedAt = CURRENT_TIMESTAMP WHERE hu.house.houseId = :houseId AND hu.deletedAt IS null")
    void softDeleteHouseUserByHouseId(
            UUID houseId
    );

    @Query("SELECT hu FROM HouseUser hu WHERE hu.user = :username AND hu.deletedAt IS null")
    List<HouseUser> findByUsername(@Param("username") String username);


}
