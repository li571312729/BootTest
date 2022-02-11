package com.hefu.auth.security.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hefu.admin.entity.SysUser;
import com.hefu.admin.service.SysUserService;
import com.hefu.auth.security.entity.SelfUserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * SpringSecurity用户的业务实现
 * @Author Sans
 * @CreateTime 2019/10/1 17:21
 */
@Component
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户信息
     * @Author Sans
     * @CreateTime 2019/9/13 17:23
     * @Param  username  用户名
     * @Return UserDetails SpringSecurity用户信息
     */
    @Override
    public SelfUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser sysUserEntity =sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername,username).eq(SysUser::getStatus,1)
                .last("limit 1"));
        if (sysUserEntity!=null){
            // 组装参数
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            BeanUtils.copyProperties(sysUserEntity,selfUserEntity);
            return selfUserEntity;
        }
        return null;
    }
    //根据手机号查询用户
    public SelfUserEntity loadUserByMobile(String mobile) {
        SysUser sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, 1).eq(SysUser::getMobile, mobile)
                .last("limit 1"));
        if (sysUserEntity!=null){
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            BeanUtils.copyProperties(sysUserEntity,selfUserEntity);
            return selfUserEntity;
        }
        return null;
    }
}