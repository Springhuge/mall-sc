package com.jihu.mall.ums;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.jihu.mall.ums.mapper","com.jihu.mall.auth.mapper"})
@ComponentScan(basePackages={"com.jihu.mall.auth","com.jihu.mall.ums"})
@EnableDiscoveryClient
public class MallUmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUmsApplication.class, args);
    }

}
