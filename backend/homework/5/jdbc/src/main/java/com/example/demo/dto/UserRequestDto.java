package com.example.demo.dto;

public class UserRequestDto {
    private Integer id;

    private String username;

    private Boolean logged_in_status;

    private String timezone;

    private Integer tenant_id;

    public UserRequestDto(Integer id, String username, Boolean logged_in_status, String timezone, Integer tenant_id) {
        this.id = id;
        this.username = username;
        this.logged_in_status = logged_in_status;
        this.timezone = timezone;
        this.tenant_id = tenant_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLogged_in_status() {
        return logged_in_status;
    }

    public void setLogged_in_status(Boolean logged_in_status) {
        this.logged_in_status = logged_in_status;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(Integer tenant_id) {
        this.tenant_id = tenant_id;
    }
}
