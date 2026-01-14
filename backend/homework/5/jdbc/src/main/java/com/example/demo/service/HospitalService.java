package com.example.demo.service;

import com.example.demo.dto.OnboardRequestDto;
import com.example.demo.dto.ShiftTypeDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.repository.HospitalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository){
       this.hospitalRepository = hospitalRepository;
    }

    public void saveUser(UserRequestDto userRequestDto) {
        if(hospitalRepository.isUserWithIdExists(userRequestDto.getId())){
            throw new RuntimeException("user already exits with id : " + userRequestDto);
        }

        hospitalRepository.saveUser(userRequestDto);
    }

    public void addShiftType(ShiftTypeDto shiftTypeDto) {
        hospitalRepository.addShiftType(shiftTypeDto);
    }

    public void editUsername(int id, String username) {
        if(!hospitalRepository.isUserWithIdExists(id)){
            throw new RuntimeException("user not exits with id : " + id);
        }
        hospitalRepository.editUsername(id,username);
    }

    public List<UserResponseDto> getUsersByTenantId(int id) {
        return hospitalRepository.getUsersByTenantId(id);
    }

    @Transactional
    public void addUserAndShiftType(OnboardRequestDto onboardRequestDto) {
        hospitalRepository.saveUser(onboardRequestDto.getUser());
        hospitalRepository.addShiftType(onboardRequestDto.getShiftType());

        // force rollback
        hospitalRepository.saveUser(
                new UserRequestDto(null, null, true, "UTC", null)
        );
    }

    public List<UserResponseDto> getUsers(int page, int size, String order) {
        return hospitalRepository.getUsers(page, size,order);
    }
}
