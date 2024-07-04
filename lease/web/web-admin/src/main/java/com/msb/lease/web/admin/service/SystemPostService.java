package com.msb.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.SystemPost;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author liubo
 * @description 针对表【system_post(岗位信息表)】的数据库操作Service
 * @createDate 2023-07-24 15:48:00
 */
public interface SystemPostService extends IService<SystemPost> {
    //分页查询
    IPage<SystemPost> pageByPost(IPage<SystemPost> iPage);
    //通过id删除信息
    void updateByidTo(Long id);
   //通过id查询用户信息
    SystemPost selectById(Long id);
}
