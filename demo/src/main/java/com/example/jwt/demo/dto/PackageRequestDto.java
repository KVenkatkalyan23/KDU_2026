package com.example.jwt.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class PackageRequestDto {

    @NotBlank
    private String destination;

    @NotBlank()
    private Double weight;

    @NotBlank
    private String status;

    @NotBlank
    private String deliveryType;

    public @NotBlank String getDestination() {
        return destination;
    }

    public void setDestination(@NotBlank String destination) {
        this.destination = destination;
    }

    public @NotBlank() Double getWeight() {
        return weight;
    }

    public void setWeight(@NotBlank() Double weight) {
        this.weight = weight;
    }

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }

    public @NotBlank String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(@NotBlank String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
