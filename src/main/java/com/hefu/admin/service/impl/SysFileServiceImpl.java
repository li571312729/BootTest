package com.hefu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.admin.dao.SysFileDao;
import com.hefu.admin.entity.SysFile;
import com.hefu.admin.service.FileDealService;
import com.hefu.admin.service.SysFileService;
import com.hefu.admin.vo.response.FileResponseVO;
import com.hefu.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lifenxing
 * @date 2020/9/21 17:51
 */
@Service
@Slf4j
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFile> implements SysFileService {

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private FileDealService fileDealService;

    @Override
    public FileResponseVO add(String url, Integer type, Long fileSize, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String[] fileNameArr = originalFilename.split("\\.");
        String suffix = fileNameArr[fileNameArr.length - 1];
        String fileName = fileNameArr[fileNameArr.length - 2] + "." + fileNameArr[fileNameArr.length - 1];
        SysFile sysFile = new SysFile();
        FileResponseVO vo = new FileResponseVO();
        sysFile.setKey(url);
        sysFile.setName(fileName);
        sysFile.setSuffix(suffix);
        sysFile.setSize(fileSize.intValue());
        sysFile.setType(type);
        baseMapper.insert(sysFile);
        BeanUtils.copyProperties(sysFile,vo);
        return vo;
    }

    @Override
    public List<FileResponseVO> getListFile(String fileName,List<Long> ids) {
        List<FileResponseVO> fileList = baseMapper.queryListFile(fileName,ids);
        return  fileList;
    }

    @Override
    public void deleteFile(Long fileId) throws Exception {
        SysFile file = sysFileService.getById(fileId);
        String s = fileDealService.deleteFile(file.getKey());
        if(Utils.isNullOrEmpty(s)){
            throw new Exception();
        }

        boolean b = sysFileService.removeById(fileId);
        if(!b){
            throw new Exception();
        }
    }
}
