package com.msb.lease.common.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.msb.lease.web.*.mapper")
public class MybatisPlusConfiguration {
}
