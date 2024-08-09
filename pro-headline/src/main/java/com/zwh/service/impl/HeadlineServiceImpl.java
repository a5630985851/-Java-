package com.zwh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwh.pojo.Headline;
import com.zwh.pojo.PortalVo;
import com.zwh.pojo.Result;
import com.zwh.pojo.ResultCodeEnum;
import com.zwh.service.HeadlineService;
import com.zwh.mapper.HeadlineMapper;
import com.zwh.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author LUNIX
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-08-06 22:39:14
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Autowired
    HeadlineMapper headlineMapper;
    @Autowired
    JwtHelper jwtHelper;

    @Override
    public Result findNewPage(PortalVo portalVo) {
        IPage<Headline> page = new Page(portalVo.getPageNum(),portalVo.getPageSize());
         headlineMapper.selectMyPage(page,portalVo);

        Map pageInfo = new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());
        Map pageInfomap = new HashMap<>();
        pageInfomap.put("pageInfo",pageInfo);
        IPage<Headline> page1 = new Page<>(1,5);
        headlineMapper.selectMytest(page1);

        System.out.println("总记录数为"+page1.getTotal());

        return Result.ok(pageInfomap);



    }

    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map headLineDetail = headlineMapper.selectDetailMap(hid);
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setPageViews((Integer) headLineDetail.get("pageViews")+1);
        headline.setVersion((Integer) headLineDetail.get("version"));

        headlineMapper.updateById(headline);

        Map data = new HashMap<>();
        data.put("headline",headLineDetail);

        return Result.ok(data);
    }

    @Override
    public Result publish(Headline headline) {
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headline.setPageViews(0);
        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        Map headlineMap = new HashMap<>();
        headlineMap.put("headline",headline);

        return Result.ok(headlineMap);
    }

    @Override
    public Result updateHeadline(Headline headline) {
      Integer version =  headlineMapper.selectById(headline.getHid()).getVersion();
      headline.setVersion(version);
      headline.setUpdateTime(new Date());
      headlineMapper.updateById(headline);


        return Result.ok(null);
    }

    @Override
    public Result RemoveByHid(Integer hid) {
        headlineMapper.deleteById(hid);
        return Result.ok(null);
    }



}




