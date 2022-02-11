package com.hefu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.auth.dao.SysRoleDao;
import com.hefu.auth.entity.SysRole;
import com.hefu.auth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色(SysRole)表服务实现类
 *
 *
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    //根据用户id查询用户角色
    @Override
    public List<SysRole> selectSysRoleByUserId(Long userId) {
        return sysRoleDao.selectSysRoleByUserId(userId);
    }
}