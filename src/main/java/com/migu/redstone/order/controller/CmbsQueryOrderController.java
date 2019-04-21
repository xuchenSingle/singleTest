/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.migu.redstone.order.service.dto.request.QuerySubOrderReq;
import com.migu.redstone.order.service.dto.response.QuerySubOrderRsp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryOrderService;
import com.migu.redstone.utils.JsonUtil;


/**
* 类作用:    查询订单信息
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.controller
* 类名称:    CmbsQueryOrderController
* 类描述:    cmbs查询订单controller
* 创建人:    jianghao
* 创建时间:   2018年11月5日 上午11:15:08
*/
@RestController
@Api(value = "查询订单服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsQueryOrderController {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsQueryOrderController.class);

    /**
     * cmbsQueryOrderService
     */
    @Autowired
    private ICmbsQueryOrderService cmbsQueryOrderService;

    /**
    *<querySubOrder>.
    *<查询单个子订单信息>
    * @param  queryOrderReq   [queryOrderReq]
    * @return [返回订单信息]
    * @throws Exception [Exception]
    * @author jianghao
    */
    @RequestMapping(value = "/querySubOrder", produces = { "application/json" },
        method = RequestMethod.POST)
    @ApiOperation(value = "查询单个子订单信息", notes = "查询单个子订单信息",
        response = QuerySubOrderRsp.class, tags = {"Cmbs_Query_Order", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回", response = QuerySubOrderRsp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QuerySubOrderRsp.class) })
    public QuerySubOrderRsp querySubOrder(@RequestBody @Validated QuerySubOrderReq queryOrderReq) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.error("CmbsMiguVideoController.querySubOrder startTime=" + startTime
            + " ,queryOrderReq=" + JsonUtil.objectToString(queryOrderReq));

        QuerySubOrderRsp response = cmbsQueryOrderService.querySubOrder(queryOrderReq);

        long endTime = System.currentTimeMillis();
        LOG.error("CmbsMiguVideoController.querySubOrder startTime=" + startTime
            + " ,endTime=" + endTime + " ,response=" + JsonUtil.objectToString(response));
        return response;
    }
}
