package com.msb.lease.web.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.model.entity.SystemUser;
import com.msb.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface SystemUserService extends IService<SystemUser> {
     //通过id查询用户信息
    SystemUserItemVo selectById(Long id);
    //根据条件分页查询后台用户列表
    IPage<SystemUserItemVo> selectPage(IPage<SystemUser> iPage, SystemUserQueryVo queryVo);

    //通过id删除用户信息
    void deleteById(Long id);
    // 通过username查询用户总数
    Integer countByUsername(String username);
}
