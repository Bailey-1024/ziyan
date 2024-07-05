package com.msb.lease.web.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.BrowsingHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.lease.web.app.vo.history.HistoryItemVo;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface BrowsingHistoryService extends IService<BrowsingHistory> {
    //通过当前登录的用户id分页查询历史记录
    IPage<HistoryItemVo> getHistoryItem(IPage<HistoryItemVo> page, long userId);
}
