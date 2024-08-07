package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.model.entity.CityInfo;
import com.msb.lease.web.app.service.CityInfoService;
import com.msb.lease.web.app.mapper.CityInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【city_info】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo>
        implements CityInfoService {

}




