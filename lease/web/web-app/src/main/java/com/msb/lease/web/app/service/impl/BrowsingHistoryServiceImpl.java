package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.BrowsingHistory;
import com.msb.lease.web.app.mapper.BrowsingHistoryMapper;
import com.msb.lease.web.app.service.BrowsingHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.history.HistoryItemVo;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        return mapper.queryHistory(page, userId);
    }


    /**
     * 保存浏览记录
     * 1、进入页面后，通过用户id和房间id，查看是否有该历史记录
     * 2、有记录则将时间更新为现在时间
     * 3、没有记录则添加记录
     */

    @Override
    @Async
    public void saveHistory(long userId, Long id) {
        //通过用户id和房间id查看历史记录
        //创建条件构造器
        LambdaQueryWrapper<BrowsingHistory> browsingHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        browsingHistoryLambdaQueryWrapper.eq(BrowsingHistory::getUserId, userId);
        browsingHistoryLambdaQueryWrapper.eq(BrowsingHistory::getRoomId, id);
        //待用mapper层的方法查询到历史记录
        BrowsingHistory browsingHistory = mapper.selectOne(browsingHistoryLambdaQueryWrapper);
        //判断
        if (browsingHistory != null) {
            //当存在历史记录时
            //将历史记录时间更新为最新时间
            browsingHistory.setBrowseTime(new Date());
            mapper.updateById(browsingHistory);
        } else {
            //不存在历史记录时,添加记录
            //创建历史记录
            BrowsingHistory browsingHistory1 = new BrowsingHistory();
            //给历史记录赋值
            browsingHistory1.setUserId(userId);
            browsingHistory1.setRoomId(id);
            browsingHistory1.setBrowseTime(new Date());
            //添加记录
            mapper.insert(browsingHistory1);
        }
    }
}