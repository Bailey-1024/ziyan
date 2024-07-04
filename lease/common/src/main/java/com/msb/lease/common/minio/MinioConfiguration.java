package com.msb.lease.common.minio;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioConfiguration.class)  //开启属性绑定
public class MinioConfiguration {
    @Resource
    private MinioProperties minioProperties;

    /**
     * 构建minio客户端连接对象
     * @return
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient
                .builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
