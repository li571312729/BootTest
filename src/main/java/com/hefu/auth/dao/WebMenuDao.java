package com.hefu.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hefu.auth.entity.WebMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedList;
import java.util.List;

/**
 * (WebMenu)表数据库访问层
 *
 */
@Mapper
public interface WebMenuDao extends BaseMapper<WebMenu> {
    //根据用户编号查询可操作资源
    List<String> selectSysMenuByUserId(Long userId);
    //查询该用户可操作的路由信息
    LinkedList<WebMenu> selectMenuTreeByUserId(Long userId);
    //查询所有权限资源的id
    List<Integer> selectAllMenu();
}