package com.hefu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hefu.admin.entity.SysFile;
import com.hefu.admin.vo.response.FileResponseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author lifenxing
 * @date 2020/9/21 17:52
 */
@Mapper
public interface SysFileDao extends BaseMapper<SysFile> {
    List<FileResponseVO> queryListFile(@PathParam("fileName") String fileName, @PathParam("ids") List<Long> ids);
}
