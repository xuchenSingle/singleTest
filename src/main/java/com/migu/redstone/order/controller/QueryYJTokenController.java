/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package com.migu.redstone.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//import com.migu.redstone.common.dto.QueryTokenInfoReq;
import com.migu.redstone.common.interfaces.IQueryTokenService;
import com.migu.redstone.dto.QueryAuthCodeReq;
import com.migu.redstone.dto.QueryTokenReq;
import com.migu.redstone.dto.QueryTokenResp;


/**
 * 业务办理资格校验
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.controller
 * @类名称 QualificationController
 * @类描述 业务办理资格校验
 * @创建人 xuchen
 * @创建时间 2018年11月27日 下午3:10:29
 */
@RestController
@RequestMapping(value = "/cmbs/query")
public class QueryYJTokenController {

    /**
     * queryTokenService
     */
    @Autowired
    private IQueryTokenService queryTokenService;


    /**
     * queryToken
     * @param req [QueryTokenReq] 业务办理资格校验
     * @return 城市名
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/bossAbility/queryToken", produces = {
        "application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询token接口-能开", notes = "查询token接口-能开", response = String.class, tags = {"Cmbs_Query_YJPlatform", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = String.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = String.class)})
    public QueryTokenResp queryToken(@RequestBody @Validated QueryTokenReq req)
        throws Exception {
        return queryTokenService.queryToken(req);
    }


    /**
     * queryAuthCode
     * @param req [QueryAuthCodeReq] 业务办理资格校验
     * @param appKey [String]
     * @param sercet [String]
     * @return 城市名
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/bossAbility/queryAuthCode", produces = {
        "application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询queryAuthCode接口-能开", notes = "查询queryAuthCode接口-能开", response = String.class, tags = {
        "Cmbs_Query_YJPlatform", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = String.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = String.class)})

    public String queryAuthCode(@RequestBody @Validated QueryAuthCodeReq req, String appKey, String sercet)
        throws Exception {
        return queryTokenService.queryAuthCode(req, appKey, sercet);
    }

    /**
     * @param queryTokenStatus 业务办理资格校验
     * @return 城市名
     * @throws Exception
     */
//	@RequestMapping(value = "/bossAbility/queryTokenStatus", produces = {
//			"application/json" }, method = RequestMethod.POST)
//	@ApiOperation(value = "查询token有效性", notes = "查询token有效性", response = String.class, tags = { "Cmbs_Query_YJPlatform", })
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = String.class),
//			@ApiResponse(code = 200, message = "Unexpected error", response = String.class) })
//	
//	public String queryTokenStatus(@RequestBody @Validated QueryTokenInfoReq req)
//			throws Exception {
//		return queryTokenService.queryTokenStatus(req);
//	}

}
