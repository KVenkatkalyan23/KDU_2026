package com.example.smartHome.dto.HouseDtos;

import java.util.UUID;

public class TransferOwnerShipRequest extends AddUserToHouseRequest {
    public TransferOwnerShipRequest(String username, UUID houseId){
        super(username,houseId);
    }
}
