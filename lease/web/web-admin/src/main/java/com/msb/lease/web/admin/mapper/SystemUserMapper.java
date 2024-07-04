package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.SystemUser;
import com.msb.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserQueryVo;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    IPage<SystemUserItemVo> selectUserPage(IPage<SystemUser> iPage, SystemUserQueryVo queryVo);


}




