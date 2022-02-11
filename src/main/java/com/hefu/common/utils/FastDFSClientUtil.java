package com.hefu.common.utils;

import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
*
*
* @description: FastDFSClientUtil FastDfs 客户端连接工具类
**/
@Component
public class FastDFSClientUtil {
 
//	@Value("${fdfs.reqHost}")
//    private String reqHost;
//
//    @Value("${fdfs.reqPort}")
//    private String reqPort;
	
    @Resource
    private FastFileStorageClient storageClient;


//    /**
//     * 文件上传
//     * @param file
//     * @return
//     * @throws IOException
//     */
//   public FileInfo uploadFile(MultipartFile file) throws IOException {
//       StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(),
//               FilenameUtils.getExtension(file.getOriginalFilename()),null);
//       String fileType = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
//       FileInfo fileInfo = FileInfo.builder().fileId(storePath.getFullPath())
//               .fileName(file.getOriginalFilename())
//               .fileSize(file.getSize())
//               .fileType(fileType)
//               .filePath(getResAccessUrl(storePath))
//               .build();
//       return fileInfo;
//   }



    /**
     * 文件删除
     * @param filePath
     */
   public void delFile(String filePath) { 
	   storageClient.deleteFile(filePath);
   }


    /**
     * 文件下载
     * @param groupName
     * @param path
     * @return
     */
   public InputStream download(String groupName, String path ) {
	   InputStream ins =  storageClient.downloadFile(groupName, path, new DownloadCallback<InputStream>(){
		@Override
		public InputStream recv(InputStream ins) throws IOException {
			// 将此ins返回给上面的ins 
			return ins;
		}}) ;
	   return ins ;
   }
   
//    /**
//      * 封装文件完整URL地址
//     * @param storePath
//     * @return
//     */
//   private String getResAccessUrl(StorePath storePath) {
//       String fileUrl = "http://" + reqHost + ":" + reqPort + "/" + storePath.getFullPath();
//       return fileUrl;
//   }
}
