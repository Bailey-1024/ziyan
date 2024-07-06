package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msb.lease.model.entity.*;
import com.msb.lease.web.app.mapper.*;
import com.msb.lease.web.app.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.apartment.ApartmentItemVo;
import com.msb.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.msb.lease.web.app.vo.graph.GraphVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {
    @Resource
    private ViewAppointmentMapper viewAppointmentMapper;
    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;
    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Resource
    private GraphInfoMapper graphInfoMapper;
    //标签mapper
    @Resource
    private LabelInfoMapper labelInfoMapper;
    //公寓-标签mapper
    @Resource
    private ApartmentLabelMapper apartmentLabelMapper;
    //房间-标签mapper
    @Resource
    private RoomLabelMapper roomLabelMapper;

    @Override
    public AppointmentDetailVo getDetailById(Long id) {
        //创建预约看房详细信息表
        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        //通过看房信息表获取公寓ID ，获取公寓信息表
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(viewAppointment.getApartmentId());
        //通过公寓id获取房间信息
        RoomInfo roomInfo=roomInfoMapper.selectById(apartmentInfo.getId());
        //创建预约看房详情
        AppointmentDetailVo detailVo = new AppointmentDetailVo();
        //创建公寓基本信息
        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();
        /**
         * 创建标签列表
         *
         *  公寓标签表 ----只能查到那些公寓id有那些标签id
         *  房间标签表-----只能查到哪些房间id有那些标签id
         *  标签表 --------只能查到标签id对应那些标签名和标签类型
         *
         *  1、先找出有那些房间id和公寓id
         *  2、使用公寓和房间标签表通过id查找到各自对应的标签id列表
         *  3、循环便利标签id列表，得到标签表
         */
        //创建标签信息列表
        List<LabelInfo> labelInfos = new ArrayList<>();
        //通过公寓id获取标签信息，创建条件选择器（公寓表--公寓标签表）
        LambdaQueryWrapper<ApartmentLabel> labelInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //链表，当公寓标签表的公寓id，等于公寓id时
        labelInfoLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentInfo.getId());
        //使用公寓标签表的mapper的方法，查出公寓标签信息列表
        List<ApartmentLabel> apartmentLabels=apartmentLabelMapper.selectList(labelInfoLambdaQueryWrapper);
        //通过房间id获取标签信息，创建条件选择器（房间表--房间标签表）
        LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //链表，当房间标签表的房间id，等于房间id时
        roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId,roomInfo.getId());
        //使用房间标签表的mapper方法，查出房间标签表信息
        List<RoomLabel> roomLabels=roomLabelMapper.selectList(roomLabelLambdaQueryWrapper);
        /**
         * 循环两张表的信息，将信息存到标签表中
         */
        for (int i = 0; i < apartmentLabels.size(); i++) {
            //获取公寓标签表的房间id对应的标签id
            Long labelId = apartmentLabels.get(i).getLabelId();
            //通过标签id得到标签信息对象
            LabelInfo labelInfo = labelInfoMapper.selectById(labelId);
            //将公寓标签对象放进标签信息表中
            labelInfos.add(labelInfo);
        }
        for (int i = 0; i < roomLabels.size(); i++) {
            //获取房间标签表的id对应的标签id
            Long labelId = roomLabels.get(i).getLabelId();
            //通过标签id获得相应的标签对象
            LabelInfo labelInfo = labelInfoMapper.selectById(labelId);
            //将标签对象放入标签信息表中
            labelInfos.add(labelInfo);
        }



        /**
         * 创建图片列表
         */
        //创建图片信息列表
        List<GraphInfo> graphInfos2 = new ArrayList<>();
        //通过公寓id获取图片信息，创建条件选择器
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper2.eq(GraphInfo::getItemId, apartmentInfo.getId());
        //给图片信息赋值
        graphInfos2 = graphInfoMapper.selectList(graphInfoLambdaQueryWrapper2);
        //循环给图片列表复制
        //创建图片列表
        List<GraphVo> graphVos = new ArrayList<>();
        for (int i = 0; i < graphInfos2.size(); i++) {
            //创建图片对象
            GraphVo graphVo = new GraphVo();
            //设置图片的名称
            graphVo.setName(graphInfos2.get(i).getName());
            //设置图片的地址
            graphVo.setUrl(graphInfos2.get(i).getUrl());
            graphVos.add(graphVo);
        }


        //将预约看房信息表的内容拷贝到预约看房详情中
        BeanUtils.copyProperties(viewAppointment, detailVo);
        //将公寓信息表的内容拷贝到公寓基本信息中
        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        //设置参数
        //将标签列表设置到公寓基本信息中
        apartmentItemVo.setLabelInfoList(labelInfos);
        //将图片列表设置到公寓基本信息中
        apartmentItemVo.setGraphVoList(graphVos);
        //将公寓基本添加到预约看房信息中
        detailVo.setApartmentItemVo(apartmentItemVo);
        return detailVo;
    }
}




