package com.meng.gmall.manager.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class PmsUploadUtil {
    public static String uploadImage(MultipartFile multipartFile) {
        String imgUrl = "http://192.168.5.155";
        //配置fdfs的全局链接地址
        String tracker = PmsUploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
            TrackerClient trackerClient = new TrackerClient();
            // 获得一个trackerServer的实例
            TrackerServer trackerServer = trackerClient.getConnection();
            // 通过tracker获得一个Storage链接客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //转换成二进制流
            byte[] bytes = multipartFile.getBytes();
            //获取文件名称后缀
            String originalFilename = multipartFile.getOriginalFilename();
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String[] uploadFiles = storageClient.upload_file(bytes, suffixName, null);
            for (String url : uploadFiles) {
                imgUrl += "/" + url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;
    }
}
