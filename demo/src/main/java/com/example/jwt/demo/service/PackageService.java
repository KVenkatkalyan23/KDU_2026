package com.example.jwt.demo.service;

import com.example.jwt.demo.constant.Constants;
import com.example.jwt.demo.dto.PackageRequestDto;
import com.example.jwt.demo.entity.PackageClass;
import com.example.jwt.demo.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;


@Service
public class PackageService {

    private final PackageRepository packageRepository;
    private final ExecutorService executorService;
    private static int sortedPackagesCount = 0;
    private static final Double PRICE = Constants.PRICE;

    public PackageService(PackageRepository packageRepository,ExecutorService executorService){
        this.packageRepository = packageRepository;
        this.executorService = executorService;

       sortedPackagesCount = packageRepository.getPackagesWithStatusSorted().size();
    }

    // Map RequestDto to Package
    private PackageClass mapPackageRequestDtoToPackage(PackageRequestDto packageRequestDto){
        PackageClass packageClass = new PackageClass();
        packageClass.setDestination(packageRequestDto.getDestination());
        packageClass.setStatus(packageRequestDto.getStatus());
        packageClass.setWeight(packageRequestDto.getWeight());
        packageClass.setDeliveryType(packageRequestDto.getDeliveryType());
        return packageClass;
    }

    // adds package
    public PackageClass addPackage(PackageRequestDto packageRequestDto) {
        PackageClass packageClass = mapPackageRequestDtoToPackage(packageRequestDto);
        return packageRepository.save(packageClass);
    }

    // adds package with Async
    public PackageClass addPackageWithAsync(PackageRequestDto packageRequestDto){
        PackageClass packageClass = mapPackageRequestDtoToPackage(packageRequestDto);
        PackageClass savedPackage = packageRepository.save(packageClass);

        executorService.submit(() -> {
            try {
                // change the status to SORTED
                Thread.sleep(3000);
                Optional<PackageClass> optionalPackage = packageRepository.findById(savedPackage.getId());
                PackageClass packageClass1 = optionalPackage.get();
                packageClass1.setStatus("SORTED");
                synchronized (this){
                    sortedPackagesCount++;
                }
                packageRepository.save(packageClass1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return savedPackage;
    }

    // calculate the revenue
    public Double getRevenue() {
        List<PackageClass> packages = packageRepository.getPackagesWithStatusSorted();
        Double revenue = 0.0;
        for(PackageClass p : packages){
           revenue += (p.getWeight() * PRICE); // calculates the revenue
        }
        return revenue;
    }

    public List<PackageClass> getAllPackages() {
        return packageRepository.findAll();
    }

    public Integer noOfSortedPackets() {
        return sortedPackagesCount;
    }
}
