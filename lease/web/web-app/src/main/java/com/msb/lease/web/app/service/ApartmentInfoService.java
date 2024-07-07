package com.msb.lease.web.app.service;

import com.msb.lease.model.entity.ApartmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.msb.lease.web.app.vo.apartment.ApartmentItemVo;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
 * @createDate 2023-07-26 11:12:39
 */
public interface ApartmentInfoService extends IService<ApartmentInfo> {
    //根据获取公寓信息
    ApartmentDetailVo getDetailById(Long id);
    //通过公寓id查询公寓信息
    ApartmentItemVo  selectByListId(Long id);
}
