package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.BrowsingHistory;
import com.msb.lease.web.app.mapper.BrowsingHistoryMapper;
import com.msb.lease.web.app.service.BrowsingHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.history.HistoryItemVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {

    @Resource
    private BrowsingHistoryMapper mapper;
    //分页查询历史记录
    @Override
    public IPage<HistoryItemVo> getHistoryItem(IPage<HistoryItemVo> page, long userId) {
        return mapper.queryHistory(page,userId) ;
    }

}