package com.zwh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zwh.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwh.pojo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author LUNIX
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-08-06 22:39:14
* @Entity com.zwh.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage<Headline> page, @Param("portalVo") PortalVo portalVo);
    IPage<Headline> selectMytest(IPage<Headline> page);

   public Map selectDetailMap(Integer hid);
}




