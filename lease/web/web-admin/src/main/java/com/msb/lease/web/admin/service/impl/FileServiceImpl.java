package com.msb.lease.web.admin.service.impl;

import com.msb.lease.common.minio.MinioProperties;
import com.msb.lease.web.admin.service.FileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private MinioProperties minioProperties;
    @Resource
    private MinioClient minioClient;
    /**
     * 文件上传功能
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        //判断是否存在对应的文件夹
        try {
            boolean bucketExiste = minioClient.bucketExists(
                    BucketExistsArgs
                            .builder()
                            .bucket(minioProperties.getBucketName())
                            .build()
            );
            //如果不存在，就创建对应的文件夹
            if (!bucketExiste){
                //创建文件夹
                minioClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(minioProperties.getBucketName())
                                .build()
                );
                //设置访问策略
                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs
                                .builder()
                                .bucket(minioProperties.getBucketName())
                                .config(createBucketPolicyConfig(minioProperties.getBucketName()))
                                .build()
                );
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入文件夹名称，创建访问该文件夹的策略
     * @param bucketName
     * @return
     */
    private String createBucketPolicyConfig(String bucketName) {
        return """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": {
                                "AWS": [
                                    "*"
                                ]
                            },
                            "Action": [
                                "s3:GetObject"
                            ],
                            "Resource": [
                                "arn:aws:s3:::%s/*"
                            ]
                        }
                    ]
                }
                """.formatted(bucketName);
    }
}
