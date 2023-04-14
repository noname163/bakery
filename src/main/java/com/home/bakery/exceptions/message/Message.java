package com.home.bakery.exceptions.message;

public class Message {
    public String objectExistMessage(String objectName, String value){
        return objectName+" name " + value + " already exist.";
    }
    public String objectNotFoundByIdMessage(String objectName, Long value){
        return objectName+" id " + value + " not found.";
    }

}
