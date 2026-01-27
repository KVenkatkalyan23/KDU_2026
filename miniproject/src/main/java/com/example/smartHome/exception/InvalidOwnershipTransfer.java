package com.example.smartHome.exception;

public class InvalidOwnershipTransfer extends RuntimeException{
    public InvalidOwnershipTransfer(String message){
        super(message);
    }
}
