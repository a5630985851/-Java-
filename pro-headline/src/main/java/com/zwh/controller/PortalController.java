package com.zwh.controller;

import com.zwh.pojo.PortalVo;
import com.zwh.pojo.Result;
import com.zwh.pojo.Type;
import com.zwh.service.HeadlineService;
import com.zwh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private  HeadlineService headlineService;

    /**
     * 查询全部类别信息
     * @return
     */
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        //直接调用业务层,查询全部数据
        List<Type> list = typeService.list();
        return  Result.ok(list);
    }
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
       Result result = headlineService.findNewPage(portalVo);
       return  result;
    }
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return  result;

    }
}