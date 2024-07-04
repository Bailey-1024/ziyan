package com.msb.lease.web.admin.service.impl;

import com.msb.lease.common.minio.MinioProperties;
import com.msb.lease.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

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
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

            //判断是否存在对应的文件夹
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
                                .bucket(minioProperties.getBucketName())//给哪个桶修改访问权限
                                .config(createBucketPolicyConfig(minioProperties.getBucketName()))
                                .build()
                );
            }
            String filename = new SimpleDateFormat("yyyyMMdd")
                    .format(new Date())
                    +"/"+
                    UUID.randomUUID()
                    +"-"+
                    file.getOriginalFilename();

            //将本地文件上传到minIO服务器
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            //将数据保存到哪个文件夹中
                            .bucket(minioProperties.getBucketName())
                            //保存的文件名称
                            .object(filename)
                            //以流的形式，将文件上传到服务器中
                            .stream(file.getInputStream(),file.getSize(),-1)
                            //设置请求的类型
                            .contentType(file.getContentType())
                            .build()
            );
            //返回文件存储路径
            return String.join(
                    "/",
                    minioProperties.getEndpoint(),
                    minioProperties.getBucketName(),
                    filename);


    }

    /**
     * 传入文件夹名称，创建访问该文件夹的策略
     * @param bucketName
     * @return
     */
    private String createBucketPolicyConfig(String bucketName) {
        /**
         * 自定义访问规则
         *  允许所有人访问
         *  只允许自己写
         */
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
