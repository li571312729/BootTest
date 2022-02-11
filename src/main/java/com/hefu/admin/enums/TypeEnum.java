package com.hefu.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {
    COMPREHENSIVE(0,"comprehensive_score"),
    ACTION(1,"action_score"),
    FOUND(2,"found_score"),
    TOWER(3,"tower_score"),
    FORMWORK(4,"formwork_score"),
    BULID(5,"build_score"),
    UPDATEPASSWORD(11,"updatePassword:"),
    UPDATEPHONE(12,"updatePhone:"),
    REGISTERPHONE(13, "registerPhone:");
    private Integer code;
    private String orderType;
}
