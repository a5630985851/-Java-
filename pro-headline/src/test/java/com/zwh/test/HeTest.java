package com.zwh.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwh.mapper.HeadlineMapper;
import com.zwh.pojo.Headline;
import com.zwh.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeTest {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private  HeadlineMapper headlineMapper;
    @Test
    public void test01(){
        String token = jwtHelper.createToken(1L);

        if(jwtHelper.isExpiration(token)){
            System.out.println("已过期");
        }
        System.out.println("在有效期内");
        System.out.println("id为："+jwtHelper.getUserId(token));
        System.out.println("id为："+jwtHelper.getUserId(token).intValue());
    }
    @Test
    public void test02(){
        Page<Headline> page = new Page<>(1,5);
        headlineMapper.selectPage(page,null);
       System.out.println("总记录"+page.getTotal());

    }
}
