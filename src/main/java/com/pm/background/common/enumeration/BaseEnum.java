package com.pm.background.common.enumeration;

/**
 * @author Larry
 * @date 2020/5/18 0018 16:30
 * @param
 * @return
 * @description  只有两种答案的枚举  1yes 0 no
 **/
public enum BaseEnum {
    YES(1),NO(0);
    private Integer value;

    BaseEnum(Integer value){
        this.value=value;
    }
    public Integer getValue(){
        return value;
    }
}
