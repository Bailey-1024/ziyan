package com.msb.lease.web.app.mapper;

import com.msb.lease.model.entity.BrowsingHistory;
import com.msb.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.msb.lease.model.entity.BrowsingHistory
*/
public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {

    //分页查询历史记录
    IPage<HistoryItemVo> queryHistory(IPage<HistoryItemVo> page, long userId);



}




