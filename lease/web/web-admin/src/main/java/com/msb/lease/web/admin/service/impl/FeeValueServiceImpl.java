package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.model.entity.FeeValue;
import com.msb.lease.web.admin.service.FeeValueService;
import com.msb.lease.web.admin.mapper.FeeValueMapper;
import com.msb.lease.web.admin.vo.fee.FeeValueVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FeeValueServiceImpl extends ServiceImpl<FeeValueMapper, FeeValue>
    implements FeeValueService{
    @Resource
    private FeeValueMapper feeValueMapper;

    /**
     * 通过公寓id查询杂费列表
     * @param id
     * @return
     */
    @Override
    public List<FeeValueVo> selectListByApartmentId(Long id) {
        return feeValueMapper.selectListByApartmentId(id);
    }
}




