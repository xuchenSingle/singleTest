package com.migu.redstone.order.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.product.common.enums.CmbsChannelType;
import com.migu.redstone.cfg.product.mapper.po.CmbsProdChannelAbilityCfg;
import com.migu.redstone.cfg.product.service.interfaces.ICmbsProdService;
import com.migu.redstone.client.dto.model.RecommendOffer;
import com.migu.redstone.client.dto.model.RecommendOfferResult;
import com.migu.redstone.client.dto.model.ResultData;
import com.migu.redstone.client.dto.request.BroadbandAddressQueryReq;
import com.migu.redstone.client.dto.request.CheckBroadbandStatusReq;
import com.migu.redstone.client.dto.request.JsCheckOrderBroadbandReq;
import com.migu.redstone.client.dto.request.JsMarketingPositionReq;
import com.migu.redstone.client.dto.request.QueryOfferInfoReq;
import com.migu.redstone.client.dto.request.TargetUserQueryReq;
import com.migu.redstone.client.dto.response.BroadbandAddressQueryResp;
import com.migu.redstone.client.dto.response.CheckBroadbandStatusResp;
import com.migu.redstone.client.dto.response.EncsMsisdnSectionRes;
import com.migu.redstone.client.dto.response.JsBaseRsp;
import com.migu.redstone.client.dto.response.JsBusinessOpeningRes;
import com.migu.redstone.client.dto.response.JsCampaignActivityRes;
import com.migu.redstone.client.dto.response.JsCheckOrderBroadbandRsp;
import com.migu.redstone.client.dto.response.JsMarketingPositionRes;
import com.migu.redstone.client.dto.response.JsPlayerMarketingPositionFlowerRes;
import com.migu.redstone.client.dto.response.JsQueryBroadbandOrderRsp;
import com.migu.redstone.client.dto.response.QueryOfferInfoRsp;
import com.migu.redstone.client.dto.response.QueryOrderBusinessRsp;
import com.migu.redstone.client.dto.response.TargetUserQueryResp;
import com.migu.redstone.client.enums.JsFlowperCode;
import com.migu.redstone.client.fallback.BaseServiceFallback;
import com.migu.redstone.client.service.interfaces.IJsOutClientService;
import com.migu.redstone.client.service.interfaces.IRestService;
import com.migu.redstone.client.service.interfaces.ISdOutClientService;
import com.migu.redstone.common.dto.CheckBusinessQualificationResp;
import com.migu.redstone.common.dto.QualificationCheckReq;
import com.migu.redstone.common.interfaces.IQualificationService;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.MktCampaignConst.CreateOrderConst;
import com.migu.redstone.constants.RedisKey;
import com.migu.redstone.entity.BusinessQualification;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.emums.BusinessQualificationSyncCode;
import com.migu.redstone.order.service.dto.model.Offer;
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
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.request.QueryJSFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryOrderBusinessReq;
import com.migu.redstone.order.service.dto.request.QueryOrderedBusinessReq;
import com.migu.redstone.order.service.dto.request.QuerySubscriptionReq;
import com.migu.redstone.order.service.dto.request.QueryTargetUserReq;
import com.migu.redstone.order.service.dto.request.QueryUserPackageInfoReq;
import com.migu.redstone.order.service.dto.request.QueryUserStatusReq;
import com.migu.redstone.order.service.dto.request.QueryYJPlatFormServiceReq;
import com.migu.redstone.order.service.dto.response.ActivityData;
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
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryOrderBusinessService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJPlatFormService;
import com.migu.redstone.order.service.interfaces.ICmbsVideoCustomService;
import com.migu.redstone.redis.RedisTemplate;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.DateCommonUtil;
import com.migu.redstone.utils.JsonUtil;
import com.migu.redstone.utils.SpringContextUtil;

/**
 * CmbsVideoCustomService
 */
