package com.example.demo.repository;

import com.example.demo.dto.ShiftTypeDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class HospitalRepository {

    private final JdbcTemplate jdbcTemplate;


    public HospitalRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }

    public void saveUser(UserRequestDto userRequestDto) {
        String query = "INSERT INTO users(id,username,logged_in_status,timezone,tenant_id) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(query,userRequestDto.getId(),userRequestDto.getUsername(),userRequestDto.getLogged_in_status(),userRequestDto.getTimezone(),userRequestDto.getTenant_id());
    }

    public Boolean isUserWithIdExists(int id){
        String query = "SELECT COUNT(*) FROM users WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject(
                query,
                Integer.class,
                id
        );

        return count != null && count > 0;

    }

    public void addShiftType(ShiftTypeDto shiftTypeDto) {
        String query = "INSERT INTO shift_type(name, description, active_status, tenant_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query,
                shiftTypeDto.getName(),
                shiftTypeDto.getDescription(),
                shiftTypeDto.getActive_status(),
                shiftTypeDto.getTenant_id()
        );
    }

    public void editUsername(int id, String username) {
        String query = "UPDATE users SET username = ? WHERE id = ?";
        jdbcTemplate.update(query, username,id);
    }

    public List<UserResponseDto> getUsersByTenantId(int tenantId) {
        String query = "SELECT * FROM users WHERE tenant_id = ?";
        return jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(UserResponseDto.class),
                tenantId
        );
    }

    public List<UserResponseDto> getUsers(int page, int size, String order) {
        int offset = page * size;

        String sortOrder = "ASC";
        if ("DESC".equalsIgnoreCase(order)) {
            sortOrder = "DESC";
        }

        String query = """
            SELECT *
            FROM users
            ORDER BY username %s
            LIMIT ? OFFSET ?
            """.formatted(sortOrder);

        return jdbcTemplate.query(
                query,
                new UserRowMapper(),
                size,
                offset
        );
    }

}
