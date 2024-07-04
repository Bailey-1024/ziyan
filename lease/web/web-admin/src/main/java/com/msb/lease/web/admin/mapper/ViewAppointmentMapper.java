package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.ViewAppointment;
import com.msb.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.msb.lease.web.admin.vo.appointment.AppointmentVo;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {
    //分页查询
    IPage<AppointmentVo> selectAVPage(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}




