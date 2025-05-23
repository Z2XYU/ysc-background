package com.ysc.controller;

import com.ysc.utils.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@CrossOrigin
public class UploadController {

    //依赖注入
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file" ,required = false) MultipartFile image) {
        log.info("正在上传，文件名{}",image.getOriginalFilename());
        String url = TencentCOSUtil.upLoadFile(image);
        log.info("文件的Url：{}",url);
        return url;

    }
}
