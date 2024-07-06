package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.model.entity.ApartmentInfo;
import com.msb.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentSubmitVo;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {


    /**
     * 多条件分页查询
     * @param apartmentItemVos
     * @param queryVo
     * @return
     */
    IPage<ApartmentItemVo> pageApartmentByQuery(Page<ApartmentItemVo> apartmentItemVos, ApartmentQueryVo queryVo);
}




