package com.migu.redstone.order.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.client.dto.model.InPlanQry;
import com.migu.redstone.client.dto.model.PackageList;
import com.migu.redstone.client.dto.model.ResourcesInfo;
import com.migu.redstone.client.dto.model.ResourcesLeftInfo;
import com.migu.redstone.client.dto.model.SecResourcesInfo;
import com.migu.redstone.client.dto.response.QueryMonthFlowUseResp;
import com.migu.redstone.client.dto.response.QueryOspAccountBalanceRsp;
import com.migu.redstone.client.dto.response.QueryUserPackageInfoRsp;
import com.migu.redstone.client.proxy.interfaces.IFirstAbilityFeignProxy;
import com.migu.redstone.common.dto.QueryOspUserStatusRsp;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.RedisKey;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.emums.SignTypeCode;
import com.migu.redstone.order.service.dto.request.QueryYJPlatFormServiceReq;
import com.migu.redstone.order.service.dto.response.QueryChargeBalanceResp;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.dto.response.QueryMonthFlowUseResponse;
import com.migu.redstone.order.service.dto.response.QueryUserStatusResp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJPlatFormService;
import com.migu.redstone.redis.RedisTemplate;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;

/**
 * 查询用户已使用服务情况
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.impl
 * @类名称 CmbsQueryAccountService
 * @类描述 用户已使用服务情况接口实现
 * @创建时间 2018年11月27日 下午3:17:09
 */

