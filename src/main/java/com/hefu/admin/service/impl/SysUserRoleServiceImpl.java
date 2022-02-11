package com.hefu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.admin.dao.SysUserRoleDao;
import com.hefu.admin.entity.SysUserRole;
import com.hefu.admin.service.SysUserRoleService;
import com.hefu.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * (SysUserRole)表服务实现类
 * @author Administrator
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Slf4j
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Override
    public boolean transferProject(SysUserRole source, SysUserRole target) throws Exception {
        source.setRoleId(Constant.TWO_NUM);
        boolean b = this.updateById(source);
        if(!b){
            throw new Exception();
        }

        target.setRoleId(Constant.ONE_NUM);
        boolean b1 = this.updateById(target);
        if(!b1){
            throw new Exception();
        }
        return true;
    }
}