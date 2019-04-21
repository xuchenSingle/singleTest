package com.migu.redstone.order.controller;

import com.migu.redstone.order.service.dto.response.QueryMonthFlowUseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.migu.redstone.order.service.dto.request.QueryYJPlatFormServiceReq;
import com.migu.redstone.order.service.dto.response.QueryChargeBalanceResp;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJPlatFormService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 类作用: 用户服务的使用情况 项目名称: cmbs-order-process 包: com.migu.redstone.order.controller 类名称: CmbsQueryAccountController 类描述:
 * 用户服务的使用情况controller 创建时间: 2018年11月27日 下午2:19:16
 */
@RestController
@RequestMapping(value = "/test/cmbs/query")
@Api(value = "用户服务的使用情况", description = "cmbs用户服务的使用情况")
public class CmbsQueryYJPLatFormController {

    /**
     * cmbsQueryYJPlatFormService
     */
    @Autowired
    private ICmbsQueryYJPlatFormService cmbsQueryYJPlatFormService;

    /**
     * <queryMonthFlowUse>. <用户按月查询流量套餐使用情况>
     * 
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @return [返回使用情况]
     * @throws Exception
     *             [Exception]
     * @author wangfl
     */
    @RequestMapping(value = "/package/userinfo", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "OSPQ017按月查询流量套餐使用情况", notes = "按月查询流量套餐使用情况", response = QueryMonthFlowUseResponse.class, tags = {
        "Query_YJPlatForm_Service", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "响应结果", response = QueryMonthFlowUseResponse.class) })
    public QueryMonthFlowUseResponse queryMonthFlowUse(
        @RequestBody @Validated QueryYJPlatFormServiceReq queryAccountInfoReq) throws Exception {
        QueryMonthFlowUseResponse response = cmbsQueryYJPlatFormService.queryPackageUseInfo(queryAccountInfoReq);
        return response;
    }

    /**
     * <queryAcountBalance>. <账户余额脱敏查询接口>
     * 
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @return [账户余额脱敏查询接口]
     * @throws Exception
     *             [Exception]
     */
    @RequestMapping(value = "/account/newbalance", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "OSPQ035账户余额查询", notes = "查询账户余额", response = QueryChargeBalanceResp.class, tags = {
            "Query_YJPlatForm_Service", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "响应结果", response = QueryChargeBalanceResp.class) })
    public QueryChargeBalanceResp queryAcountBalance(
            @RequestBody @Validated QueryYJPlatFormServiceReq queryAccountInfoReq) throws Exception {
        QueryChargeBalanceResp response = cmbsQueryYJPlatFormService.queryAcountBalance(queryAccountInfoReq);
        return response;
    }

    /**
     * <queryPackageUseInfo>. <流量使用情况脱敏查询接口>
     * 
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @return [流量使用情况脱敏查询接口]
     * @throws Exception
     *             [Exception]
     */
    @RequestMapping(value = "/newpackage/userinfo", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "OSPQ034查询用户的套餐使用情况", notes = "OSPQ034查询用户的套餐使用情况", response = QueryFlowInfoResp.class, tags = {
            "Query_YJPlatForm_Service", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "响应结果", response = QueryFlowInfoResp.class) })
    public QueryFlowInfoResp queryPackageUseInfo(@RequestBody @Validated QueryYJPlatFormServiceReq queryAccountInfoReq)
            throws Exception {
        QueryFlowInfoResp response = cmbsQueryYJPlatFormService.queryFlowUseInfo(queryAccountInfoReq, null, "06");
        return response;
    }

    // /**
    // *<queryUserStatus>.
    // *<提供查询用户的状态信息>
    // * @param queryAccountInfoReq [QueryAccountInfoReq]
    // * @return [返回用户的套餐使用信息]
    // * @exception/throws [Exception] [Exception]
    // */
    // @RequestMapping(value = "/queryUserStatus", produces = { "application/json" }, method = RequestMethod.POST)
    // @ApiOperation(value = "OSPQ036查询用户的状态信息", notes = "查询用户的状态信息", response = QueryUserStatusResp.class, tags = {
    // "Query_YJPlatForm_Service内测试，不暴露", })
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "响应结果s", response = QueryUserStatusResp.class) })
    // public QueryUserStatusResp queryUserStatus(@RequestBody @Validated QueryUserStatusReq req) throws Exception {
    // QueryYJPlatFormServiceReq platFomServiceReq = new QueryYJPlatFormServiceReq();
    //
    // platFomServiceReq.setServiceNumber(req.getPhoneNumber());
    // QueryUserStatusResp response = cmbsQueryYJPlatFormService.queryUserStatusService(platFomServiceReq);
    // return response;
    // }

    // /**
    // *<queryUserStatus>.
    // *<提供查询用户的状态信息>
    // * @param queryAccountInfoReq [QueryAccountInfoReq]
    // * @return [返回用户的套餐使用信息]
    // * @exception/throws [Exception] [Exception]
    // */
    // @RequestMapping(value = "/queryUserCallback", produces = { "application/json" }, method = RequestMethod.POST)
    // @ApiOperation(value = "查询用户的状态信息", notes = "查询用户的状态信息", tags = {
    // "Query_YJPlatForm_Service", })
    // @ApiResponses(value = { @ApiResponse(code = 200, message = "响应结果s") })
    // public String queryUserCallback(@RequestBody @Validated OrderFeedbackReq req) throws Exception {
    // QueryYJPlatFormServiceReq platFomServiceReq = new QueryYJPlatFormServiceReq();
    //
    // String response = cmbsQueryYJPlatFormService.queryUserCallback(req);
    // return response;
    // }
}
