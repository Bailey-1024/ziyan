package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.SystemPost;
import com.msb.lease.model.entity.SystemUser;
import com.msb.lease.web.admin.mapper.SystemPostMapper;
import com.msb.lease.web.admin.mapper.SystemUserMapper;
import com.msb.lease.web.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserQueryVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.msb.lease.model.enums.BaseStatus.ENABLE;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemPostMapper systemPostMapper;

    //通过id查询后台用户信息
    @Override
    public SystemUserItemVo selectById(Long id) {
//通过id查询系统用户信息
        SystemUser systemUser = systemUserMapper.selectById(id);
//通过岗位id查询岗位信息
        SystemPost systemPost = systemPostMapper.selectById(systemUser.getPostId());
//将systemUser对象存储到systemUserItemVo当中
        SystemUserItemVo systemUserItemVo = sel(systemUser);
        systemUserItemVo.setPostName(systemPost.getName());
        return systemUserItemVo;
    }

    //根据条件分页查询后台用户列表
    @Override
    public IPage<SystemUserItemVo> selectPage(IPage<SystemUser> iPage, SystemUserQueryVo queryVo) {
//调用Mapper层的分页条件查询方法
        IPage<SystemUserItemVo> page = systemUserMapper.selectUserPage(iPage, queryVo);

        return page;
    }
     //通过id删除用户信息  开启事物 实际上是修改操作
     @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(Long id) {
        systemUserMapper.deleteByIdTo(id);
    }
    //通过username查询用户总数
    @Override
    public Integer countByUsername(String username) {

        return systemUserMapper.countByUsername(username);
    }


    //将systemUser对象存储到systemUserItemVo当中
    public SystemUserItemVo sel(SystemUser systemUser) {
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
//姓名
        systemUserItemVo.setName(systemUser.getName());
//密码
        systemUserItemVo.setPassword(systemUser.getPassword());
//电话号码
        systemUserItemVo.setPhone(systemUser.getPhone());
//用户名
        systemUserItemVo.setUsername(systemUser.getUsername());
//用户类型
        systemUserItemVo.setType(systemUser.getType());
//头像地址
        systemUserItemVo.setAvatarUrl(systemUser.getAvatarUrl());
//备注信息
        systemUserItemVo.setAdditionalInfo(systemUser.getAdditionalInfo());
//岗位id
        systemUserItemVo.setPostId(systemUser.getPostId());
        return systemUserItemVo;
    }
}




