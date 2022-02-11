package com.hefu.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.admin.dao.SysUserDao;
import com.hefu.admin.dao.WebDeptDao;
import com.hefu.admin.dto.UserDto;
import com.hefu.admin.entity.SysUser;
import com.hefu.admin.entity.WebDept;
import com.hefu.admin.service.SysUserService;
import com.hefu.admin.vo.AdminInfoVO;
import com.hefu.admin.vo.SysUserVO;
import com.hefu.common.entity.EmployeeVO;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import com.hefu.common.utils.JacksonUtils;
import com.hefu.common.utils.Md5Util;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务实现类
 *
 */
@Service
@Slf4j
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private WebDeptDao webDeptDao;


    /**
     * 校验用户名唯一
     *
     * @param username
     * @return
     */
    @Override
    public SysUser checkUserName(String username) {
        SysUser username1 = sysUserDao.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        return username1;
    }

    /**
     * 校验用户名,密码
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Result login(String username, String password) {
        SysUser user = sysUserDao.selectOne(new QueryWrapper<SysUser>().eq("username", username).or()
                .eq("mobile", username));
        if (null == user) {
            return Result.error("用户名或手机号不存在,先注册");
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.error("用户名,手机号或者密码不能为空");
        }
        if ((Md5Util.encode(password)).equals(user.getPassword())) {
            return Result.success("登陆成功", user);
        }
        return Result.error("用户信息有误,请联系管理员");
    }

    //显示当前用户信息
    @Override
    public AdminInfoVO showCurrentAdmin(Long userId) {
        if (null == userId) {
            throw new BaseException(999, "请先登录");
        }
        AdminInfoVO adminInfoVO=sysUserDao.selectAdminAllInfo(userId);
        return adminInfoVO;
    }

    //登录校验验证码
    @Override
    public SysUser checkCode(String mailDTOEmail, String emailCode, Claims claims) throws Exception {
        SysUser sysUser = sysUserDao.selectOne(new QueryWrapper<SysUser>()
                .eq("email", mailDTOEmail).eq("status", 1));
        if (null == sysUser) {
            throw new BaseException(1004, "请提供相关材料联系管理员获取账号");
        }
        Map map = JacksonUtils.json2pojo(claims.getId(), Map.class);
        String code = String.valueOf(map.get(mailDTOEmail));
        if (StringUtils.isBlank(code)) {
            throw new BaseException(1005, "验证码失效,请重新获取验证码");
        }
        if (!(emailCode.toLowerCase()).equals(code.toLowerCase())) {
            throw new BaseException(1006, "验证码不正确");
        }
        log.info("邮箱验证码校验成功");
        return sysUser;
    }

    //修改密码
    @Override
    public int changePassWord(SysUser admin, String newpassword, String comfirmpassword) {
        if (null == admin) {
            throw new BaseException(999, "系统繁忙");
        }
        if (StringUtils.isBlank(newpassword) || StringUtils.isBlank(comfirmpassword)) {
            throw new BaseException(999, "请填写新密码和确认密码");
        }
        if (!newpassword.equals(comfirmpassword)) {
            throw new BaseException(999, "密码不一致");
        }
        admin.setPassword(Md5Util.encode(newpassword));
        int update = sysUserDao.update(admin, new QueryWrapper<SysUser>()
                .eq("user_id", admin.getUserId()).eq("status", 1));
        log.info("修改密码,user={}", admin);
        return update;
    }

    //修改邮箱
    @Override
    public int changeEmail(SysUser admin, String acCode, String code, String email) {
        if (null == admin) {
            throw new BaseException(999, "系统繁忙");
        }
        if (StringUtils.isBlank(acCode)) {
            throw new BaseException(1005, "验证码失效,请重新获取验证码");
        }
        if (!(acCode.toLowerCase()).equals(code.toLowerCase())) {
            throw new BaseException(1006, "验证码不正确");
        }
        SysUser sysUser = sysUserDao.selectOne(new QueryWrapper<SysUser>().eq("email", email)
                .eq("status", 1));
        if (sysUser != null) {
            throw new BaseException(999, "该邮箱已被注册");
        }
        admin.setEmail(email);
        int update = sysUserDao.update(admin, new QueryWrapper<SysUser>()
                .eq("user_id", admin.getUserId()).eq("status", 1));
        log.info("修改该用户邮箱,admin={}", admin);
        return update;
    }

    //根据部门查询该部门下的员工
    @Override
    public List<SysUser> selectdeptEmployeeList(String userId, Integer projectId, Integer webDeptId) {
        if(!StringUtils.isNotBlank(userId)){
            throw new BaseException(999,"请先登录");
        }
        List<SysUser> sysUsers=sysUserDao.selectdeptEmployeeList(userId,projectId,webDeptId);
        log.info("根据部门查询该部门下的员工,deptId={}",webDeptId);
        return sysUsers;
    }
    //显示当前项目下员工详情
    @Override
    public EmployeeVO showEmployeeDetail(Integer projectId, String userId) {
        EmployeeVO employeeVO=sysUserDao.showEmployeeDetail(projectId,userId);
        log.info("显示当前项目下员工详情,employee={}",employeeVO);
        return employeeVO;
    }

    //显示项目下部门列表
    @Override
    public List<WebDept> selectDeptList() {
        return webDeptDao.selectList(new LambdaQueryWrapper<WebDept>().select(WebDept::getWebDeptId, WebDept::getWebDeptName).eq(WebDept::getDeptFlag,1).eq(WebDept::getIsValid, 1));
    }

    //分页显示该项目部门下员工信息
    @Override
    public Map<String,Object> selectEmployeePage(Integer projectId, Integer webDeptId, Integer page, Integer size) {
        Map<String,Object> map = new HashMap<>();
        map.put("total",sysUserDao.selectCountByDeptAndProject(webDeptId,projectId));
        //map.put("head",employeeRoleNature);
        page=(page-1)*size;
        List<EmployeeVO> employeeVOS=sysUserDao.selectUserData(projectId,webDeptId,page,size);
        map.put("data",employeeVOS);
        log.info("分页显示该项目部门下员工信息,projectId={}",projectId);
        return map;
    }

    @Override
    public Map<String, Object> selectEmployeePageList(Map<String, Object> map) {
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("total",sysUserDao.countEmployee(map));
        List<SysUserVO> users = sysUserDao.selectUserDatas(map);
        resultMap.put("data",users);
        return resultMap;

    }

    @Override
    public List<UserDto> selectUserList(Map<String, Object> map) {
        return sysUserDao.selectUserList(map);
    }

}