package com.example.smartHome.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id")
    private UUID deviceId;

    @Column(name = "kingston_id",length = 6,nullable = false)
    private String kickstonId;

    @Column(name = "device_name",length = 50,nullable = false)
    private String deviceName;

    @ManyToOne
    @JoinColumn(name="room_id")
    @JsonManagedReference
    private Room room;

    @ManyToOne(optional = true)
    @JoinColumn(name = "house_id",nullable = true)
    @JsonManagedReference
    private House house;

    @Column(name = "owned_by",nullable = false)
    private String ownedBy;

    @Column(name = "created_at", updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
