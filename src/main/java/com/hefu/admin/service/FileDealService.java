package com.hefu.admin.service;


import com.hefu.admin.vo.response.FileResponseVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileDealService {
    //文件上传
    FileResponseVO upload(MultipartFile file, String fileExtName, String uploadImg) throws IOException;
    //文件下载
    byte[] downloadFileTest(String fileUrl);
    //文件删除
    String deleteFile(String fileUrl);

}
