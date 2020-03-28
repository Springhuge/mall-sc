package com.jihu.mall.ums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUnit {

    @Autowired
    private PasswordEncoder passwordEncoder;

    //测试密码
    @Test
    public void testPassword(){
        String password = passwordEncoder.encode("123456");
        System.out.println(password);
    }
}
