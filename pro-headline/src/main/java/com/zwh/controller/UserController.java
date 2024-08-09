package com.zwh.controller;

import com.alibaba.druid.util.StringUtils;
import com.zwh.pojo.Result;
import com.zwh.pojo.ResultCodeEnum;
import com.zwh.pojo.User;
import com.zwh.service.UserService;
import com.zwh.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtHelper jwtHelper;
    @PostMapping("login")
    public Result login(@RequestBody User user){
       Result result =  userService.login(user);
       return result;
    }
    @GetMapping("getUserInfo")
    public Result getUserMess(@RequestHeader String token){
        Result result = userService.getMess(token);
        return  result;
    }
    @PostMapping("checkUserName")
    public  Result checkUserName(String username){
       Result result = userService.checkName(username);
       return  result;
    }
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return  result;

    }
    @GetMapping("checkLogin")
    public Result CheckLogin(@RequestHeader String token){
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)){
            //没有传或者过期 未登录
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
