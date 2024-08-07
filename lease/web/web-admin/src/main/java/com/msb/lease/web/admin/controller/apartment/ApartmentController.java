package com.msb.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.ApartmentInfo;
import com.msb.lease.model.enums.ReleaseStatus;
import com.msb.lease.web.admin.service.ApartmentInfoService;
import com.msb.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "公寓信息管理")
@RestController
@RequestMapping("/admin/apartment")
public class ApartmentController {
    @Resource
    private ApartmentInfoService apartmentInfoService;

    @Operation(summary = "保存或更新公寓信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ApartmentSubmitVo apartmentSubmitVo) {
        apartmentInfoService.saveOrUpdateApartment(apartmentSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询公寓列表")
    @GetMapping("pageItem")
    public Result<IPage<ApartmentItemVo>> pageItem(@RequestParam long current, @RequestParam long size, ApartmentQueryVo queryVo) {
        //开启分页
        Page<ApartmentItemVo> apartmentItemVos = new Page<>(current, size);
        //apartmentInfoService.page只会查询单表，即ApartmentInfo里面的东西
        IPage<ApartmentItemVo>  list = apartmentInfoService.pageApartmentItemByQuery(apartmentItemVos,queryVo);
        return Result.ok(list);
    }

    @Operation(summary = "根据ID获取公寓详细信息")
    @GetMapping("getDetailById")
    public Result<ApartmentDetailVo> getDetailById(@RequestParam Long id) {
        ApartmentDetailVo apartmentDetailVo = apartmentInfoService.getApartmentDetailById(id);
        return Result.ok(apartmentDetailVo);
    }

    @Operation(summary = "根据id删除公寓信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        apartmentInfoService.removeApartmentById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改公寓发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(@RequestParam Long id, @RequestParam ReleaseStatus status) {
        LambdaUpdateWrapper<ApartmentInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(id!=null,ApartmentInfo::getId,id);
        wrapper.set(id!=null,ApartmentInfo::getIsRelease,status);
        apartmentInfoService.update(wrapper);
        return Result.ok();
    }

    @Operation(summary = "根据区县id查询公寓信息列表")
    @GetMapping("listInfoByDistrictId")
    public Result<List<ApartmentInfo>> listInfoByDistrictId(@RequestParam Long id) {
        LambdaQueryWrapper<ApartmentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApartmentInfo::getDistrictId,id);
        List<ApartmentInfo> list = apartmentInfoService.list(wrapper);
        return Result.ok(list);
    }
}














