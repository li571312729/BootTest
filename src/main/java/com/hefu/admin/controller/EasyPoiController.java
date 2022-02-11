package com.hefu.admin.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.hefu.admin.dto.UserDto;
import com.hefu.admin.service.SysUserService;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import com.hefu.component.ExcelDataHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyPoi导入导出测试Controller
 * @author xiaoqiangli
 * @Date 2022-02-09
 */
@RestController
@Api(value = "EasyPoiController", tags = "EasyPoi导入导出测试")
@RequestMapping("easyPoi")
@Slf4j
public class EasyPoiController {

    @Autowired
    private SysUserService sysUserService;

    // 这里如果文件名称是中文，则swagger下载会是乱码，，但是直接发请求则是好的，因为swagger没有进行解码，，如果直接发请求是好的
    // 前端下载是乱码，，那让前端参考 https://blog.csdn.net/weixin_44140483/article/details/107078314
    @ApiOperation(value = "导出用户列表Excel")
    @ApiImplicitParam(name = "fileName", value = "导出文件名称", paramType="query", dataType="String", defaultValue = "memberList")
    @RequestMapping(value = "/exportMemberList", method = RequestMethod.GET)
    public void exportMemberList(@RequestParam(value = "fileName", defaultValue = "memberList") String fileName,
                                 @ApiIgnore ModelMap map,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Map<String, Object> qmap = new HashMap<>(16);

        List<UserDto> userDtos = sysUserService.selectUserList(qmap);
        log.info("查询当前所有用户详细信息,userDtos={}", userDtos);

        ExportParams params = new ExportParams("用户列表", "用户列表", ExcelType.XSSF);
        map.put(NormalExcelConstants.DATA_LIST, userDtos);
        map.put(NormalExcelConstants.CLASS, UserDto.class);
        map.put(NormalExcelConstants.PARAMS, params);
        map.put(NormalExcelConstants.FILE_NAME, fileName);
        params.setDataHandler(new ExcelDataHandler()); // 属性拦截器，增加超链接处理
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }

    @ApiOperation("从Excel导入用户列表")
    @RequestMapping(value = "/importMemberList", method = RequestMethod.POST)
    public Result importMemberList(@RequestPart("file") MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<UserDto> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), UserDto.class, params);
            log.info("导入用户列表为,userDtos={}", list);
        } catch (Exception e) {
            throw new BaseException(501, e.getMessage());
        }
        return Result.success(list);
    }

}

