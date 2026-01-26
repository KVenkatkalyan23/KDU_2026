package com.example.smartHome.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_id")
    private UUID houseId;

    @Column(name = "house_name",length = 100)
    private String houseName;

    @Column(nullable = false)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(name = "postal_code",length = 6)
    private String postalCode;

    @Column(nullable = false,name = "admin_id")
    private String admin;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Device> devices = new ArrayList<>();

    @OneToMany(mappedBy = "house",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(
            mappedBy = "house",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<HouseUser> houseUsers = new HashSet<>();

    @Column(name = "created_at", updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
