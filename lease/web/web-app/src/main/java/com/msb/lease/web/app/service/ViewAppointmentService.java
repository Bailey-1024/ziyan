package com.msb.lease.web.app.service;

import com.msb.lease.model.entity.ViewAppointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.msb.lease.web.app.vo.appointment.AppointmentItemVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {
   //通过ID查看预约看房详细信息
    AppointmentDetailVo getDetailById(Long id);
    //通过userId查看个人预约看房信息列表
    List<AppointmentItemVo> getItemByUserId(long userId);
}
