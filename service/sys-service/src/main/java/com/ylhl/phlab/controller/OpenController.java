package com.ylhl.phlab.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@Api(tags = "开放接口")
@RequestMapping("/open")
public class OpenController {

    @ApiOperation("自动登录")
    @RequestMapping(value = "/user/authorize",method = RequestMethod.POST)
    public JSONObject code(@RequestBody JSONObject param) {
        log.info("自动登录系统：[{}]",param);
        JSONObject res = new JSONObject();
        //1 根据code获取token
        //2 根据token获取用户信息
        //3 根据用户信息关联/创建用户
        //4 返回登录成功数据

        res.put("respCode","0");
        res.put("rtnMsg","成功");
        return res;
    }

    @ApiOperation("校验登录")
    @RequestMapping(value = "/user/checkLogin",method = RequestMethod.POST)
    public JSONObject checkLogin(@RequestBody JSONObject param) {
        log.info("校验登录：[{}]",param);
        JSONObject res = new JSONObject();
        //1 验签
        res.put("respCode","0");
        res.put("rtnMsg","成功");
        return res;
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/user/token",method = RequestMethod.POST)
    public JSONObject token(@RequestBody JSONObject param) {
        log.info("获取登录用户信息：[{}]",param);
        JSONObject res = new JSONObject();
        //1 验签
        res.put("respCode","0");
        res.put("rtnMsg","成功");
        return res;
    }

}
