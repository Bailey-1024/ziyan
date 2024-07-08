package com.msb.lease.web.app.custom.config;

import com.msb.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * 自定义类型转换类：将前端传递的参数String类型，转换成自定义的枚举类型
 * 实现接口：Converter<T,V>
 *     T：表示前端传递过来的参数类型
 *     V：表示需要转换成的参数类型
 */
@Component
public class StringToItemTypeConverter implements Converter<String, ItemType> {
    /**
     *
     * @param code  表示前端发送过来的参数
     * @return  表示需要返回的参数
     */
    @Override
    public ItemType convert(String code) {
        //获取枚举类中所有实例对象
        //枚举类.values  返回包含枚举类所有实例的数组，用于遍历枚举值
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getCode().equals(Integer.valueOf(code))){
                return itemType;
            }
        }
         throw new IllegalArgumentException("code："+code+"  非法");
    }
}
