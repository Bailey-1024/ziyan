package com.msb.lease.web.admin.service;

import com.msb.lease.model.entity.FeeValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.lease.web.admin.vo.fee.FeeValueVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface FeeValueService extends IService<FeeValue> {
    /**
     * 通过公寓id查询杂费列表
     * @param id
     * @return
     */
    List<FeeValueVo> selectListByApartmentId(Long id);
}
