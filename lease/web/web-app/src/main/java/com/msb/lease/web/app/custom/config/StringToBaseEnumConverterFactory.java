package com.msb.lease.web.app.custom.config;

import com.msb.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    /**
     *
     * @param targetType  表示前端传过来的数据的类型的字节码
     *                    通过这个可以获取到该枚举类的所有实例对象
     * @return
     * @param <T>
     */
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            /**
             * 匿名内部类写法
             * @param code  代表前端传过来的具体数据
             * @return
             */
            @Override
            public T convert(String code) {
                //targetType.getEnumConstants()  拿到目标类型的全部实例
                for (T enumConstant : targetType.getEnumConstants()) {
                    if (enumConstant.getCode().equals(Integer.valueOf(code))){
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("非法的枚举值："+code);
            }
        };
    }
}
