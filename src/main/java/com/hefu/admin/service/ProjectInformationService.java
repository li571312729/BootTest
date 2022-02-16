package com.hefu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hefu.admin.entity.ProjectArea;
import com.hefu.admin.entity.ProjectInformation;

import java.util.List;

/**
 * (ProjectInformation)表服务接口
 *
 * @author makejava
 * @since 2020-02-04 20:08:17
 */
public interface ProjectInformationService extends IService<ProjectInformation> {
    /**
     * 显示用户关联项目下拉框
     *
     * @param userId 用户id
     * @return 项目信息
     */
    List<ProjectInformation> selectProjectList(Long userId);

    /**
     * 根据项目名查询用户当前项目列表
     *
     * @param userId      用户id
     * @param projectName 项目名
     * @return 项目
     */
    List<ProjectInformation> selectProjectByName(Long userId, String projectName);

    /**
     * 项目施工单位列表
     *
     * @return
     */
    List<String> listConsUnits();

    /**
     * 荷福行为识别监控-根据项用户名查询当前项目列表，默认为当前登录用户
     *
     * @param queryUserId
     * @return
     */
    List<ProjectInformation> selectProjectByUserId(Long queryUserId);

    /**
     * 查询项目所在大区（华东，华南，，，）情况
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