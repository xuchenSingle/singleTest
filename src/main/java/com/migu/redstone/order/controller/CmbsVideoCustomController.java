package com.migu.redstone.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.migu.redstone.client.dto.response.QueryOfferInfoRsp;
import com.migu.redstone.common.dto.CheckBusinessQualificationResp;
import com.migu.redstone.order.service.dto.request.CheckBroadbandIdentityReq;
import com.migu.redstone.order.service.dto.request.CheckBusinessQualificationReq;
import com.migu.redstone.order.service.dto.request.CheckMktPlanQualificationReq;
import com.migu.redstone.order.service.dto.request.CheckOrderBroadbandReq;
import com.migu.redstone.order.service.dto.request.GetRecommendOfferReq;
import com.migu.redstone.order.service.dto.request.QueryActivityDataReq;
import com.migu.redstone.order.service.dto.request.QueryActivityDetailReq;
import com.migu.redstone.order.service.dto.request.QueryActivityPositionReq;
import com.migu.redstone.order.service.dto.request.QueryBannerActivityReq;
import com.migu.redstone.order.service.dto.request.QueryBroadbandAddressReq;
import com.migu.redstone.order.service.dto.request.QueryBroadbandOrderReq;
import com.migu.redstone.order.service.dto.request.QueryBusinessQualificationResultReq;
import com.migu.redstone.order.service.dto.request.QueryGDFlowReq;
import com.migu.redstone.order.service.dto.request.QueryJSFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryOrderedBusinessReq;
import com.migu.redstone.order.service.dto.request.QuerySubscriptionReq;
import com.migu.redstone.order.service.dto.request.QueryTargetUserReq;
import com.migu.redstone.order.service.dto.request.QueryUserPackageInfoReq;
import com.migu.redstone.order.service.dto.request.QueryUserStatusReq;
import com.migu.redstone.order.service.dto.response.CheckBroadbandIdentityResp;
import com.migu.redstone.order.service.dto.response.CheckMktPlanQualificationResp;
import com.migu.redstone.order.service.dto.response.CheckOrderBroadbandResp;
import com.migu.redstone.order.service.dto.response.GetRecommendOfferResp;
import com.migu.redstone.order.service.dto.response.QueryActivityDataResp;
import com.migu.redstone.order.service.dto.response.QueryActivityDetailResp;
import com.migu.redstone.order.service.dto.response.QueryActivityPositionResp;
import com.migu.redstone.order.service.dto.response.QueryBannerActivityResp;
import com.migu.redstone.order.service.dto.response.QueryBroadbandAddressResp;
import com.migu.redstone.order.service.dto.response.QueryBroadbandOrderResp;
import com.migu.redstone.order.service.dto.response.QueryBusinessQualificationResultResp;
import com.migu.redstone.order.service.dto.response.QueryGDFlowResp;
import com.migu.redstone.order.service.dto.response.QueryJSFlowInfoResp;
import com.migu.redstone.order.service.dto.response.QueryMonthFlowUseResponse;
import com.migu.redstone.order.service.dto.response.QueryOrderedBusinessResp;
import com.migu.redstone.order.service.dto.response.QuerySubscriptionResp;
import com.migu.redstone.order.service.dto.response.QueryTargetUserResp;
import com.migu.redstone.order.service.dto.response.QueryUserPackageInfoResp;
import com.migu.redstone.order.service.dto.response.QueryUserStatusResp;
import com.migu.redstone.order.service.interfaces.ICmbsVideoCustomService;
import com.migu.redstone.utils.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * CmbsVideoCustomController
 */
