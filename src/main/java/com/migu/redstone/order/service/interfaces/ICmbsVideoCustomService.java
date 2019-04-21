package com.migu.redstone.order.service.interfaces;

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
import com.migu.redstone.order.service.dto.response.QueryUserStatusResp;

/**
 * @author MY Feel ML
 */

/**
 * ICmbsVideoCustomService
 * @author MY Feel ML
 */
public interface ICmbsVideoCustomService {

    /**
     * <查询江苏流量百分比>
     * @param req [QueryJSFlowInfoReq]
     * @return QueryJSFlowInfoResp
     * @throws Exception [Exception]
     */
    QueryJSFlowInfoResp queryJsFlowPercent(QueryJSFlowInfoReq req) throws Exception;

    /**
     * <弹窗营销位接口>
     * @param req [QueryActivityPositionReq]
     * @return QueryActivityPositionResp
     * @throws Exception [Exception]
     */
    QueryActivityPositionResp queryActivityPosition(QueryActivityPositionReq req) throws Exception;

    /**
     * <Banner营销位接口>
     * @param req [QueryBannerActivityReq]
     * @return QueryBannerActivityResp
     * @throws Exception [Exception]
     */
    QueryBannerActivityResp queryBannerActivity(QueryBannerActivityReq req) throws Exception;

    /**
     * <详情页营销位接口>
     * @param req [QueryActivityDetailReq]
     * @return QueryActivityDetailResp
     * @throws Exception [Exception]
     */
    QueryActivityDetailResp queryActivityDetail(QueryActivityDetailReq req) throws Exception;

    /**
     * <查询营销活动数据>
     * @param req [QueryActivityDataReq]
     * @return QueryActivityDataResp
     * @throws Exception [Exception]
     */
    QueryActivityDataResp queryActivityData(QueryActivityDataReq req) throws Exception;

    /**
     * <用户已办理的业务查询>
     * @param req [QueryOrderedBusinessReq]
     * @return QueryOrderedBusinessResp
     * @throws Exception [Exception]
     */
    QueryOrderedBusinessResp queryOrderedBusiness(QueryOrderedBusinessReq req) throws Exception;

    /**
     * <业务办理资格校验>
     * @param req [CheckBusinessQualificationReq]
     * @return CheckBusinessQualificationResp
     * @throws Exception [Exception]
     */
    CheckBusinessQualificationResp checkBusinessQualification(CheckBusinessQualificationReq req) throws Exception;

	/**
	 * 业务办理资格结果查询
	 */
	QueryBusinessQualificationResultResp getBusinessQualificationResult(QueryBusinessQualificationResultReq req) ;
     
    /**
     * <查询用户状态>
     * @param req [QueryUserStatusReq]
     * @return QueryUserStatusResp
     * @throws Exception [Exception]
     */
    QueryUserStatusResp queryUserStatus(QueryUserStatusReq req) throws Exception;

    /**
     * <套餐用量查询>
     *
     * @param req [QueryUserPackageInfoReq]
     * @return QueryUserPackageInfoResp
     * @throws Exception [Exception]
     */
    QueryMonthFlowUseResponse queryUserPackageInfo(QueryUserPackageInfoReq req) throws Exception;

    /**
     * <宽带身份校验>
     *
     * @param req [CheckBroadbandIdentityReq]
     * @return CheckBroadbandIdentityResp
     * @throws Exception [Exception]
     */
    CheckBroadbandIdentityResp checkBroadbandIdentity(CheckBroadbandIdentityReq req) throws Exception;

    /**
     * <宽带办理校验>
     *
     *  @param req [CheckOrderBroadbandReq]
     *  @return CheckOrderBroadbandResp
     */
    CheckOrderBroadbandResp checkOrderBroadband(CheckOrderBroadbandReq req) throws Exception;

    /**
     * <查询宽带地址>
     * @param req [QueryBroadbandAddressReq]
     * @return QueryBroadbandAddressResp
     * @throws Exception [Exception]
     */
    QueryBroadbandAddressResp queryBroadbandAddress(QueryBroadbandAddressReq req) throws Exception;

    /**
     *<queryBroadbandOrder>.
     *<查询宽带订单>
     * @param  request   [request]
     * @return [返回宽带订单]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
    QueryBroadbandOrderResp queryBroadbandOrder(QueryBroadbandOrderReq request) throws Exception;

    /**
     * <查询目标用户>
     * @param req [QueryTargetUserReq]
     * @return QueryTargetUserResp
     * @throws Exception [Exception]
     */
    QueryTargetUserResp queryTargetUser(QueryTargetUserReq req) throws Exception;

    /**
     *<checkMktPlanQualification>.
     *<营销案办理资格校验>
     * @param  req   [request]
     * @return [返回校验结果]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
    CheckMktPlanQualificationResp checkMktPlanQualification(CheckMktPlanQualificationReq req);

    /**
     * 查询用户订购关系
     */
    QuerySubscriptionResp querySubscription(QuerySubscriptionReq req);

    QueryGDFlowResp queryGDFlowInfo(QueryGDFlowReq req) throws Exception;

    /**
     * Offer推荐接口
     */
    GetRecommendOfferResp getRecommendOffer(GetRecommendOfferReq recommendOfferReq) throws Exception;
    
    /**
     * 江苏获取token
     */
    String queryAccessToken() throws Exception;
}
