package com.msb.lease.web.app.controller.appointment;


import com.msb.lease.common.login.LoginUserHolder;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.ViewAppointment;
import com.msb.lease.web.app.service.ViewAppointmentService;
import com.msb.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.msb.lease.web.app.vo.appointment.AppointmentItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "看房预约信息")
@RestController
@RequestMapping("/app/appointment")
public class ViewAppointmentController {
    @Resource
    private ViewAppointmentService service;

    @Operation(summary = "保存或更新看房预约")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ViewAppointment viewAppointment) {
        service.saveOrUpdate(viewAppointment);
        return Result.ok();
    }


    /**
     * 查看个人预约看房列表
     *
     * 1、获取登录这的userId
     * 2、通过userId查询预约看房列表
     * @return
     */
    @Operation(summary = "查询个人预约看房列表")
    @GetMapping("listItem")
    public Result<List<AppointmentItemVo>> listItem() {
        //通过当前登陆者获取登录者id
        long userId = LoginUserHolder.getLoginUser().getUserId();
        //通过userId查询预约看房列表
        List<AppointmentItemVo> result=service.getItemByUserId(userId);
        return Result.ok(result);
    }

    @GetMapping("getDetailById")
    @Operation(summary = "根据ID查询预约详情信息")
    public Result<AppointmentDetailVo> getDetailById(Long id) {
        AppointmentDetailVo result=service.getDetailById(id);
        return Result.ok(result);
    }

}

