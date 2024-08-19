package com.home.bakery.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {
    public String getSubfixApi(String url){
        String result = "";
        String[] apiList = url.split("/");
        result = apiList[3];
        return result;
    }
}
