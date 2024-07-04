package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.SystemPost;

/**
* @author liubo
* @description 针对表【system_post(岗位信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.SystemPost
*/
public interface SystemPostMapper extends BaseMapper<SystemPost> {
    //分页查询
    IPage<SystemPost> pageByPost(IPage<SystemPost> iPage);
    //删除信息
    void updateByidTo(Long id);
}




