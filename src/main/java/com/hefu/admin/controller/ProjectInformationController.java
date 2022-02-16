package com.hefu.admin.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.hefu.admin.entity.ProjectInformation;
import com.hefu.admin.service.ProjectInformationService;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * (ProjectInformation)表控制层
 *
 * @author mengkai
 * @since 2019-12-16 11:46:51
 */
@Api(tags = "项目模块")
@RestController
@RequestMapping("project")
@Slf4j
public class ProjectInformationController {

    @Autowired
    private ProjectInformationService projectInformationService;

    @ApiOperation("根据项目名查询用户当前项目列表")
    @PostMapping("/select")
    public Result findAll(HttpServletRequest req, @RequestParam(required = false) String projectName) {
        Long userId = (Long) req.getAttribute("admin");
        List<ProjectInformation> list = projectInformationService.selectProjectByName(userId, projectName);
        log.info("根据项目名称查询用户当前项目,list={}", list);
        if (CollectionUtil.isNotEmpty(list)) {
            return Result.success(list);
        }
        throw new BaseException(999, "暂无内容");
    }

    @ApiOperation("个人关联项目信息")
    @GetMapping("show/list")
    public Result showList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("admin");
        List<ProjectInformation> projectInformations = projectInformationService.selectProjectList(userId);
        if (CollectionUtil.isNotEmpty(projectInformations)) {
            return Result.success(projectInformations);
        }
        log.error("个人关联项目信息下拉框暂无内容");
        throw new BaseException(999, "暂无内容");
    }
}
