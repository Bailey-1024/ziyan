package com.msb.lease.common.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 封装MinIo相关信息的实体类
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {
    private String endpoint;//连接点
    private String accessKey;//用户名
    private String secretKey;//密码
    private String bucketName;//文件夹名称

}
