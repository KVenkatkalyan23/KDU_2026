package com.example.demo.service;

import com.example.demo.dto.UserResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<UserResponseDto> {

    @Override
    public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(rs.getInt("id"));
        userResponseDto.setUsername(rs.getString("username"));
        userResponseDto.setTimezone(rs.getString("timezone"));
        userResponseDto.setTenant_id(rs.getInt("tenant_id"));
        userResponseDto.setLogged_in_status(rs.getBoolean("logged_in_status"));
        return userResponseDto;
    }
}