@Service
public class CmbsQueryYJPLatFormService implements ICmbsQueryYJPlatFormService {

    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsGDQueryService.class);

    @Autowired
    private IFirstAbilityFeignProxy firstAbilityFeignProxy;

    /**
     * redisTemplate.
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * cfgCommonService
     */
    @Autowired
    private ICfgCommonService cfgCommonService;

    /**
     * queryPackageUseInfo
     * 
     * @param platFormServiceReq
     *            [QueryYJPlatFormServiceReq]
     * @return 描述：查询套餐用量情况
     * @throws Exception
     */
    @Override
    public QueryMonthFlowUseResponse queryPackageUseInfo(QueryYJPlatFormServiceReq platFormServiceReq)
            throws Exception {
        // 本期只有01：手机号码
        platFormServiceReq.setServiceType(SignTypeCode.PHONE_NUMBER.getServiceType());

        // 返回word结果
        QueryMonthFlowUseResponse rsp = new QueryMonthFlowUseResponse();
        QueryMonthFlowUseResp response = new QueryMonthFlowUseResp();
        try {
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
                    StaticDataConst.StaticCodeName.FIRST_QUERY_PACKAGE_USERINFO_URL);
            Map<String, Object> expandMap = new HashMap<String, Object>();
            expandMap.put("MSISDN", platFormServiceReq.getServiceNumber());
            response = firstAbilityFeignProxy.queryPackageUserInfo(expandMap, new URI(staticData.getCodeValue()),
                    platFormServiceReq.getServiceType(), platFormServiceReq.getServiceNumber());
            if (LOG.isDebugEnabled()) {
                LOG.debug("process the method queryPackageUseInfo response=" + JsonUtil.objectToString(response));
            }
            if (CommonUtil.isNotNull(response)
                    && StringUtils.equals(response.getBizCode(), IResultCode.YJ_PLATFORM_SUCCESS_CODE)) {
                // rsp.setResult(result);
                rsp.setUserValue(response.getUserValue());
                rsp.setBillValue(response.getBillValue());
                rsp.setServiceNumber(response.getServiceNumber());
                rsp.setPackageUsedList(response.getPackageUsedList());
            } else {
                rsp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, response.getBizDesc()));
            }
        } catch (Exception e) {
            LOG.error("process  the method queryPackageUseInfo error e=" + e);
        }
        return rsp;
    }

    /**
     * queryAcountBalance
     * 
     * @param formServiceReq
     *            [QueryAccountInfoReq]
     * @return 描述：查询账户余额
     * @throws Exception
     *             [Exception]
     */
    @Override
    public QueryChargeBalanceResp queryAcountBalance(QueryYJPlatFormServiceReq formServiceReq) throws Exception {
        // Map<String, String> headerMap = getHeadMap(null, formServiceReq);
        // 本期只有01：手机号码
        formServiceReq.setServiceType(SignTypeCode.PHONE_NUMBER.getServiceType());
        // url工具类解析url
        // String url = UrlUtil.packagingUrl(acountBalanceUrl, formServiceReq);

        // 返回结果
        QueryChargeBalanceResp response = new QueryChargeBalanceResp();
        try {
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
                    StaticDataConst.StaticCodeName.FIRST_QUERY_ACOUNTBALANCE_URL);
            Map<String, Object> expandMap = new HashMap<String, Object>();
            expandMap.put("MSISDN", formServiceReq.getServiceNumber());
            QueryOspAccountBalanceRsp ospRsp = firstAbilityFeignProxy
                    .queryAcountBalance(expandMap, new URI(staticData.getCodeValue()),formServiceReq.getServiceNumber(), formServiceReq.getServiceType());

            // String entity = yjRestHttpclient.get(headerMap, url, proxySwtich, proxyHost, proxyPort);
            if (LOG.isDebugEnabled()) {
                LOG.debug("processs the method queryAcountBalance ospRsp=" + JsonUtil.objectToString(ospRsp));
            }
            if (IResultCode.YJ_PLATFORM_SUCCESS_CODE.equals(ospRsp.getBizCode())) {

                response.setTotalAvailableAmt(ospRsp.getBalance());
                response.setCurrentAvailableAmt(ospRsp.getCurFeeTotal());

            } else {
                response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, ospRsp.getBizDesc()));

            }
        } catch (Exception e) {
            LOG.error("process  the method queryAcountBalance error e=" + e);
            response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
        }
        return response;
    }

    /**
     * queryFlowUseInfo
     * 
     * @param platFormServiceReq
     *            [QueryAccountInfoReq]
     * @return OSPQ034 描述：流量使用情况脱敏查询接口
     * @throws Exception
     *             [Exception]
     */
    @Override
    public QueryFlowInfoResp queryFlowUseInfo(QueryYJPlatFormServiceReq platFormServiceReq, String queryType,
            String productLine) throws Exception {
        // 返回结果
        QueryFlowInfoResp response = new QueryFlowInfoResp();
        QueryFlowInfoResp maxResponse = new QueryFlowInfoResp();
        CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                StaticDataConst.StaticCodeName.FLOW_EXPIRE_TYPE, StaticDataConst.StaticCodeName.FLOW_EXPIRE_NAME);
        int expire = MktCampaignConst.Number.ZERO;
        if (CommonUtil.isNotNull(staticData)) {
            expire = Integer.parseInt(staticData.getCodeValue());
        } else {
            expire = MktCampaignConst.Number.FIVE;
        }
        String key = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
                platFormServiceReq.getServiceNumber(), queryType);
        if (redisTemplate.exists(key)) {
            response = redisTemplate.get(key, QueryFlowInfoResp.class);
            if (CommonUtil.isNotNull(response) && CommonUtil.isNotNull(response.getResult())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(">>>>>> process the method queryFlowUseInfo from redis response="
                            + JsonUtil.objectToString(response));
                }
                return response;
            } else {
                QueryFlowInfoResp resp = new QueryFlowInfoResp();
                resp.setResult(new Result(IResultCode.QUERY_GD_ERROR, "系统繁忙，请稍后"));
                return resp;
            }
        } else {
            key = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
                    platFormServiceReq.getServiceNumber(), CommonConst.YJPlatformConsts.MAX);
            response.setResult(null);
            redisTemplate.set(key, response);
            redisTemplate.expire(key, expire * MktCampaignConst.Number.SIXTY);

            key = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
                    platFormServiceReq.getServiceNumber(), CommonConst.YJPlatformConsts.MIN);
            redisTemplate.set(key, response);
            redisTemplate.expire(key, expire * MktCampaignConst.Number.SIXTY);
        }

        // Map<String, String> headerMap = getHeadMap(null, platFormServiceReq);
        // 本期只有01：手机号码
        platFormServiceReq.setServiceType(SignTypeCode.PHONE_NUMBER.getServiceType());
        // url工具类解析url
        // String url = UrlUtil.packagingUrl(flowUserInfoUrl, platFormServiceReq);
        try {
//
            CmbsStaticData staticDataUrl = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
                    StaticDataConst.StaticCodeName.FIRST_QUERY_USER_FLOW_URL);
            Map<String, Object> expandMap = new HashMap<String, Object>();
            expandMap.put("MSISDN", platFormServiceReq.getServiceNumber());
            QueryUserPackageInfoRsp userPackageInfoRsp = firstAbilityFeignProxy.queryUserFlowInfo(expandMap, 
                    new URI(staticDataUrl.getCodeValue()), platFormServiceReq.getServiceNumber(),
                    platFormServiceReq.getServiceType());
