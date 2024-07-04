package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.model.entity.SystemPost;
import com.msb.lease.web.admin.service.SystemPostService;
import com.msb.lease.web.admin.mapper.SystemPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liubo
 * @description 针对表【system_post(岗位信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemPostServiceImpl extends ServiceImpl<SystemPostMapper, SystemPost>
        implements SystemPostService {
    @Autowired
    private SystemPostMapper systemPostMapper;

    //分页查询操作
    @Override
    public IPage<SystemPost> pageByPost(IPage<SystemPost> iPage) {
        //调用Mapper层的分页方法

        return systemPostMapper.pageByPost(iPage);
    }

    //通过id删除用户信息 其实是修改操作 开启事物
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateByidTo(Long id) {
        //调用mapper层方法
        systemPostMapper.updateByidTo(id);
    }

    //通过id查询用户信息
    @Override
    public SystemPost selectById(Long id) {
        //mybatisplus自带的方法
        SystemPost systemPost = systemPostMapper.selectById(id);
        return systemPost;
    }
}




