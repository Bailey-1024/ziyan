package com.msb.lease.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.LeaseAgreement;
import com.msb.lease.model.enums.LeaseStatus;
import com.msb.lease.web.admin.service.LeaseAgreementService;
import com.msb.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.msb.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@Tag(name = "租约管理")
@RestController
@RequestMapping("/admin/agreement")
public class LeaseAgreementController {
    @Resource
    private LeaseAgreementService service;

    @Operation(summary = "保存或修改租约信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        service.saveOrUpdate(leaseAgreement);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询租约列表")
    @GetMapping("page")
    public Result<IPage<AgreementVo>> page(@RequestParam long current, @RequestParam long size, AgreementQueryVo queryVo) {
        //分页查询
        IPage<AgreementVo> page = new Page<>(current, size);
        //调用service方法查询数据
        IPage<AgreementVo> result = service.selectLAPage(page,queryVo);
        //返回数据
        return Result.ok(result);
    }

    @Operation(summary = "根据id查询租约信息")
    @GetMapping(name = "getById")
    public Result<AgreementVo> getById(@RequestParam Long id) {
        AgreementVo result=service.getLessAgreementById(id);
        return Result.ok(result);
    }

    @Operation(summary = "根据id删除租约信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        service.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id更新租约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus status) {
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LeaseAgreement::getId,id).set(LeaseAgreement::getStatus,status);
        service.update(updateWrapper);
        return Result.ok();
    }

}

