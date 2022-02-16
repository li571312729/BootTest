package com.hefu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hefu.admin.entity.ProjectArea;
import com.hefu.admin.entity.ProjectInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (ProjectInformation)表数据库访问层
 *
 * @author mengkai
 * @since 2020-02-04 20:08:17
 */
@Mapper
public interface ProjectInformationDao extends BaseMapper<ProjectInformation> {
    /**
     * 显示用户关联的项目信息
     *
     * @param userId 用户id
     * @return 项目信息
     */
    List<ProjectInformation> selectProjectListByUserId(Long userId);


    /**
     * 根据用户id,项目名模糊查询项目
     *
     * @param userId      用户id
     * @param projectName 项目名
     * @return 项目信息
     */
    List<ProjectInformation> selectProjectByName(Long userId, String projectName);

    /**
     * 项目施工单位列表
     *
     * @return
     */
    List<String> selectListConsUnits();

    /**
     * 显示用户关联的项目信息-荷福行为监控系统
     *
     * @param userId 用户id
     * @return 项目信息
     */
    List<ProjectInformation> selectMProjectListByUserId(Long userId);

    /**
     * 查询项目大区（华东，华南，，，）分布情况
     *
     * @return
     */
    List<ProjectArea> areaList();

    /**
     * 查询项目监测情况
     *
     * @return
     */
    List<ProjectInformation> monitor();
}