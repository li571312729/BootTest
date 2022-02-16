package com.hefu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.admin.dao.ProjectInformationDao;
import com.hefu.admin.entity.ProjectArea;
import com.hefu.admin.entity.ProjectInformation;
import com.hefu.admin.service.ProjectInformationService;
import com.hefu.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProjectInformation)表服务实现类
 *
 * @author makejava
 * @since 2020-02-04 20:08:17
 */
@Service
@Slf4j
public class ProjectInformationServiceImpl extends ServiceImpl<ProjectInformationDao, ProjectInformation> implements ProjectInformationService {
    @Autowired
    ProjectInformationDao projectInformationDao;

    /**
     * 显示用户关联项目下拉框
     *
     * @param userId 用户id
     * @return 项目信息
     */
    @Override
    public List<ProjectInformation> selectProjectList(Long userId) {
        if (null == userId) {
            throw new BaseException(999, "请先登录");
        }
        List<ProjectInformation> projectInformations = projectInformationDao.selectProjectListByUserId(userId);
        log.info("显示用户关联项目,list={}", projectInformations);
        return projectInformations;
    }


    /**
     * 根据项目名查询用户当前项目列表
     *
     * @param userId      用户id
     * @param projectName 项目名
     * @return 项目
     */
    @Override
    public List<ProjectInformation> selectProjectByName(Long userId, String projectName) {
        return projectInformationDao.selectProjectByName(userId, projectName);
    }

    /**
     * 项目施工单位列表
     *
     * @return
     */
    @Override
    public List<String> listConsUnits() {
        return projectInformationDao.selectListConsUnits();
    }

    /**
     * 荷福行为识别监控-根据项用户名查询当前项目列表，默认为当前登录用户
     *
     * @param queryUserId
     * @return
     */
    @Override
    public List<ProjectInformation> selectProjectByUserId(Long queryUserId) {
        return projectInformationDao.selectMProjectListByUserId(queryUserId);
    }

    /**
     * 查询项目大区（华东，华南，，，）分布情况
     *
     * @return
     */
    @Override
    public List<ProjectArea> areaList() {
        return projectInformationDao.areaList();
    }

    /**
     * 查询项目监测情况
     *
     * @return
     */
    @Override
    public List<ProjectInformation> monitor() {
        return projectInformationDao.monitor();
    }
}
