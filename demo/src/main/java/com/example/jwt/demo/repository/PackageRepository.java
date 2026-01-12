package com.example.jwt.demo.repository;

import com.example.jwt.demo.entity.PackageClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageRepository extends JpaRepository<PackageClass, Integer> {

    // will fetch all the packages with status sorted
    @Query(value = "SELECT P FROM PackageClass P WHERE status = 'SORTED'")
    public List<PackageClass> getPackagesWithStatusSorted();
}
