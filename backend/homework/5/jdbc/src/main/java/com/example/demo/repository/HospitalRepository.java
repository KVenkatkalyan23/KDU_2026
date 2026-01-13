package com.example.demo.repository;

import com.example.demo.dto.ShiftRequestDto;
import com.example.demo.dto.UserRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;


@Repository
public class HospitalRepository {

    private final JdbcTemplate jdbcTemplate;

    public HospitalRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int adduser(UserRequestDto userRequestDto) {
        String query = "INSERT INTO users(username,logged_in_status,timezone,tenant_id) VALUES (?,?,?,?)";
        System.out.println(userRequestDto.getUsername());
        return jdbcTemplate.update(query,userRequestDto.getUsername(),userRequestDto.getLogged_in_status(),userRequestDto.getTimezone(),userRequestDto.getTenant_id());
    }

    public void addShift(ShiftRequestDto shiftRequestDto) {
        String query = "INSERT INTO shift(shift_type_id, date_start, date_end, time_start, time_end, tenant_id) VALUES (?, ?, ?, ?, ?, ?)";
        Timestamp now = Timestamp.from(java.time.Instant.now());
        jdbcTemplate.update(query,
                shiftRequestDto.getShiftTypeId(),
                shiftRequestDto.getDateStart(),
                shiftRequestDto.getDateEnd(),
                now,
                shiftRequestDto.getTimeEnd(),
                shiftRequestDto.getTenantId()
        );
    }



}
