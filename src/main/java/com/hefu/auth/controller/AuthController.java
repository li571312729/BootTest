package com.hefu.auth.controller;

import com.hefu.admin.service.SysUserService;
import com.hefu.auth.entity.WebMenu;
import com.hefu.auth.service.WebMenuService;
import com.hefu.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("auth")
@Api(tags = "权限模块")
public class AuthController {

    @Autowired
    WebMenuService webMenuService;
    @Autowired
    SysUserService sysUserService;
    //根据token查询用户信息
    @ApiOperation("根据token查询用户可操作资源信息")
    @PostMapping("/info")
    public Result selectAdminInfo(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("admin");
        List<WebMenu> webMenuTrees = webMenuService.selectWebMenuTree(userId);
        if(CollectionUtils.isEmpty(webMenuTrees)){
            return Result.error("暂无内容");
        }
        return Result.success(webMenuTrees);
    }

}
