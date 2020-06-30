package com.pm.background.common.enumeration;

public enum UserTypeEnum {
    BACKUSER("1"),LITTLEAPPUSER("2");

    private String value;
    UserTypeEnum (String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
}
