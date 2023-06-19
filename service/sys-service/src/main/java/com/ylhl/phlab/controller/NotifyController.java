package com.ylhl.phlab.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "回调接口")
@RequestMapping("/notify/")
public class NotifyController {

    @ApiOperation("短信回调")
    @RequestMapping(value = "/sms/callback",method = RequestMethod.POST)
    public JSONObject smsCallback(@RequestBody JSONObject param) {
        log.info("移动短信回调入参：[{}]",param);
        JSONObject res = new JSONObject();

        res.put("respCode","0");
        res.put("rtnMsg","成功");
        return res;
    }


}
