package com.msb.lease.web.app.controller.region;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.CityInfo;
import com.msb.lease.model.entity.DistrictInfo;
import com.msb.lease.model.entity.ProvinceInfo;
import com.msb.lease.web.app.service.CityInfoService;
import com.msb.lease.web.app.service.DistrictInfoService;
import com.msb.lease.web.app.service.ProvinceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "地区信息")
@RestController
@RequestMapping("/app/region")
public class RegionController {
@Autowired
private ProvinceInfoService provinceInfoService;
@Autowired
private CityInfoService cityInfoService;
@Autowired
private DistrictInfoService districtInfoService;
    @Operation(summary = "查询省份信息列表")
    @GetMapping("province/list")
    public Result<List<ProvinceInfo>> listProvince() {
        //直接调用MP封装好的方法 查询所有省份信息列表
        List<ProvinceInfo> list = provinceInfoService.list();
        return Result.ok(list);
    }

    @Operation(summary = "根据省份id查询城市信息列表")
    @GetMapping("city/listByProvinceId")
    public Result<List<CityInfo>> listCityInfoByProvinceId(@RequestParam Long id) {
        //调用Service层的查询方法返回一个list集合
        QueryWrapper<CityInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        List<CityInfo> list = cityInfoService.list(queryWrapper);
        return Result.ok(list);
    }

    @GetMapping("district/listByCityId")
    @Operation(summary = "根据城市id查询区县信息")
    public Result<List<DistrictInfo>> listDistrictInfoByCityId(@RequestParam Long id) {
        QueryWrapper<DistrictInfo>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        List<DistrictInfo> list = districtInfoService.list(queryWrapper);
        return Result.ok(list);
    }
}
