package com.zwh.service;

import com.zwh.pojo.Result;
import com.zwh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author LUNIX
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-08-06 22:39:14
*/
public interface UserService extends IService<User> {

   public Result<User> login(User user);

    Result getMess(String token);

    Result checkName(String username);

    Result regist(User user);
}
