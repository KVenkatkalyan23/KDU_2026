package com.example.jwt.demo.controller;


import com.example.jwt.demo.dto.PackageRequestDto;
import com.example.jwt.demo.entity.PackageClass;
import com.example.jwt.demo.service.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService){
        this.packageService = packageService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<PackageClass> addPackage(@RequestBody PackageRequestDto packageRequestDto) throws InterruptedException {
        Thread.sleep(3000);
        PackageClass savedPackage = packageService.addPackage(packageRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPackage);
    }

    @PostMapping("/async")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<PackageClass> addPackageWithAsync(@RequestBody PackageRequestDto packageRequestDto){
        PackageClass savedPackage = packageService.addPackageWithAsync(packageRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedPackage);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<PackageClass>> getAllPackages(){
        List<PackageClass> packages = packageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/sorted-count")
    public ResponseEntity<Integer> noOfSortedPackets(){
        Integer count = packageService.noOfSortedPackets();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/analytics/revenue")
    public ResponseEntity<Double> getRevenue(){
        Double revenue = packageService.getRevenue();
        return ResponseEntity.ok(revenue);
    }
}
