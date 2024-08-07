package com.msb.lease.web.app.mapper;

import com.msb.lease.model.entity.FeeValue;
import com.msb.lease.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
 * @createDate 2023-07-26 11:12:39
 * @Entity com.msb.lease.model.entity.FeeValue
 */
public interface FeeValueMapper extends BaseMapper<FeeValue> {
    //查询学杂费列表
    List<FeeValueVo> selectByListId(Long id);
}




