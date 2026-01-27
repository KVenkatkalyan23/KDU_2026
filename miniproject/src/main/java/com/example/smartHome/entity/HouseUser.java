package com.example.smartHome.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class HouseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID houseUserId;

    @ManyToOne()
    @JoinColumn(nullable = false,name = "house_id")
    private House house;

    @Column(nullable = false,name = "user_id")
    private String user;

    @Column(name = "created_at", updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
