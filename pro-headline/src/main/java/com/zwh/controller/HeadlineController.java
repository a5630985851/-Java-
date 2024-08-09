package com.zwh.controller;

import com.alibaba.druid.util.StringUtils;
import com.zwh.pojo.Headline;
import com.zwh.pojo.Result;
import com.zwh.pojo.ResultCodeEnum;
import com.zwh.service.HeadlineService;
import com.zwh.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("headline")
public class HeadlineController {
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    HeadlineService headlineService;
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token){
        Integer hid = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(hid);
       Result result = headlineService.publish(headline);
        return result;
    }
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return  result;
    }
    @PostMapping("update")
    public  Result updateHeadline(@RequestBody Headline headline){
        Result result = headlineService.updateHeadline(headline);
        return  result;

    }
    @PostMapping("removeByHid")
    public Result RemoveByhid(Integer hid){
        Result result = headlineService.RemoveByHid(hid);
        return  result;
    }


}