@RestController
@Api(value = "咪咕视讯定制查询服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsVideoCustomController {

    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsVideoCustomController.class);
    /**
     * videoCustomService
     */
    @Autowired
    private ICmbsVideoCustomService cmbsVideoCustomService;

    /**
     * queryJsFlowPercent
     * @param request [QueryJSFlowInfoReq]
     * @return QueryJSFlowInfoResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryJsFlowPercent", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询江苏流量百分比6.2.2", notes = "查询江苏流量百分比6.2.2", response = QueryJSFlowInfoResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryJSFlowInfoResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryJSFlowInfoResp.class)})
    public QueryJSFlowInfoResp queryJsFlowPercent(@RequestBody @Validated QueryJSFlowInfoReq request) throws Exception {
        LOG.error("CmbsVideoCustomController.queryActivityPosition ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryJsFlowPercent(request);
    }

    /**
     * queryActivityPosition
     * @param request [QueryActivityPositionReq]
     * @return QueryActivityPositionResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryActivityPosition", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "弹窗营销位6.2.6", notes = "弹窗营销位6.2.6", response = QueryActivityPositionResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryActivityPositionResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryActivityPositionResp.class)})
    public QueryActivityPositionResp queryActivityPosition(@RequestBody @Validated QueryActivityPositionReq request)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryActivityPosition ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryActivityPosition(request);
    }

    /**
     * queryActivityPosition
     * @param request [QueryBannerActivityReq]
     * @return QueryBannerActivityResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryBannerActivity", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "Banner营销位6.2.7", notes = "Banner营销位6.2.7", response = QueryBannerActivityResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryBannerActivityResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryBannerActivityResp.class)})
    public QueryBannerActivityResp queryBannerActivity(@RequestBody @Validated QueryBannerActivityReq request)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryBannerActivity ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryBannerActivity(request);
    }

    /**
     * queryActivityDetail
     * @param request [QueryActivityDetailReq]
     * @return QueryActivityDetailResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryActivityDetail", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "详情页营销位6.2.8", notes = "详情页营销位6.2.8", response = QueryActivityDetailResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryActivityDetailResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryActivityDetailResp.class)})
    public QueryActivityDetailResp queryActivityDetail(@RequestBody @Validated QueryActivityDetailReq request)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryActivityDetail ,request= }" + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryActivityDetail(request);
    }

    /**
     * queryActivityData
     * @param request [QueryActivityDataReq]
     * @return QueryActivityDataResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryActivityData", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询营销活动数据6.2.9", notes = "查询营销活动数据6.2.9", response = QueryActivityDataResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryActivityDataResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryActivityDataResp.class)})
    public QueryActivityDataResp queryActivityData(@RequestBody @Validated QueryActivityDataReq request)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryActivityData ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryActivityData(request);
    }

    /**
     * queryOrderedBusiness
     * @param request [QueryOrderedBusinessReq]
     * @return QueryOrderedBusinessResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryOrderedBusiness", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "用户已办理的业务查询6.2.4", notes = "用户已办理的业务查询6.2.4", response = QueryOrderedBusinessResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryOrderedBusinessResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryOrderedBusinessResp.class)})
    public QueryOrderedBusinessResp queryOrderedBusiness(@RequestBody @Validated QueryOrderedBusinessReq request)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryOrderedBusiness ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryOrderedBusiness(request);
    }

    /**
     * checkBusinessQualification
     * @param request [CheckBusinessQualificationReq]
     * @return CheckBusinessQualificationResp
     * @throws Exception [Exception]
     */
	@RequestMapping(value = "/queryBusinessQualificationResult", produces = {
			"application/json" }, method = RequestMethod.POST)
	@ApiOperation(value = "业务办理资格结果查询", notes = "业务办理资格结果查询", response = QueryBusinessQualificationResultResp.class, tags = {
			"cmbs_video", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "返回", response = QueryBusinessQualificationResultResp.class),
			@ApiResponse(code = 200, message = "Unexpected error", response = QueryBusinessQualificationResultResp.class) })
	public QueryBusinessQualificationResultResp queryBusinessQualificationResult(
			@RequestBody @Validated QueryBusinessQualificationResultReq request) throws Exception {
		LOG.error("CmbsVideoCustomController.checkBusinessQualification ,request= " + JsonUtil.objectToString(request));
		return cmbsVideoCustomService.getBusinessQualificationResult(request);
	}

    @RequestMapping(value = "/checkBusinessQualification", produces = {
    "application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "业务资格校验查询6.2.5", notes = "业务资格校验查询6.2.5", response = CheckBusinessQualificationResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = CheckBusinessQualificationResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = CheckBusinessQualificationResp.class)})
    public CheckBusinessQualificationResp checkBusinessQualification(
            @RequestBody @Validated CheckBusinessQualificationReq request) throws Exception {
        LOG.error("CmbsVideoCustomController.checkBusinessQualification ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.checkBusinessQualification(request);
    }

    /**
     * queryUserStatus
     * @param request [QueryUserStatusReq]
     * @return QueryUserStatusResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryUserStatus", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询用户状态6.2.3", notes = "查询用户状态6.2.3", response = QueryUserStatusResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryUserStatusResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryUserStatusResp.class)})
    public QueryUserStatusResp queryUserStatus(@RequestBody @Validated QueryUserStatusReq request) throws Exception {
        LOG.error("CmbsVideoCustomController.queryUserStatus ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryUserStatus(request);
    }

    /**
     * queryUserPackageInfo
     * @param request [QueryUserPackageInfoReq]
     * @return QueryUserPackageInfoResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryUserPackageInfo", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "套餐用量查询6.2.10", notes = "套餐用量查询6.2.10", response = QueryUserPackageInfoResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryMonthFlowUseResponse.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryMonthFlowUseResponse.class)})
    public QueryMonthFlowUseResponse queryUserPackageInfo(@RequestBody @Validated QueryUserPackageInfoReq request)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryUserPackageInfo ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.queryUserPackageInfo(request);
    }

    /**
     * checkBroadbandIdentity
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkBroadbandIdentity", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "宽带身份校验", notes = "宽带身份校验", response = CheckBroadbandIdentityResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = CheckBroadbandIdentityResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = CheckBroadbandIdentityResp.class)})
    public CheckBroadbandIdentityResp checkBroadbandIdentity(@RequestBody @Validated CheckBroadbandIdentityReq req)
            throws Exception {
        LOG.error("CmbsVideoCustomController.checkBroadbandIdentity ,request= " + JsonUtil.objectToString(req));
        return cmbsVideoCustomService.checkBroadbandIdentity(req);
    }

    /**
     * 7.2.宽带办理校验接口
     *
     * @param req
     *            [CheckOrderBroadbandReq]
     * @throws Exception
     *             [Exception]
     * @return resp
     */
    @RequestMapping(value = "/checkBroadbandSubscriptionQualification", produces = { "application/json" },
            method = RequestMethod.POST)
    @ApiOperation(value = "宽带办理校验接口", notes = "宽带办理校验接口", response = CheckOrderBroadbandResp.class, tags = { "cmbs_video", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = CheckOrderBroadbandResp.class) })
    public CheckOrderBroadbandResp queryCheckOrderBroadband(@RequestBody @Validated CheckOrderBroadbandReq req) throws Exception{
        LOG.error("CmbsVideoCustomController.queryCheckOrderBroadband ,request= " + JsonUtil.objectToString(req));
        return cmbsVideoCustomService.checkOrderBroadband(req);
    }

    /**
     * <查询宽带地址>
     * @param req [QueryBroadbandAddressReq]
     * @return QueryBroadbandAddressResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryBroadbandAddress", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询宽带地址", notes = "查询宽带地址", response = QueryBroadbandAddressResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryBroadbandAddressResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryBroadbandAddressResp.class)})
    public QueryBroadbandAddressResp queryBroadbandAddress(@RequestBody @Validated QueryBroadbandAddressReq req)
            throws Exception {
        LOG.error("CmbsVideoCustomController.queryBroadbandAddress, request= " + JsonUtil.objectToString(req));
        return cmbsVideoCustomService.queryBroadbandAddress(req);
    }

    /**
     *<queryBroadbandOrder>.
     *<查询宽带订单>
     * @param  request   [request]
     * @return [返回宽带订单]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
    @RequestMapping(value = "/queryBroadbandOrder", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询宽带订单", notes = "查询宽带订单", response = QueryBroadbandOrderResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryBroadbandOrderResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryBroadbandOrderResp.class)})
    public QueryBroadbandOrderResp queryBroadbandOrder(
            @RequestBody @Validated QueryBroadbandOrderReq request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.error("CmbsVideoCustomController.queryBroadbandOrder startTime=" + startTime
                + " ,request=" + JsonUtil.objectToString(request));

        //查询宽带订单
        QueryBroadbandOrderResp response = cmbsVideoCustomService.queryBroadbandOrder(request);

        long endTime = System.currentTimeMillis();
        LOG.error("CmbsVideoCustomController.queryBroadbandOrder startTime=" + startTime
                + " ,endTime=" + endTime + " ,response=" + JsonUtil.objectToString(response));
        return response;
    }

    /**
     * <查询目标用户>
     * @param req [QueryTargetUserReq]
     * @return QueryTargetUserResp
     * @throws Exception
     */
    @RequestMapping(value = "/queryTargetUser", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "查询目标用户", notes = "查询目标用户", response = QueryTargetUserResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryTargetUserResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryTargetUserResp.class)})
    public QueryTargetUserResp queryTargetUser(@RequestBody @Validated QueryTargetUserReq req) throws Exception {
        LOG.error("CmbsVideoCustomController.queryTargetUser, request= " + JsonUtil.objectToString(req));
        return cmbsVideoCustomService.queryTargetUser(req);
    }

    /**
    *<checkMktPlanQualification>.
    *<营销案办理资格校验>
    * @param  request   [request]
    * @return [返回校验结果]
    * @exception/throws [Exception] [Exception]
    * @author jianghao
    */
    @RequestMapping(value = "/checkMktPlanQualification", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "营销案办理资格校验", notes = "营销案办理资格校验", response = CheckMktPlanQualificationResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = CheckMktPlanQualificationResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = CheckMktPlanQualificationResp.class)})
    public CheckMktPlanQualificationResp checkMktPlanQualification (
            @RequestBody @Validated CheckMktPlanQualificationReq request) throws Exception {
        LOG.error("CmbsVideoCustomController.checkMktPlanQualification ,request= " + JsonUtil.objectToString(request));
        return cmbsVideoCustomService.checkMktPlanQualification(request);
    }
    
    /**
     * 查询用户订购关系
     */
     @RequestMapping(value = "/querySubscription", produces = {"application/json"}, method = RequestMethod.POST)
     @ApiOperation(value = "查询用户订购关系6.2.18", notes = "查询用户订购关系6.2.18", response = QuerySubscriptionResp.class, tags = {
             "cmbs_video", })
     @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QuerySubscriptionResp.class),
             @ApiResponse(code = 200, message = "Unexpected error", response = QuerySubscriptionResp.class)})
     public QuerySubscriptionResp querySubscription (
             @RequestBody @Validated QuerySubscriptionReq request) throws Exception {
    	 LOG.error("CmbsVideoCustomController.QuerySubscriptionReq ,request= " + JsonUtil.objectToString(request));
         return cmbsVideoCustomService.querySubscription(request);
     }
     
     @RequestMapping(value = "/queryGDFlowInfo", produces = {"application/json"}, method = RequestMethod.POST)
     @ApiOperation(value = "广东流量查询", notes = "广东流量查询", response = QueryGDFlowResp.class, tags = {
             "cmbs_video", })
     @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryGDFlowResp.class),
             @ApiResponse(code = 200, message = "Unexpected error", response = QueryGDFlowResp.class)})
     public QueryGDFlowResp queryGDFlowInfo(@RequestBody @Validated QueryGDFlowReq request) throws Exception {
    	 LOG.error("CmbsVideoCustomController.queryGDFlowInfo ,request= " + JsonUtil.objectToString(request));
    	 return cmbsVideoCustomService.queryGDFlowInfo(request);
     }

    /**
     * Offer推荐接口
     * @param getRecommendOfferReq
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRecommendOffer", method = RequestMethod.POST, produces = { "application/json" })
    @ApiOperation(value = "Offer推荐接口", notes = "Offer推荐接口", tags = {"cmbs_video", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = GetRecommendOfferResp.class) })
    public GetRecommendOfferResp getRecommendOffer(@RequestBody @Validated GetRecommendOfferReq getRecommendOfferReq) throws Exception {
        LOG.error("CmbsVideoCustomController.getRecommendOffer ,request= " + JsonUtil.objectToString(getRecommendOfferReq));
        return cmbsVideoCustomService.getRecommendOffer(getRecommendOfferReq);
    }


    /**
     * 获取token
     * @param querySdAccessTokenReq
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSdAccessToken", method = RequestMethod.POST, produces = { "application/json" })
    @ApiOperation(value = "山东获取token接口", notes = "山东获取token接口", tags = {"cmbs_video", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = String.class) })
    public String getSdAccessToken() throws Exception {
        return cmbsVideoCustomService.queryAccessToken();
    }

}
