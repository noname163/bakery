package com.home.bakery.exceptions.message;

import org.springframework.stereotype.Component;

@Component
public class Message {
    public String objectExistMessage(String objectName, String value){
        return objectName+" name " + value + " already exist.";
    }
    public String objectNotFoundByIdMessage(String objectName, Long value){
        return objectName+" id " + value + " not found.";
    }
    public String notFoundObjectMessage(String objectName, Object value){
        return objectName + value + " not found.";
    }
    public String badValue(String objectName){
        return "The " + objectName + " not valid";
    }

}
