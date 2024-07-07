package com.msb.lease.web.app.controller.payment;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.PaymentType;
import com.msb.lease.web.app.service.PaymentTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "支付方式接口")
@RestController
@RequestMapping("/app/payment")
public class PaymentTypeController {
  @Autowired
  private PaymentTypeService typeService;
    @Operation(summary = "根据房间id获取可选支付方式列表")
    @GetMapping("listByRoomId")
    public Result<List<PaymentType>> list(@RequestParam Long id) {
        QueryWrapper<PaymentType> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        List<PaymentType> list = typeService.list(queryWrapper);
        return Result.ok(list);
    }

    @Operation(summary = "获取全部支付方式列表")
    @GetMapping("list")
    public Result<List<PaymentType>> list() {
        //直接调用MP的查询方法
        List<PaymentType> list = typeService.list();
        return Result.ok(list);
    }
}
