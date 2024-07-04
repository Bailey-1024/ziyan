package com.msb.lease.web.admin.service;

import com.msb.lease.model.entity.FeeKey;
import com.msb.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface FeeKeyService extends IService<FeeKey> {
    /**
     * 查询全部杂费名称和杂费值列表
     * @return
     */
    List<FeeKeyVo> feeInfoList();
}
