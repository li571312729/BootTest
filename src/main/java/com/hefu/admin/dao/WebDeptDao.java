package com.hefu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hefu.admin.entity.WebDept;
import com.hefu.common.entity.CountDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (WebDept)表数据库访问层
 *
 */
@Mapper
public interface WebDeptDao extends BaseMapper<WebDept> {
    //会议通讯录该项目下部门列表
    List<CountDept> selectProjectListCount(String userId, Integer projectId);
}