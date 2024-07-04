package com.msb.lease.web.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传功能
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
