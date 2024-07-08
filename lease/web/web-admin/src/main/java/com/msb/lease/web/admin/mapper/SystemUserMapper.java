package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.SystemUser;
import com.msb.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserQueryVo;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @Entity com.msb.lease.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {


    /**
     * 多条件分页查询
     * @param iPage
     * @param queryVo
     * @return
     */
    IPage<SystemUserItemVo> selectUserPage(IPage<SystemUser> iPage, SystemUserQueryVo queryVo);


    /**
     * 根据id删除用户信息
     * @param id
     */
    void deleteByIdTo(Long id);


    /**
     * 通过用户名查询用户总数
     * @param username
     * @return
     */
    Integer countByUsername(String username);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SystemUser selectOneByUsername(String username);

}




