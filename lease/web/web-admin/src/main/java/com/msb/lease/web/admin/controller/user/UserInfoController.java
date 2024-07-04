package com.msb.lease.web.admin.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.UserInfo;
import com.msb.lease.model.enums.BaseStatus;
import com.msb.lease.web.admin.service.UserInfoService;
import com.msb.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    //current:当前所处的页面    size:每个页面记录的数量     userInfoqueryVo:(phone:电话号码    status:状态)
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        //调用mb的分页，new一个页面，传入当前页面以及页面大小
        IPage<UserInfo> page = new Page<>(current,size);
        //mb的crud表达式，封装好的，query包，直接调用即可
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询，电话号码
        queryWrapper.like(queryVo.getPhone()!= null, UserInfo::getPhone,queryVo.getPhone());
        //精准查询，用户状态
        queryWrapper.eq(queryVo.getStatus()!= null, UserInfo::getStatus, queryVo.getStatus());
        //页面信息，调用service方法
        IPage<UserInfo> list = userInfoService.page(page,queryWrapper);

        return Result.ok(list);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        //更新工具包
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        //根据id查询
        updateWrapper.eq(UserInfo::getId,id);
        //设置状态值
        updateWrapper.set(UserInfo::getStatus,status);

        return Result.ok();
    }
}
