package com.msb.lease.common.minio;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)  //开启属性绑定
public class MinioConfiguration {
    @Resource
    private MinioProperties minioProperties;

    /**
     * 构建minio客户端对象
     * @return
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient
                .builder()
                //添加端点，用于访问存储桶和对象
                .endpoint(minioProperties.getEndpoint())
                //添加凭证--用户名和密码
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
