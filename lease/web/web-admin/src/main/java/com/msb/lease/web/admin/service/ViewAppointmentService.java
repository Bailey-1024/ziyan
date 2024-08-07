package com.msb.lease.web.admin.service;

import com.msb.lease.model.entity.ViewAppointment;
import com.msb.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.msb.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {

    //分页查询
    IPage<AppointmentVo> selectAVPage(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}
