package com.hefu.auth.security;

import com.hefu.auth.config.MobileCodeAuthenticationToken;
import com.hefu.auth.entity.SysRole;
import com.hefu.auth.security.entity.SelfUserEntity;
import com.hefu.auth.security.service.SelfUserDetailsService;
import com.hefu.auth.service.SysRoleService;
import com.hefu.config.redis.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    RedisCache redisCache;
    @Value("${user.achievecode}")
    private String userloginCode;

    /**
     *校验手机号
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileCodeAuthenticationToken tokenReq = (MobileCodeAuthenticationToken) authentication;
        // 查询用户是否存在
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByMobile(tokenReq.getMobile());
        if (null==userInfo) {
            throw new BadCredentialsException("该手机号下的用户不存在,请注册");
        }
        String code = redisCache.getCacheObject(userloginCode+tokenReq.getMobile());
        if (StringUtils.isBlank(code)){
            throw new BadCredentialsException("请重新获取手机验证码");
        }
        // 判断验证码是否正确
        if(!code.equals(tokenReq.getCode())){
            throw new BadCredentialsException("手机验证码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (userInfo.getStatus()==0){
            throw new BadCredentialsException("该用户已被冻结");
        }
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRole> sysRoleEntityList = roleService.selectSysRoleByUserId(userInfo.getUserId());
        for (SysRole sysRoleEntity: sysRoleEntityList){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleSign()));
        }
        userInfo.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (MobileCodeAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
