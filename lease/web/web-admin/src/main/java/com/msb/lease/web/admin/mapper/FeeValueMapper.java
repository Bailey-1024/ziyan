package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.lease.model.entity.FeeValue;
import com.msb.lease.web.admin.vo.fee.FeeValueVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.FeeValue
*/
public interface FeeValueMapper extends BaseMapper<FeeValue> {
    /**
     * 通过公寓id查询杂费列表
     * @param id
     * @return
     */
    List<FeeValueVo> selectListByApartmentId(Long id);
}




