package com.hefu.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hefu.auth.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色(SysRole)表数据库访问层
 *
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {
    List<SysRole> selectSysRoleByUserId(Long userId);
}