package com.example.smartHome.repository;

import com.example.smartHome.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {


    @Modifying
    @Transactional
    @Query("""
        UPDATE Room r
        SET r.deletedAt = CURRENT_TIMESTAMP
        WHERE r.house.houseId = :houseId
          AND r.deletedAt IS NULL
    """)
    int softDeleteByHouseId(@Param("houseId") UUID houseId);

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.deletedAt = CURRENT_TIMESTAMP WHERE r.roomId = :roomId AND r.deletedAt IS NULL")
    void softDeleteByRoomId(@Param("roomId") UUID roomId);


    Set<Room> findByHouse_HouseIdAndDeletedAtIsNull(UUID houseId);

}
