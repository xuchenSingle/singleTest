/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.migu.redstone.order.service.dto.request.*;
import com.migu.redstone.order.service.dto.response.*;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJService;


/**
 * 类作用:    CmbsQueryExclusiveFlowUseController
 * 项目名称:   cmbs-query-order
 * 包:       com.migu.redstone.order.controller
 * 类名称:    CmbsQueryExclusiveFlowUseController
 * 类描述:    cmbs定向流量使用controller
 * 创建人:    xuchen
 * 创建时间:   2018年11月5日 上午11:15:08
 */
@RestController
@Api(value = "查询服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsQueryYJController {
    /**
     * cmbsQueryYJService
     */
    @Autowired
    private ICmbsQueryYJService cmbsQueryYJService;

    /**
     * <queryExclusiveFlowUseReq>.
     * <查询流量使用情况>
     *
     * @param flowInfoReq [QueryFlowInfoReq]
     * @return [查询流量使用情况]
     * @throws Exception [Exception]
     * @author xuchen
     */
    @RequestMapping(value = "/video/queryFlowInfo", produces = {"application/json"},
        method = RequestMethod.POST)
    @ApiOperation(value = "查询套餐内流量使用情况6.2.1", notes = "查询套餐内流量使用情况6.2.1",
        response = QueryFlowInfoResp.class, tags = {"cmbs_video", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回", response = QueryFlowInfoResp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QueryFlowInfoResp.class)})
    public QueryFlowInfoResp queryFlowInfo(
        @RequestBody @Validated QueryFlowInfoReq flowInfoReq) throws Exception {
        QueryFlowInfoResp response = cmbsQueryYJService.queryFlowInfo(flowInfoReq);
        return response;
    }


    /**
     * 〈查询账户余额〉
     * 〈查询账户余额〉
     *
     * @param req QueryChargeBalanceReq
     * @throws Exception [Exception]
     * @return QueryChargeBalanceResp
     */
    @RequestMapping(value = "/video/queryChargeBalance", produces = {"application/json"},
        method = RequestMethod.POST)
    @ApiOperation(value = "余额查询6.1.4", notes = "余额查询6.1.4",
        response = QueryResp.class, tags = {"cmbs_video", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回", response = QueryChargeBalanceResp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QueryChargeBalanceResp.class)})
    public QueryChargeBalanceResp queryChargeBalance(@RequestBody @Validated QueryChargeBalanceReq req) throws Exception {
        QueryChargeBalanceResp response = cmbsQueryYJService.queryChargeBalance(req);
        return response;
    }

    /**
     * 家庭网成员信息查询
     *
     * @param req [QueryFamilyMemberInfoReq]
     * @return QueryFamilyMemberInfoResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/video/queryFamilyMemberInfo", produces = {"application/json"},
        method = RequestMethod.POST)
    @ApiOperation(value = "查询家庭网成员信息6.1.5", notes = "查询家庭网成员信息6.1.5", response = QueryResp.class, tags = {"cmbs_video"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回", response = QueryFamilyMemberInfoReq.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QueryFamilyMemberInfoResp.class)})
    public QueryFamilyMemberInfoResp queryFamilyMemberInfo(@RequestBody @Validated QueryFamilyMemberInfoReq req) throws Exception {
        QueryFamilyMemberInfoResp response = cmbsQueryYJService.queryFamilyMemberInfo(req);
        return response;
    }

}
