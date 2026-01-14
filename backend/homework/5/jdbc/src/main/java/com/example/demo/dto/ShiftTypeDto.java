package com.example.demo.dto;

public class ShiftTypeDto {
    private String name;

    private String description;

    private Boolean active_status;

    private Integer tenant_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive_status() {
        return active_status;
    }

    public void setActive_status(Boolean active_status) {
        this.active_status = active_status;
    }

    public Integer getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(Integer tenant_id) {
        this.tenant_id = tenant_id;
    }
}


