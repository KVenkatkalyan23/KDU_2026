package com.example.smartHome.entity;


import com.example.smartHome.constant.ROOM_TYPE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private UUID roomId;

    @Column(name = "room_name",length = 50)
    private String roomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private ROOM_TYPE roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "house_id")
    @JsonBackReference
    private House house;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Device> devices = new HashSet<>();

    @Column(name = "created_at", updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
