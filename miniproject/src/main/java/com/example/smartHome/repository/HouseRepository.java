package com.example.smartHome.repository;

import com.example.smartHome.entity.House;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HouseRepository extends JpaRepository<House, UUID> {
    @Query("SELECT h FROM House h WHERE h.admin = :username AND h.deletedAt IS null")
    List<House> getAllHousesByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("""
        UPDATE House h
        SET h.deletedAt = CURRENT_TIMESTAMP
        WHERE h.houseId = :houseId
          AND h.deletedAt IS NULL
    """)
    int softDeleteByHouseId(@Param("houseId") UUID houseId);


}
