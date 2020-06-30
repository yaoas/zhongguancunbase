package com.pm.background.common.enumeration;

public enum UserRoleEnum {
    LITTLEAPPUSER("4");

    private String value;

    UserRoleEnum(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
}