@Service
public class CmbsVideoCustomService implements ICmbsVideoCustomService {
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CmbsVideoCustomService.class);
	/**
	 * jsClient
	 */
	@Autowired
	private IJsOutClientService jsOutClientService;

	/**
	 * sdClient
	 */
	@Autowired
	private ISdOutClientService sdOutClientService;

	/**
	 * cmbsQueryOrderBusinessService
	 */
	@Autowired
	private ICmbsQueryOrderBusinessService cmbsQueryOrderBusinessService;

	/**
	 * cmbsQueryYJPLatFormService
	 */
	@Autowired
	private ICmbsQueryYJPlatFormService cmbsQueryYJPLatFormService;

	/**
	 * cmbsProdService
	 */
	@Autowired
	private ICmbsProdService cmbsProdService;

	/**
	 * encsClient
	 */
	@Autowired
	private IRestService restService;

	/**
	 * cmbsGDQueryService
	 */
	@Autowired
	private ICmbsGDQueryService cmbsGDQueryService;

	/**
	 * redisTemplate
	 */
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * kafkaTemplate
	 */
	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;

	/**
	 * <查询江苏流量百分比>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryJSFlowInfoResp queryJsFlowPercent(QueryJSFlowInfoReq req) throws Exception {
		QueryJSFlowInfoResp resp = new QueryJSFlowInfoResp();
		JsPlayerMarketingPositionFlowerRes jsRes = jsOutClientService.getMiguFlower(req.getPhoneNumber());
		if (CommonUtil.isNotNull(jsRes) && StringUtils.equals(JsFlowperCode.SUCCESS.getStatus(), jsRes.getRetCode())) {
			resp.setMsg(jsRes.getMsg());
			resp.setPhoneNumber(jsRes.getRetObj().getPhone());
			resp.setTime(DateCommonUtil.getFormatDateStr(new Date(jsRes.getRetObj().getTime()),
					CommonConst.DATEFORMAT.DATETIME_FORMAT));
			resp.setBuscode(jsRes.getRetObj().getBuscode());
			Integer type = jsRes.getRetObj().getType();
			if (type != null) {
				resp.setType(type);
			}
			Long subtotal = jsRes.getRetObj().getSubtotal();
			if (subtotal != null) {
				// 流量kb转M
				BigDecimal bd = new BigDecimal(subtotal);
				BigDecimal m = bd.divide(new BigDecimal(1024), 2, RoundingMode.FLOOR);
				resp.setSubtotal(m.toString());
			}
			resp.setPercentage(jsRes.getRetObj().getPercentage());
			resp.setMessage(jsRes.getRetObj().getMessage());
			resp.setActurl(jsRes.getRetObj().getActurl());
			resp.setActtitle(jsRes.getRetObj().getActtitle());
			resp.setActdesc(jsRes.getRetObj().getActdesc());
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * <弹窗营销位接口>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryActivityPositionResp queryActivityPosition(QueryActivityPositionReq req) throws Exception {
		QueryActivityPositionResp resp = new QueryActivityPositionResp();
		JsMarketingPositionReq jsMarketingPositionReq = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
				req.getIsMember(), req.getIsV15(), req.getLocationid());
		JsMarketingPositionRes jsMarketingPositionRes = jsOutClientService
				.getPopupMarketingPosition(jsMarketingPositionReq);
		if (CommonUtil.isNotNull(jsMarketingPositionRes)) {
			resp.setCode(jsMarketingPositionRes.getCode());
			resp.setMsg(jsMarketingPositionRes.getMsg());
			resp.setUrl(jsMarketingPositionRes.getUrl());
			resp.setRetCode(jsMarketingPositionRes.getRetCode());
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}

		return resp;
	}

	/**
	 * <Banner营销位接口>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryBannerActivityResp queryBannerActivity(QueryBannerActivityReq req) throws Exception {
		QueryBannerActivityResp resp = new QueryBannerActivityResp();
		JsMarketingPositionReq request = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
				req.getIsMember(), req.getIsV15(), req.getLocationid());
		JsMarketingPositionRes jsMarketingPositionRes = jsOutClientService.getBannerMarketingPosition(request);
		if (CommonUtil.isNotNull(jsMarketingPositionRes)) {
			resp.setCode(jsMarketingPositionRes.getCode());
			resp.setMsg(jsMarketingPositionRes.getMsg());
			resp.setRetCode(jsMarketingPositionRes.getRetCode());
			resp.setUrl(jsMarketingPositionRes.getUrl());
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * <详情页营销位接口>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryActivityDetailResp queryActivityDetail(QueryActivityDetailReq req) throws Exception {
		QueryActivityDetailResp resp = new QueryActivityDetailResp();
		JsMarketingPositionReq request = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
				req.getIsMember(), req.getIsV15(), req.getLocationid());
		JsMarketingPositionRes jsMarketingPositionRes = jsOutClientService.getDetailMarketingPosition(request);
		if (CommonUtil.isNotNull(jsMarketingPositionRes)) {
			resp.setCode(jsMarketingPositionRes.getCode());
			resp.setMsg(jsMarketingPositionRes.getMsg());
			resp.setRetCode(jsMarketingPositionRes.getRetCode());
			resp.setUrl(jsMarketingPositionRes.getUrl());
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * <查询营销活动数据>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryActivityDataResp queryActivityData(QueryActivityDataReq req) throws Exception {
		QueryActivityDataResp resp = new QueryActivityDataResp();
		JsCampaignActivityRes jsCampaignActivityRes = jsOutClientService.getCampaignActivity(req.getPhoneNumber(),
				req.getActivityId());
		if (CommonUtil.isNotNull(jsCampaignActivityRes)
				&& MktCampaignConst.VideoToYj.SUCCESS_TRUE.equals(jsCampaignActivityRes.getSuccess())) {
			List<ActivityData> activityDataList = new ArrayList<>();
			ActivityData activityData = new ActivityData();
			if (CommonUtil.isNotNull(jsCampaignActivityRes.getData())) {
				activityData.setActivityId(jsCampaignActivityRes.getData().getActId());
				activityData.setActivityType(jsCampaignActivityRes.getData().getActtype());
				activityData.setArea(jsCampaignActivityRes.getData().getArea());
				activityData.setChannelId(jsCampaignActivityRes.getData().getChannelId());
				activityData.setIp(jsCampaignActivityRes.getData().getIp());
				activityData.setOrderTime(jsCampaignActivityRes.getData().getOrdertime());
				activityData.setOrderType(jsCampaignActivityRes.getData().getOrdertype());
				activityData.setPhoneNumber(jsCampaignActivityRes.getData().getPhone());
				activityDataList.add(activityData);
			}
			resp.setActivityData(activityDataList);
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * <用户已办理的业务查询>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryOrderedBusinessResp queryOrderedBusiness(QueryOrderedBusinessReq req) throws Exception {
		QueryOrderedBusinessResp resp = new QueryOrderedBusinessResp();
		QueryOrderBusinessReq queryOrderBusinessReq = new QueryOrderBusinessReq("01", req.getPhoneNumber(),
				req.getBusiType());
		QueryOrderBusinessRsp queryOrderBusinessRsp = cmbsQueryOrderBusinessService
				.queryOrderBusiness(queryOrderBusinessReq);
		if (CommonUtil.isNotNull(queryOrderBusinessRsp)) {
			if (MktCampaignConst.OrderStatus.ABILITY_CALLBACK_TERUE.equals(queryOrderBusinessRsp.getBizCode())) {
				resp.setBizInfoList(queryOrderBusinessRsp.getBizInfoList());
			} else {
				resp.setResult(
						new Result(IResultCode.CMBS_CURRENT_PROVINCE_NOT_SUPPRTT, queryOrderBusinessRsp.getBizDesc()));
			}
		} else {
			// throw new BusinessException(IResultCode.CMBS_SYS_ERROR,
			// "远程调用失败");
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * <业务办理资格校验>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public CheckBusinessQualificationResp checkBusinessQualification(CheckBusinessQualificationReq req)
			throws Exception {

		CheckBusinessQualificationResp resp = new CheckBusinessQualificationResp();

		// 根据channelCode，producId，oper_type 查询资格校验的处理类
		try {
			String phoneNumber = req.getPhoneNumber();
			// 查询号码归属地
			String provinceCode = "";
			EncsMsisdnSectionRes encsMsisdnSectionRes = new EncsMsisdnSectionRes();
			try {
				encsMsisdnSectionRes.setProvinceCode("351");
				// encsMsisdnSectionRes = restService.getMsisdnSection(phoneNumber);
				if (CommonUtil.isNotNull(resp) && StringUtils.isNotBlank(encsMsisdnSectionRes.getProvinceCode())) {
					provinceCode = encsMsisdnSectionRes.getProvinceCode();
				}
			} catch (Exception e) {
				LOGGER.error("CmbQueryYJService.queryMsisdnSection, resp=" + JsonUtil.objectToString(resp));
			}

			// 省份编码
			LOGGER.error("CmbQueryYJService.queryMsisdnSection, provinceCode=" + provinceCode);

			// 获取产品Id配置
			// 获取产品分渠道能力配置-获取processName
			String processName = "";
			String productCode = "";
			if (StringUtils.isNotBlank(req.getGoodsId())) {
				String channelCode = CmbsChannelType.PROVINCE.getValue() + provinceCode;
				CmbsProdChannelAbilityCfg cmbsProdChannelAbilityCfg = cmbsProdService
						.getCmbsProdChannelAbilityCfgByProductIdAndChannelAndType(Long.parseLong(req.getGoodsId()),
								channelCode, CreateOrderConst.OPER_TYPE_PAYMENT);
				if (cmbsProdChannelAbilityCfg != null) {
					processName = cmbsProdChannelAbilityCfg.getProcessName();
					productCode = cmbsProdChannelAbilityCfg.getProductCode();
				}
			}
			QualificationCheckReq qualificationCheckReq = new QualificationCheckReq();
			// 处理业务
			if (StringUtils.isNotBlank(processName)) {
				qualificationCheckReq.setGoodsId(productCode);
				qualificationCheckReq.setNumber(phoneNumber);
				qualificationCheckReq.setNumType(String.valueOf(MktCampaignConst.Number.ONE));
				qualificationCheckReq.setTransactionId(req.getRequestHeader().getTransactionId());
				if (StringUtils.isNotBlank(req.getServiceIdList())) {
					qualificationCheckReq.setServiceIdList(req.getServiceIdList());
				}
				qualificationCheckReq.setProcessName(processName);
				// 判断同步异步操作，只有值为0的时候为异步操作
				if (StringUtils.equals(BusinessQualificationSyncCode.ASYNC.getServiceType(), req.getIsSync())) {
					// 处理类信息和message信息，前置的代码逻辑可以共用
					// TODO
					kafkaTemplate.send(MktCampaignConst.KafkaTopic.CMBS_CHECK_BUSINESS_QUALIFICATION,
							JsonUtil.objectToString(qualificationCheckReq));
				} else {
					IQualificationService qualificationService = (IQualificationService) SpringContextUtil
							.getBean(processName);
					resp = qualificationService.qualificationCheck(qualificationCheckReq);
				}

			} else {
				resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, "业务办理失败，上游未提供该能力"));
			}

		} catch (Exception e) {
			resp.setResult(new Result(IResultCode.YJ_PLATFORM_ERROR_CODE, e.getMessage()));
		}
		return resp;
	}


	/**
	 * 业务办理资格结果查询
	 */
	@Override
	public QueryBusinessQualificationResultResp getBusinessQualificationResult(
			QueryBusinessQualificationResultReq req) {

		QueryBusinessQualificationResultResp resp = new QueryBusinessQualificationResultResp();

		// 根据transactionId查询缓存信息
		String key = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.CMBS_BUSINESS_QUALIFICATION,
				req.getTransactionId());
		BusinessQualification result = redisTemplate.get(key, BusinessQualification.class);
		// 未存在缓存校验结果，返回等待
		if (CommonUtil.isNull(result)) {
			resp.setResult(new Result(IResultCode.BUSINESS_QUALIFICATION_HANDING, "等待上游返回结果"));
			resp.setUpResultCode("");
			resp.setUpResultMsg("");
		} else {
			resp.setUpResultCode(result.getUpResultCode());
			resp.setUpResultMsg(result.getUpResultMsg());
		}

		return resp;
	}

	/**
	 * <查询用户状态>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryUserStatusResp queryUserStatus(QueryUserStatusReq req) throws Exception {
		QueryYJPlatFormServiceReq queryYJPlatFormServiceReq = new QueryYJPlatFormServiceReq("01", req.getPhoneNumber());
		QueryUserStatusResp queryUserStatusResp = cmbsQueryYJPLatFormService
				.queryUserStatusService(queryYJPlatFormServiceReq);
		return queryUserStatusResp;
	}

	/**
	 * <套餐用量查询>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryMonthFlowUseResponse queryUserPackageInfo(QueryUserPackageInfoReq req) throws Exception {

		QueryYJPlatFormServiceReq queryYJPlatFormServiceReq = new QueryYJPlatFormServiceReq("01", req.getPhoneNumber());
		QueryMonthFlowUseResponse queryUserStatusResp = cmbsQueryYJPLatFormService
				.queryPackageUseInfo(queryYJPlatFormServiceReq);
		return queryUserStatusResp;
	}

	// /**
	// * <营销案办理资格校验>
	// *
	// * @param req
	// * @return
	// * @throws Exception
	// */
	// @Override
	// public CheckMktPlanQualificationResp
	// checkMktPlanQualification(CheckMktPlanQualificationReq req)
	// throws Exception {
	// String phoneNumber = req.getPhoneNumber();
	//
	// String channelCode = "";
	// Long productId = Long.parseLong(req.getProductId());
	// List<CmbsProdChar> cmbsProdCharList =
	// cmbsProdService.getCmbsProdCharsByProductId(productId);
	// if (CommonUtil.isNotEmptyCollection(cmbsProdCharList)) {
	// for (CmbsProdChar cmbsProdChar : cmbsProdCharList) {
	// String specCharCode = cmbsProdChar.getSpecCharCode();
	// if
	// (MktCampaignConst.CmbsMiguVideo.ABILITY_CHANNEL_CODE.equals(specCharCode))
	// {
	// channelCode = cmbsProdChar.getVal();
	// break;
	// }
	// }
	// }
	// CmbsProdChannelAbilityCfg cfg = cmbsProdService
	// .getCmbsProdChannelAbilityCfgByProductIdAndChannelAndType(productId,
	// channelCode,
	// MktCampaignConst.CmbsMiguVideo.ABILITY_QUERY_OPER_TYPE);
	//
	// String marketplanId = cfg.getProductCode();
	// JsBaseRsp rsp =
	// jsOutClientService.marketplanCheck(phoneNumber,marketplanId,null, null);
	// CheckMktPlanQualificationResp resp = new CheckMktPlanQualificationResp();
	// if(IResultCode.OSPB_YJ_PLATFORM_RETURN_SUCCESS.equals(rsp.getStatus())){
	// resp.setCanOrder(true);
	// } else {
	// resp.setCanOrder(false);
	// resp.setResult(new Result(IResultCode.YJ_PLATFORM_ERROR_CODE,
	// rsp.getMessage()));
	// }
	// return resp;
	// }
	/**
	 * <宽带身份校验>
	 *
	 * @param req
	 *            [CheckBroadbandIdentityReq]
	 * @return CheckBroadbandIdentityResp
	 * @throws Exception
	 *             [Exception]
	 */
	@Override
	public CheckBroadbandIdentityResp checkBroadbandIdentity(CheckBroadbandIdentityReq req) throws Exception {
		CheckBroadbandIdentityResp resp = new CheckBroadbandIdentityResp();
		CheckBroadbandStatusReq checkBroadbandStatusReq = new CheckBroadbandStatusReq(req.getPhoneNumber(),
				req.getIdNo(), req.getUserName());
		CheckBroadbandStatusResp checkBroadbandStatusResp = jsOutClientService
				.broadbandIdentityCheck(checkBroadbandStatusReq);
		if (CommonUtil.isNotNull(checkBroadbandStatusResp)) {
			if (!StringUtils.equals(MktCampaignConst.Number.STRING_ZERO, checkBroadbandStatusResp.getStatus())) {
				resp.setResult(
						new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, checkBroadbandStatusResp.getMessage()));
			}
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * 宽带办理校验
	 * 
	 * @param req
	 *            [CheckOrderBroadbandReq]
	 * @return CheckOrderBroadbandResp
	 */
	@Override
	public CheckOrderBroadbandResp checkOrderBroadband(CheckOrderBroadbandReq req) throws Exception {
		CheckOrderBroadbandResp checkOrderBroadbandResp = new CheckOrderBroadbandResp();
		JsCheckOrderBroadbandReq jsCheckOrderBroadbandReq = new JsCheckOrderBroadbandReq();
		jsCheckOrderBroadbandReq.setServnumber(req.getPhoneNumber());
		jsCheckOrderBroadbandReq.setIdNo(req.getIdNo());
		jsCheckOrderBroadbandReq.setType(req.getType());
		jsCheckOrderBroadbandReq.setUserName(req.getUserName());
		JsCheckOrderBroadbandRsp jsCheckOrderBroadbandRsp = jsOutClientService
				.getCheckOrderBroadband(jsCheckOrderBroadbandReq);
		if (StringUtils.equals(MktCampaignConst.Number.STRING_ZERO, jsCheckOrderBroadbandRsp.getStatus())) {
			checkOrderBroadbandResp
					.setResult(new Result(IResultCode.SUCCESS_CODE, jsCheckOrderBroadbandRsp.getMessage()));
			checkOrderBroadbandResp.setStatus(MktCampaignConst.Number.STRING_ZERO);
			checkOrderBroadbandResp.setMessage(jsCheckOrderBroadbandRsp.getMessage());
		} else {
			checkOrderBroadbandResp.setResult(
					new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, jsCheckOrderBroadbandRsp.getMessage()));
			checkOrderBroadbandResp.setStatus(MktCampaignConst.Number.STRING_ONE);
			checkOrderBroadbandResp.setMessage(jsCheckOrderBroadbandRsp.getMessage());
		}
		return checkOrderBroadbandResp;
	}

	/**
	 * <查询宽带地址>
	 * 
	 * @param req
	 *            [QueryBroadbandAddressReq]
	 * @return QueryBroadbandAddressResp
	 * @throws Exception
	 *             [Exception]
	 */
	@Override
	public QueryBroadbandAddressResp queryBroadbandAddress(QueryBroadbandAddressReq req) throws Exception {
		QueryBroadbandAddressResp resp = new QueryBroadbandAddressResp();
		BroadbandAddressQueryReq broadbandAddressQueryReq = new BroadbandAddressQueryReq(req.getRegion(),
				req.getAddress(), req.getAddressId(), req.getType(), req.getPage(), req.getLimit());
		BroadbandAddressQueryResp broadbandAddressQueryResp = jsOutClientService
				.broadbandAddressQuery(broadbandAddressQueryReq);
		if (CommonUtil.isNotNull(broadbandAddressQueryResp)) {
			if (StringUtils.equals(MktCampaignConst.Number.STRING_ZERO, broadbandAddressQueryResp.getStatus())) {
				resp.setAddressList(broadbandAddressQueryResp.getList());
			} else {
				resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE,
						broadbandAddressQueryResp.getMessage()));
			}
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * <queryBroadbandOrder>. <查询宽带订单>
	 * 
	 * @param request
	 *            [request]
	 * @return [返回宽带订单]
	 * @exception/throws [Exception] [Exception]
	 * @author jianghao
	 */
	@Override
	public QueryBroadbandOrderResp queryBroadbandOrder(QueryBroadbandOrderReq request) throws Exception {
		// 初始化
		QueryBroadbandOrderResp response = new QueryBroadbandOrderResp();

		// 远程调用
		JsQueryBroadbandOrderRsp feignRsp = jsOutClientService.queryBroadbandOrder(request.getOrderType(),
				request.getSubOrderId());

		// 设置返回值
		String feignStatus = feignRsp.getStatus();
		String feignMessage = feignRsp.getMessage();
		if (IResultCode.JS_BROADBAND_ORDER_SUCCESS.equals(feignStatus)) {
			response.setResult(new Result(IResultCode.SUCCESS_CODE, feignMessage));
		} else if (IResultCode.CMBS_PARAM_VALIDATE_FAIL.equals(feignStatus)) {
			response.setResult(new Result(IResultCode.CMBS_PARAM_VALIDATE_FAIL, feignMessage));
		} else {
			response.setResult(new Result(IResultCode.SUB_ORDER_NOT_EXIST, feignMessage));
		}
		ResultData resultData = feignRsp.getData();
		if (resultData != null) {
			response.setOrderStatus(resultData.getStatus());
			response.setStatusMsg(resultData.getStatusMsg());
		}
		return response;
	}

	/**
	 * 查询目标用户
	 */
	@Override
	public QueryTargetUserResp queryTargetUser(QueryTargetUserReq req) throws Exception {
		QueryTargetUserResp resp = new QueryTargetUserResp();
		TargetUserQueryReq targetUserQueryReq = new TargetUserQueryReq(req.getPhoneNumber(), req.getScene());
		TargetUserQueryResp targetUserQueryResp = jsOutClientService.TargetUserQuery(targetUserQueryReq);
		if (StringUtils.equals(MktCampaignConst.Number.STRING_ZERO, targetUserQueryResp.getStatus())) {
			if (CommonUtil.isNotNull(targetUserQueryResp.getData())) {
				resp.setActivityList(targetUserQueryResp.getData().getActivity());
			}
		} else {
			resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, targetUserQueryResp.getMessage()));
		}
		return resp;
	}

	/**
	 * <checkMktPlanQualification>. <营销案办理资格校验>
	 * 
	 * @param req
	 *            [request]
	 * @return [返回校验结果]
	 * @exception/throws [Exception] [Exception]
	 * @author jianghao
	 */
	@Override
	public CheckMktPlanQualificationResp checkMktPlanQualification(CheckMktPlanQualificationReq req) {
		// 初始化
		String phoneNumber = req.getPhoneNumber();
		String productId = req.getProductId();
		String quantity = req.getQuantity();

		// 远程调用
		JsBaseRsp rsp = jsOutClientService.marketplanCheck(phoneNumber, productId, quantity);

		// 设置返回值
		CheckMktPlanQualificationResp resp = new CheckMktPlanQualificationResp();
		if (MktCampaignConst.Number.STRING_ZERO.equals(rsp.getStatus())) {
			resp.setCanOrder(true);
			resp.setResult(new Result(IResultCode.SUCCESS_CODE, IResultCode.SUCCESS_MESSAGE));
		} else if (IResultCode.CMBS_PARAM_VALIDATE_FAIL.equals(rsp.getStatus())) {
			resp.setCanOrder(false);
			resp.setResult(new Result(IResultCode.CMBS_PARAM_VALIDATE_FAIL, rsp.getMessage()));
		} else {
			resp.setCanOrder(false);
			resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, rsp.getMessage()));
		}
		return resp;
	}

	@Override
	public QuerySubscriptionResp querySubscription(QuerySubscriptionReq req) {
		JsBusinessOpeningRes res = jsOutClientService.getBusinessOping(req.getPhoneNumber(), req.getOfferId());
		QuerySubscriptionResp querySubscriptionResp = new QuerySubscriptionResp();
		if (CommonUtil.isNotNull(res) && StringUtils.equals(MktCampaignConst.Number.STRING_ZERO, res.getStatus())) {
			querySubscriptionResp.setOfferId(res.getData().getOfferid());
			querySubscriptionResp.setOfferName(res.getData().getOffername());
			querySubscriptionResp.setStatus(res.getData().getStatus());
		} else {
			querySubscriptionResp
					.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常：" + res.getMessage()));
		}
		return querySubscriptionResp;
	}

	@Override
	public QueryGDFlowResp queryGDFlowInfo(QueryGDFlowReq req) throws Exception {
		QueryGDFlowResp resp = new QueryGDFlowResp();
		QueryGdServiceReq queryGdServiceReq = new QueryGdServiceReq();
		queryGdServiceReq.setPhoneNumber(req.getPhoneNumber());
		String gdFlowInfo = cmbsGDQueryService.queryAllFlow(queryGdServiceReq);
		if (StringUtils.isNotBlank(gdFlowInfo)) {
			resp.setGdFlowInfo(gdFlowInfo);
		} else {
			resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}
		return resp;
	}

	/**
	 * 山东offer推荐
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Override
	public GetRecommendOfferResp getRecommendOffer(GetRecommendOfferReq req) throws Exception {

		GetRecommendOfferResp recommendOfferResp = new GetRecommendOfferResp();
		// 参数转化feign
		QueryOfferInfoReq queryOfferInfoReq = new QueryOfferInfoReq();
		queryOfferInfoReq.setEventCode(req.getEventCode());
		queryOfferInfoReq.setTelNum(req.getPhoneNumber());
		queryOfferInfoReq.setVersion(req.getVersion());
		queryOfferInfoReq.setEventAttrMap(req.getEventAttrMap());

		QueryOfferInfoRsp offerInfoRsq = sdOutClientService.queryOfferInfo(queryOfferInfoReq);
		// 服务降级
		if (CommonUtil.isNull(offerInfoRsq)
				|| StringUtils.equals(BaseServiceFallback.FALLBACK_CODE, offerInfoRsq.getResCode())) {
			recommendOfferResp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, "调用上游返回异常"));
		} else {
			recommendOfferResp.setResult(new Result(IResultCode.SUCCESS_CODE, IResultCode.SUCCESS_MESSAGE));
		}
		// 返回参数赋值
		recommendOfferResp.setUpResultCode(offerInfoRsq.getResCode());
		recommendOfferResp.setUpResultMsg(offerInfoRsq.getResDesc());

		// list<offer>
		RecommendOfferResult recommendOfferResult = offerInfoRsq.getResult();
		if (CommonUtil.isNotNull(recommendOfferResult)) {
			List<RecommendOffer> recommendOffers = recommendOfferResult.getOfferList();
			if (CommonUtil.isNotEmptyCollection(recommendOffers)) {
				List<Offer> offerList = new ArrayList<>();
				for (RecommendOffer recommendOffer : recommendOffers) {
					Offer offer = new Offer();
					BeanUtils.copyProperties(recommendOffer, offer);
					offerList.add(offer);
					recommendOfferResp.setOfferList(offerList);
				}
			}
		}
		return recommendOfferResp;
	}

	/**
	 * 获取山东的token
	 * 
	 * @param querySdAccessTokenReq
	 * @return
	 * @throws Exception
	 */
	public String queryAccessToken() throws Exception {
		return JsonUtil.objectToString(sdOutClientService.queryAccessToken());
	}
}
