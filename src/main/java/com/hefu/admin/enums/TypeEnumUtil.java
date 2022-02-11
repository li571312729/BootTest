package com.hefu.admin.enums;

public class TypeEnumUtil {
    public static <T extends TypeEnum> T getByCode(Class<T> enumClass, Integer code) {
        for (T each : enumClass.getEnumConstants()) {
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }

    public static <T extends RoleEnum> T getByRoleId(Class<T> enumClass, Integer roleId) {
        for (T each : enumClass.getEnumConstants()) {
            if(each.getRoleId().equals(roleId)){
                return each;
            }
        }
        return null;
    }
}