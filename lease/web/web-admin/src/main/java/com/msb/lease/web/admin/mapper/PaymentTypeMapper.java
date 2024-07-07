package com.msb.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.lease.model.entity.PaymentType;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.msb.lease.model.PaymentType
 */
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {
    /**
     * 根据房间id查询对应的支付列表
     * @param id
     * @return
     */
    List<PaymentType> selectListByRoomId(Long id);
}




