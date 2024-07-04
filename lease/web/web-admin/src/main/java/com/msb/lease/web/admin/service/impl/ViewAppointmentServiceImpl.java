package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.ViewAppointment;
import com.msb.lease.web.admin.mapper.ViewAppointmentMapper;
import com.msb.lease.web.admin.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.msb.lease.web.admin.vo.appointment.AppointmentVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {
    @Resource
    private ViewAppointmentMapper mapper;
    //分页查询
    @Override
    public IPage<AppointmentVo> selectAVPage(IPage<AppointmentVo> page, AppointmentQueryVo queryVo) {
        return mapper.selectAVPage(page,queryVo);
    }
}




