package com.hefu.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hefu.auth.entity.WebMenu;

import java.util.List;

/**
 * (WebMenu)表服务接口
 *
 */
public interface WebMenuService extends IService<WebMenu> {
    //根据用户id查询可操作资源
    List<String> selectSysMenuByUserId(Long userId);
    //根据用户id查询前端资源树
    List<WebMenu> selectWebMenuTree(Long userId);
}