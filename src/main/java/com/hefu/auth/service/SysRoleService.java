package com.hefu.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hefu.auth.entity.SysRole;

import java.util.List;

/**
 * 角色(SysRole)表服务接口
 *
 */
public interface SysRoleService extends IService<SysRole> {
    //根据用户id查询角色
    List<SysRole> selectSysRoleByUserId(Long userId);
}