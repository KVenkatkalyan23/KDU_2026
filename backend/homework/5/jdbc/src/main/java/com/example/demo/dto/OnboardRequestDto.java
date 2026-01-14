package com.example.demo.dto;

public class OnboardRequestDto {
    private UserRequestDto user;
    private ShiftTypeDto shiftType;

    public UserRequestDto getUser() {
        return user;
    }

    public void setUser(UserRequestDto user) {
        this.user = user;
    }

    public ShiftTypeDto getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftTypeDto shiftType) {
        this.shiftType = shiftType;
    }
}
