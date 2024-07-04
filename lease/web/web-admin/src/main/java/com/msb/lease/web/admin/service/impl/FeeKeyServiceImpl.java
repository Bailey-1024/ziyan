package com.msb.lease.web.admin.service.impl;

import com.msb.lease.model.entity.FeeKey;
import com.msb.lease.web.admin.mapper.FeeKeyMapper;
import com.msb.lease.web.admin.service.FeeKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.fee.FeeKeyVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{
    @Resource
    private FeeKeyMapper feeKeyMapper;
    /**
     * 查询全部杂费名称和杂费值列表
     * @return
     */
    @Override
    public List<FeeKeyVo> feeInfoList() {
        return feeKeyMapper.feeInfoList();
    }
}




