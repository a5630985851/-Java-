package com.zwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwh.pojo.MD5Util;
import com.zwh.pojo.Result;
import com.zwh.pojo.ResultCodeEnum;
import com.zwh.pojo.User;
import com.zwh.service.UserService;
import com.zwh.mapper.UserMapper;
import com.zwh.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author LUNIX
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-08-06 22:39:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginer = userMapper.selectOne(lambdaQueryWrapper);
        if(loginer == null){
            System.out.println("用户不存在！");
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);

        }
        /*//MD5Util.encrypt(user.getUserPwd())是加密工具，数据库的userPsd是密文存储
        * 因此进行密码比对时，要先将明文密码转为密文再与数据库密码比对
        * */

        if(!StringUtils.isEmpty(loginer.getUserPwd())
                &&loginer.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd())))

        {
            String token = jwtHelper.createToken(Long.valueOf(loginer.getUid()));
            Map data = new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }
        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getMess(String token) {
       if (jwtHelper.isExpiration(token)){
           System.out.println("已过期！");
           return Result.build(null,ResultCodeEnum.NOTLOGIN);
       }
       User user = userMapper.selectById(jwtHelper.getUserId(token));
       if(user!=null){
           Map data = new HashMap<>();
           data.put("loginUser",user);
           return Result.ok(data);
       }


        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    @Override
    public Result checkName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if(user!=null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
       Long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int rows = userMapper.insert(user);
        System.out.println("rows="+rows);

        return Result.ok(null);
    }
}