//            String entity =
//                    "{\"bizCode\":\"1\",\"bizDesc\":\"成功\",\"oprTime\":\"20181228155139\",\"packageList\":[{\"inPlanQry\":[{\"planName\":\"流量安心包（100MB档）\",\"planId\":\"2000007020\"},{\"planName\":\"流量日套餐1G\",\"planId\":\"2000007483\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"流量日套餐1G\",\"resourcesLeftInfo\":[{\"totalRes\":\"1000-1500\",\"remainRes\":\"1000-1500\",\"unit\":\"03\",\"gprs_net_type\":\"1\",\"serviceIdList\":\"\"}]}]}]},{\"planName\":\"定向流量月包（话费扣费）-17元-30G-优酷视频\",\"planId\":\"2000009342\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"定向流量月包（话费扣费）-17元-30G-优酷视频\",\"resourcesLeftInfo\":[{\"totalRes\":\">20480\",\"remainRes\":\">20480\",\"unit\":\"03\",\"gprs_net_type\":\"2\",\"serviceIdList\":\"1750000006\"}]}]}]}]}]}"; 
//            QueryUserPackageInfoRsp userPackageInfoRsp = JsonUtil.stringToObject(entity, QueryUserPackageInfoRsp.class);
            if (LOG.isDebugEnabled()) {
                LOG.debug("process  the method  flowUserInfo  the userPackageInfoRsp="
                        + JsonUtil.objectToString(userPackageInfoRsp));
            }
            if (IResultCode.YJ_PLATFORM_SUCCESS_CODE.equals(userPackageInfoRsp.getBizCode())) {
                // 对响应封装-视讯结果
                installFlowInfoResp(response, userPackageInfoRsp, maxResponse, productLine);
                response.setResult(Result.success());
                maxResponse.setResult(Result.success());
                try {
                    String maxkey = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
                            platFormServiceReq.getServiceNumber(), CommonConst.YJPlatformConsts.MAX);
                    redisTemplate.set(maxkey, maxResponse);
                    String minkey = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
                            platFormServiceReq.getServiceNumber(), CommonConst.YJPlatformConsts.MIN);
                    redisTemplate.set(minkey, response);
                    redisTemplate.expire(maxkey, expire * MktCampaignConst.Number.SIXTY);
                    redisTemplate.expire(minkey, expire * MktCampaignConst.Number.SIXTY);
                } catch (Exception e) {
                    LOG.error(">>>>> process queryFlowInfo method ,set response into redis error");
                }
            } else {
                response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
            }

        } catch (Exception e) {
            LOG.error("process the method queryFlowUseInfo error e=" + e);
            response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
        }
        if (CommonConst.YJPlatformConsts.MAX.equals(queryType)) {
            return maxResponse;
        } else {
            return response;
        }
    }

    /**
	 * 〈查询流量响应〉 〈查询流量响应〉
	 *
	 * @param response
	 *            [QueryFlowInfoResp]
	 * @param userPackageInfoRsp
	 *            [String]
	 * @param maxResponse
	 *            [QueryFlowInfoResp]
	 * @param productLine
	 */
	private void installFlowInfoResp(QueryFlowInfoResp response, QueryUserPackageInfoRsp userPackageInfoRsp,
			QueryFlowInfoResp maxResponse, String productLine) {

		Float maxCommonValue = (float) MktCampaignConst.Number.ZERO;
		Float minCommonValue = (float) MktCampaignConst.Number.ZERO;
		Float maxCommonLeftValue = (float) MktCampaignConst.Number.ZERO;
		Float minCommonLeftValue = (float) MktCampaignConst.Number.ZERO;
		Float minDingXiangFlowAmt = (float) MktCampaignConst.Number.ZERO;
		Float maxDingXiangFlowAmt = (float) MktCampaignConst.Number.ZERO;
		Float minDingXiangLeftValue = (float) MktCampaignConst.Number.ZERO;
		Float maxDingXiangLeftValue = (float) MktCampaignConst.Number.ZERO;
		String serviceid = "";
		// QueryUserPackageInfoRsp yjResponse =
		// JsonUtil.stringToObject(userPackageInfoRsp,
		// QueryUserPackageInfoRsp.class);

		if (LOG.isDebugEnabled()) {
			LOG.debug(" enter the method userPackageInfoRsp" + JsonUtil.objectToString(userPackageInfoRsp));
		}

		boolean isDingXiang = false;
		boolean isCommonMaxNoLimit = false;
		boolean isDingxiangMaxNoLimit = false;
		// 结果遍历循环获取流量数据相加
		List<PackageList> packageList = userPackageInfoRsp.getPackageList();
		if (CollectionUtils.isNotEmpty(packageList)) {
			for (PackageList list : packageList) {
				if (CommonUtil.isNotNull(list)) {
					List<InPlanQry> inPlanQry = list.getInPlanQry();
					if (CollectionUtils.isNotEmpty(inPlanQry)) {
						for (InPlanQry planQry : inPlanQry) {
							if (CommonUtil.isNotNull(planQry)) {

								CmbsStaticData datas = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
										StaticDataConst.StaticCodeType.MG_SERVICEID_MAPPING, productLine);
								if (CommonUtil.isNotNull(datas)) {
									serviceid = datas.getCodeValue();
								}
								List<ResourcesInfo> resourcesInfo = planQry.getResourcesInfo();
								if (CollectionUtils.isNotEmpty(resourcesInfo)) {
									for (ResourcesInfo info : resourcesInfo) {

										String resourcesCode = info.getResourcesCode();
										// 临时测试使用
										if ("04".equals(resourcesCode)
												&& CollectionUtils.isNotEmpty(info.getSecResourcesInfo())) {

											List<SecResourcesInfo> secResourcesInfo = info.getSecResourcesInfo();
											for (SecResourcesInfo info2 : secResourcesInfo) {
												List<ResourcesLeftInfo> resourcesLeftInfo = info2
														.getResourcesLeftInfo();
												if (CollectionUtils.isNotEmpty(resourcesLeftInfo)) {
													for (ResourcesLeftInfo info3 : resourcesLeftInfo) {
														String totalRes = info3.getTotalRes();
														String remainRes = info3.getRemainRes();
														// 是否含有定向流量
														if (String.valueOf(MktCampaignConst.Number.TWO)
																.equals(info3.getGprs_net_type())) {
															isDingXiang = true;
														}
														// 是否含有视讯定向
														if (String.valueOf(MktCampaignConst.Number.TWO)
																.equals(info3.getGprs_net_type())
																&& StringUtils.isNotBlank(serviceid)
																&& StringUtils.isNotBlank(info3.getServiceIdList())
																&& info3.getServiceIdList().indexOf(
																		serviceid) >= MktCampaignConst.Number.ZERO) {
															String[] dingxiangResArr = totalRes.split("-");
															if (dingxiangResArr.length > MktCampaignConst.Number.ONE) {
																minDingXiangFlowAmt = minDingXiangFlowAmt
																		+ Float.parseFloat(
																				dingxiangResArr[MktCampaignConst.Number.ZERO]);
																maxDingXiangFlowAmt = maxDingXiangFlowAmt
																		+ Float.parseFloat(
																				dingxiangResArr[MktCampaignConst.Number.ONE]);
															} else if (totalRes.contains(">")) {
																isDingxiangMaxNoLimit = true;
																minDingXiangFlowAmt = minDingXiangFlowAmt
																		+ Float.parseFloat(totalRes.substring(
																				MktCampaignConst.Number.ONE));
																maxDingXiangFlowAmt = maxDingXiangFlowAmt
																		+ Float.parseFloat(totalRes.substring(
																				MktCampaignConst.Number.ONE));
															} else {
																minDingXiangFlowAmt = minDingXiangFlowAmt
																		+ Float.parseFloat(totalRes);
																maxDingXiangFlowAmt = maxDingXiangFlowAmt
																		+ Float.parseFloat(totalRes);
															}

															String[] dxremainResArr = remainRes.split("-");
															if (dxremainResArr.length > MktCampaignConst.Number.ONE) {
																minDingXiangLeftValue = minDingXiangLeftValue
																		+ Float.parseFloat(
																				dxremainResArr[MktCampaignConst.Number.ZERO]);
																maxDingXiangLeftValue = maxDingXiangLeftValue
																		+ Float.parseFloat(
																				dxremainResArr[MktCampaignConst.Number.ONE]);
															} else if (remainRes.contains(">")) {
																minDingXiangLeftValue = minDingXiangLeftValue
																		+ Float.parseFloat(remainRes.substring(
																				MktCampaignConst.Number.ONE));
																maxDingXiangLeftValue = maxDingXiangLeftValue
																		+ Float.parseFloat(remainRes.substring(
																				MktCampaignConst.Number.ONE));
															} else {
																minDingXiangLeftValue = minDingXiangLeftValue
																		+ Float.parseFloat(remainRes);
																maxDingXiangLeftValue = maxDingXiangLeftValue
																		+ Float.parseFloat(remainRes);
															}

														} else {

															if (StringUtils.isNotBlank(totalRes)) {
																String[] totalResArr = totalRes.split("-");
																if (totalResArr.length > MktCampaignConst.Number.ONE) {
																	minCommonValue = minCommonValue + Float.parseFloat(
																			totalResArr[MktCampaignConst.Number.ZERO]);
																	maxCommonValue = maxCommonValue + Float.parseFloat(
																			totalResArr[MktCampaignConst.Number.ONE]);
																} else if (totalRes.contains(">")) {
																	isCommonMaxNoLimit = true;
																	minCommonValue = minCommonValue
																			+ Float.parseFloat(totalRes.substring(1));
																	maxCommonValue = maxCommonValue
																			+ Float.parseFloat(totalRes.substring(1));
																} else {
																	minCommonValue = minCommonValue
																			+ Float.parseFloat(totalRes);
																	maxCommonValue = maxCommonValue
																			+ Float.parseFloat(totalRes);
																}
															}
															if (StringUtils.isNotBlank(remainRes)) {
																String[] remainResArr = remainRes.split("-");
																if (remainResArr.length > MktCampaignConst.Number.ONE) {
																	minCommonLeftValue = minCommonLeftValue
																			+ Float.parseFloat(
																					remainResArr[MktCampaignConst.Number.ZERO]);
																	maxCommonLeftValue = maxCommonLeftValue
																			+ Float.parseFloat(
																					remainResArr[MktCampaignConst.Number.ONE]);
																} else if (remainRes.contains(">")) {
																	minCommonLeftValue = minCommonLeftValue
																			+ Float.parseFloat(remainRes.substring(1));
																	maxCommonLeftValue = maxCommonLeftValue
																			+ Float.parseFloat(remainRes.substring(1));
																} else {
																	minCommonLeftValue = minCommonLeftValue
																			+ Float.parseFloat(remainRes);
																	maxCommonLeftValue = maxCommonLeftValue
																			+ Float.parseFloat(remainRes);
																}
															}
														}
													}
												}
											}
										}

									}
								}
							}
						}
					}
				}
			}
			confirmMinFlowResponse(response, minCommonValue, minCommonLeftValue, minDingXiangFlowAmt,
					minDingXiangLeftValue, CommonConst.YJPlatformConsts.MIN, isDingXiang, isCommonMaxNoLimit,
					isDingxiangMaxNoLimit);
			confirmMinFlowResponse(maxResponse, maxCommonValue, maxCommonLeftValue, maxDingXiangFlowAmt,
					maxDingXiangLeftValue, CommonConst.YJPlatformConsts.MAX, isDingXiang, isCommonMaxNoLimit,
					isDingxiangMaxNoLimit);
		} else {
			response.setResult(new Result(IResultCode.YJ_PLATFORM_ERROR_CODE, "没有查询到流量信息"));
		}

	}

    /**
     * 
     * 〈构造流量请求〉 〈构造流量请求〉
     * 
     * @param response
     * @param minTotalValue
     * @param minTotalLeftValue
     * @param minDingXiangFlowAmt
     * @param minDingXiangLeftValue
     * @param queryType
     * @param isDingXiang
     * @param isMaxNoLimit 
     * @param isDingxiangMaxNoLimit 
     */
    private void confirmMinFlowResponse(QueryFlowInfoResp response, Float commontotalValue, Float commontotalLeftValue,
            Float dingXiangFlowAmt, Float dingXiangLeftValue, String queryType, boolean isDingXiang,
            boolean isMaxNoLimit, boolean isDingxiangMaxNoLimit) {
        if (CommonConst.YJPlatformConsts.MIN.equals(queryType)) {
            response.setTotalFlowAmt(String.valueOf(commontotalValue + dingXiangFlowAmt));
            response.setTotalLeftFlowAmt(String.valueOf(commontotalLeftValue + dingXiangLeftValue));
            // 存在定向流量
            if (isDingXiang) {
                /**
                 * else  有定向非视讯
                 */
                if (MktCampaignConst.Number.ZERO < dingXiangFlowAmt) {
                    // 有视讯的定向流量
                    hasVideoDingxiangFlowMin(response, commontotalValue, commontotalLeftValue, dingXiangFlowAmt,
                            dingXiangLeftValue);
                }
            } else {
                // 不存在定向流量
                response.setCommonFlowAmt(String.valueOf(commontotalValue));
                response.setCommonLeftFlowAmt(String.valueOf(commontotalLeftValue));
            }

        } else {

            if (isMaxNoLimit || isDingxiangMaxNoLimit) {
                response.setTotalFlowAmt(CommonConst.RegExp.LINEAE);
                response.setTotalLeftFlowAmt(CommonConst.RegExp.LINEAE);
            } else {
                response.setTotalFlowAmt(String.valueOf(commontotalValue + dingXiangFlowAmt));
                response.setTotalLeftFlowAmt(String.valueOf(commontotalLeftValue + dingXiangLeftValue));
            }
            if (isDingXiang) {

                if (MktCampaignConst.Number.ZERO < dingXiangFlowAmt) {
                    // 有视讯的定向流量
                     hasVideoDingxiangVideoMax(response, commontotalValue, commontotalLeftValue, dingXiangFlowAmt,
                     dingXiangLeftValue,isMaxNoLimit,isDingxiangMaxNoLimit);
                }

            } else {
                if (isMaxNoLimit) {
                    response.setCommonFlowAmt(CommonConst.RegExp.LINEAE);
                    response.setCommonLeftFlowAmt(CommonConst.RegExp.LINEAE);
                } else {
                    response.setCommonFlowAmt(String.valueOf(commontotalValue));
                    response.setCommonLeftFlowAmt(String.valueOf(commontotalLeftValue));

                }
            }
        }
    }

    /**
     * 
     * 〈 有视讯的定向流量-min〉 〈 有视讯的定向流量〉
     * 
     * @param response
     * @param totalValue
     * @param totalLeftValue
     * @param dingXiangFlowAmt
     * @param dingXiangLeftValue
     */
    private void hasVideoDingxiangFlowMin(QueryFlowInfoResp response, Float CommonTotalValue,
            Float CommonTotalLeftValue, Float dingXiangFlowAmt, Float dingXiangLeftValue) {
        // 存在视讯的定向流量

        response.setCommonFlowAmt(String.valueOf(CommonTotalValue));

        response.setCommonLeftFlowAmt(String.valueOf(CommonTotalLeftValue));

        response.setDingXiangFlowAmt(String.valueOf(dingXiangFlowAmt));

        response.setDingXiangLeftFlowAmt(String.valueOf(dingXiangLeftValue));
    }

    /**
     * 
     * 〈有视讯的定向流量-max〉 〈有视讯的定向流量-max〉
     * 
     * @param response
     * @param totalValue
     * @param totalLeftValue
     * @param dingXiangFlowAmt
     * @param dingXiangLeftValue
     * @param isDingxiangMaxNoLimit 
     * @param isMaxNoLimit 
     */
    private void hasVideoDingxiangVideoMax(QueryFlowInfoResp response, Float commonTotalValue,
            Float commontotalLeftValue, Float dingXiangFlowAmt, Float dingXiangLeftValue, boolean isMaxNoLimit,
            boolean isDingxiangMaxNoLimit) {

        if (isMaxNoLimit) {
            response.setCommonFlowAmt(CommonConst.RegExp.LINEAE);
            response.setCommonLeftFlowAmt(CommonConst.RegExp.LINEAE);
        } else {
            response.setCommonFlowAmt(String.valueOf(commonTotalValue));
            response.setCommonLeftFlowAmt(String.valueOf(commontotalLeftValue));
        }

        if (isDingxiangMaxNoLimit) {
            response.setDingXiangFlowAmt(CommonConst.RegExp.LINEAE);
            response.setDingXiangLeftFlowAmt(CommonConst.RegExp.LINEAE);
        } else {
            response.setDingXiangFlowAmt(String.valueOf(dingXiangFlowAmt));
            response.setDingXiangLeftFlowAmt(String.valueOf(dingXiangLeftValue));
        }
    }

    /**
     * 查询用户状态 待联调
     */
    @Override
    public QueryUserStatusResp queryUserStatusService(QueryYJPlatFormServiceReq queryAccountInfoReq) throws Exception {

        // 返回结果
        QueryUserStatusResp response = new QueryUserStatusResp();
        // String SCOPE = "[\"OSPQ025\"]";
        // Map<String, String> headerMap = getHeadMap(null, queryAccountInfoReq);
        // // 本期只有01：手机号码
        queryAccountInfoReq.setServiceType(SignTypeCode.PHONE_NUMBER.getServiceType());
        try {

            CmbsStaticData staticDataUrl = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
                    StaticDataConst.StaticCodeName.FIRST_QUERY_USER_STATUS_URL);
            Map<String, Object> expandMap = new HashMap<String, Object>();
            expandMap.put("MSISDN", queryAccountInfoReq.getServiceNumber());
            QueryOspUserStatusRsp ospUserStatusRsp = firstAbilityFeignProxy.queryUserStatus(expandMap, 
            		new URI(staticDataUrl.getCodeValue()),queryAccountInfoReq.getServiceType(), queryAccountInfoReq.getServiceNumber());
            LOG.error("process the method query UserStatusService.  查询一级能开响应entity=  "
                    + JsonUtil.objectToString(ospUserStatusRsp));

            if (IResultCode.YJ_PLATFORM_SUCCESS_CODE.equals(ospUserStatusRsp.getBizCode())) {
                response.setUserStatus(ospUserStatusRsp.getUserStatus());
            } else {
                response.setResult(
                        new Result(IResultCode.YJ_PLATFORM_ERROR_CODE, "查询用户状态异常 ," + ospUserStatusRsp.getBizDesc()));
            }
        } catch (Exception e) {
            LOG.error(" process the method queryUserStatusService error ,e=" + e);
            response.setResult(new Result(IResultCode.YJ_PLATFORM_ERROR_CODE, "查询用户状态异常 ,error=" + e.getMessage()));
            return response;
        }
        return response;
    }

}
