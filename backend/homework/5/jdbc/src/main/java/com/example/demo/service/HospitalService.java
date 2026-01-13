package com.example.demo.service;

import com.example.demo.dto.ShiftRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.repository.HospitalRepository;
import org.springframework.stereotype.Service;


@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository){
       this.hospitalRepository = hospitalRepository;
    }
    public void addUser(UserRequestDto userRequestDto) {
        hospitalRepository.adduser(userRequestDto);
    }

    public void addShift(ShiftRequestDto shiftRequestDto) {
        hospitalRepository.addShift(shiftRequestDto);
    }
}
