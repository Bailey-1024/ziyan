package com.msb.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 状态（类型）字段，在数据库中通常用一个数字表示一个状态（类型）。例如：订单状态
 * （1:待支付，2:待发货，3:待收货，4:已收货，5:已完结）。
 */

public enum ItemType implements BaseEnum {

    APARTMENT(1, "公寓"),

    ROOM(2, "房间");


    @EnumValue
    @JsonValue
    private Integer code;
    private String name;

    @Override
    public Integer getCode() {
        return this.code;
    }


    @Override
    public String getName() {
        return name;
    }

    ItemType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
