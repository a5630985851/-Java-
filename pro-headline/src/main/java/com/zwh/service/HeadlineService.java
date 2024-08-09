package com.zwh.service;

import com.zwh.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwh.pojo.PortalVo;
import com.zwh.pojo.Result;

/**
* @author LUNIX
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-08-06 22:39:14
*/
public interface HeadlineService extends IService<Headline> {
    public Result findNewPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline);

    Result findHeadlineByHid(Integer hid);

    Result updateHeadline(Headline headline);

    Result RemoveByHid(Integer hid);


}
