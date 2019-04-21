/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.controller;

import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.*;
import com.migu.redstone.order.service.dto.response.*;
import com.migu.redstone.order.service.interfaces.ICmbsQueryHaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * 河南接口服务
 */
@RestController
@Api(value = "河南查询服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsQueryHaController {

    @Autowired
    private ICmbsQueryHaService cmbsQueryHnService;

    /**
     * 消息模板接收接口（提供给河南使用） TSG能力对接平台 请求 视讯推送平台
     *
     * @param req PushTemplateInfoReq
     * @return resp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/pushTemplateInfo", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "消息模板接收接口", notes = "消息模板接收接口", response = PushTemplateInfoResp.class, tags = {"cmbs_ha",})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = PushTemplateInfoResp.class)})
    public PushTemplateInfoResp pushTemplateInfo(@RequestBody @Validated PushTemplateInfoReq req)
            throws Exception {
        return cmbsQueryHnService.pushTemplateInfo(req);
    }

}
