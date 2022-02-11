package com.hefu.auth.security;

import com.hefu.auth.config.UsernameAuthenticationToken;
import com.hefu.auth.entity.SysRole;
import com.hefu.auth.security.entity.SelfUserEntity;
import com.hefu.auth.security.service.SelfUserDetailsService;
import com.hefu.auth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义登录验证
 *
 *
 * @author Administrator
 */
@Component
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private SysRoleService roleService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernameAuthenticationToken tokenReq = (UsernameAuthenticationToken) authentication;
        SelfUserEntity selfUserEntity = selfUserDetailsService.loadUserByMobile(tokenReq.getUsername());
        if (selfUserEntity == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 还要判断密码是否正确进行加密的
        if (!new MD5PasswordEncoder().matches(tokenReq.getPassword(), selfUserEntity.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (selfUserEntity.getStatus()==0){
            throw new LockedException("该用户已被冻结");
        }
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRole> sysRoleEntityList = roleService.selectSysRoleByUserId(selfUserEntity.getUserId());
        for (SysRole sysRoleEntity: sysRoleEntityList){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleSign()));
        }
        selfUserEntity.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(selfUserEntity, null, selfUserEntity.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernameAuthenticationToken.class.isAssignableFrom(authentication));
    }
}