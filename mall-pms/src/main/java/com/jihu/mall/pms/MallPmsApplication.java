package com.jihu.mall.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan({"com.jihu.mall.pms.mapper","com.jihu.mall.auth.mapper"})
@ComponentScan(basePackages={"com.jihu.mall.auth","com.jihu.mall.pms"})
@SpringBootApplication
public class MallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPmsApplication.class, args);
    }

}
