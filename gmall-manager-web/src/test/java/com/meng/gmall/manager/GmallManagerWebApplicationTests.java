package com.meng.gmall.manager;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GmallManagerWebApplicationTests {

    @Test
    public void contextLoads() throws Exception {
        //配置fdfs的全局链接地址
        String tracker = GmallManagerWebApplicationTests.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(tracker);
        TrackerClient trackerClient = new TrackerClient();
        // 获得一个trackerServer的实例
        TrackerServer trackerServer = trackerClient.getConnection();
        // 通过tracker获得一个Storage链接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String[] uploadInfos = storageClient.upload_file("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\小米手机1.jpg", "jpg", null);
        String url = "http://192.168.5.155";

        for (String uploadInfo : uploadInfos) {
            url += "/" + uploadInfo;
        }

        System.out.println(url);
    }

}
