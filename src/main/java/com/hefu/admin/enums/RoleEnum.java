package com.hefu.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    MANAGER(1,"项目管理员"),
    SAFETYOFFICER(2,"安全员"),
    USER(3,"其他用户");
    private Integer roleId;
    private String roleName;
}
