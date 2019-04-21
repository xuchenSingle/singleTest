/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.migu.redstone.client.proxy.impl.FirstAbilityFeignProxy;
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
import com.migu.redstone.client.dto.response.QueryUserPackageInfoRsp;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.RedisKey;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.emums.SignTypeCode;
import com.migu.redstone.order.service.dto.request.QueryFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryYJPlatFormServiceReq;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.interfaces.IQueryFlowService;
import com.migu.redstone.redis.RedisTemplate;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;

/**
 * 查询一级能力平台用户 流量信息
 */
@Service
public class QueryYjFlowService implements IQueryFlowService {

    /**
     * logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(QueryYjFlowService.class);

    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * cfgCommonService
     */
    @Autowired
    private ICfgCommonService cfgCommonService;

    @Autowired
    private FirstAbilityFeignProxy firstAbilityFeignProxy;

    @Override
    public QueryFlowInfoResp queryFlow(QueryFlowInfoReq flowInfoReq,String provinceCode) throws Exception {
        QueryYJPlatFormServiceReq platFormServiceReq = new QueryYJPlatFormServiceReq();
        platFormServiceReq.setServiceNumber(flowInfoReq.getPhoneNumber());
        Map<String, Object> expandMap = new HashMap<String, Object>();
        expandMap.put("MSISDN", flowInfoReq.getPhoneNumber());
        String queryType = flowInfoReq.getQueryType();
        String productLine = flowInfoReq.getRequestHeader().getProductLine();
        // 返回结果
        QueryFlowInfoResp response = new QueryFlowInfoResp();
        QueryFlowInfoResp maxResponse = new QueryFlowInfoResp();
        CmbsStaticData cacheSwitch = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                StaticDataConst.StaticCodeName.CACHE_SWITCH, StaticDataConst.StaticCodeName.CACHE_SWITCH);
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeName.CHANNEL_CODE, provinceCode);
            int expire = MktCampaignConst.Number.ZERO;
            //缓存开关，等于1时打开
        if(MktCampaignConst.Number.STRING_ONE.equals(cacheSwitch.getCodeValue())) {
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
        }
            // Map<String, String> headerMap = getHeadMap(null, platFormServiceReq);
            // 本期只有01：手机号码
            platFormServiceReq.setServiceType(SignTypeCode.PHONE_NUMBER.getServiceType());
            // url工具类解析url
            // String url = UrlUtil.packagingUrl(flowUserInfoUrl,
            // platFormServiceReq);
            try {

//            String entity = "{\"bizCode\":\"1\",\"bizDesc\":\"Processing the request succeeded!\",\"oprTime\":\"20190130152558\",\"packageList\":[{\"inPlanQry\":[{\"planName\":\"4G飞享套餐18元档100M国内流量（2016版）(上月结转)【国内】\",\"planId\":\"gl_ffk_2016|1\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"国内2/3/4G融合流量-上月结转\",\"resourcesLeftInfo\":[{\"totalRes\":\"100-200\",\"remainRes\":\"0-100\",\"unit\":\"03\",\"gprs_net_type\":\"1\",\"residualProportion\":\"84.47%\"}]}]}]},{\"planName\":\"4G飞享套餐18元档100M国内流量（2016版）【国内】\",\"planId\":\"gl_ffk_2016\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"国内2/3/4G融合流量\",\"resourcesLeftInfo\":[{\"totalRes\":\"100-200\",\"remainRes\":\"100-200\",\"unit\":\"03\",\"gprs_net_type\":\"1\",\"residualProportion\":\"100.00%\"}]}]}]},{\"planName\":\"3元2GB酷我音乐定向流量包【国内定向】\",\"planId\":\"pip_gprs_kwyy3y\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"国内4G定向流量\",\"resourcesLeftInfo\":[{\"totalRes\":\"2000-2500\",\"remainRes\":\"2000-2500\",\"unit\":\"03\",\"gprs_net_type\":\"2\",\"residualProportion\":\"100.00%\",\"serviceIdList\":\"1750000055\"}]}]}]},{\"planName\":\"新咪咕视频畅看套餐（标准版）【国内定向】\",\"planId\":\"gl_mgspckc_2018nbz\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"国内4G定向流量\",\"resourcesLeftInfo\":[{\"totalRes\":\"5000-5500\",\"remainRes\":\"5000-5500\",\"unit\":\"03\",\"gprs_net_type\":\"2\",\"residualProportion\":\"100.00%\",\"serviceIdList\":\"1030000004;1030000006;1750000006;1750000007;1710000020\"}]}]}]},{\"planName\":\"新咪咕视频畅看套餐_赠送25G咪咕定向流量【国内定向】\",\"planId\":\"gl_mgspzx_25Gll\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"secResourcesInfo\":[{\"secResourcesName\":\"国内4G定向流量\",\"resourcesLeftInfo\":[{\"totalRes\":\">20480\",\"remainRes\":\">20480\",\"unit\":\"03\",\"gprs_net_type\":\"2\",\"residualProportion\":\"100.00%\",\"serviceIdList\":\"1030000004;1030000006;1750000006;1750000007;1710000020\"}]}]}]}]}]}";
                CmbsStaticData staticDataUrl = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                        StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
                        StaticDataConst.StaticCodeName.FIRST_QUERY_USER_FLOW_URL);
                QueryUserPackageInfoRsp userPackageInfoRsp = firstAbilityFeignProxy.queryUserFlowInfo(expandMap,
                        new URI(staticDataUrl.getCodeValue()), platFormServiceReq.getServiceNumber(),
                        platFormServiceReq.getServiceType());
                // String entity = yjRestHttpclient.get(headerMap, url, proxySwtich,
                // proxyHost, proxyPort);
//             QueryUserPackageInfoRsp userPackageInfoRsp =
//             JsonUtil.stringToObject(entity,
//             QueryUserPackageInfoRsp.class);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("process  the method  flowUserInfo  the userPackageInfoRsp="
                            + JsonUtil.objectToString(userPackageInfoRsp));
                }
                if (IResultCode.YJ_PLATFORM_SUCCESS_CODE.equals(userPackageInfoRsp.getBizCode())) {
                    // 对响应封装-视讯结果
                    installFlowInfoResp(response, userPackageInfoRsp, maxResponse, productLine);
                    response.setResult(Result.success());
                    maxResponse.setResult(Result.success());
                    //缓存开关，等于1时打开
                    if (MktCampaignConst.Number.STRING_ONE.equals(cacheSwitch.getCodeValue())) {
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
                    }
                    } else {
                        response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, userPackageInfoRsp.getBizDesc()));
                    }

                } catch(Exception e){
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
                                                            // 含有视讯定向
                                                            if (StringUtils.isNotBlank(serviceid)
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

                                                            }
                                                        } else {

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
     *  modify by xuchen 20190219
     */
    private void confirmMinFlowResponse(QueryFlowInfoResp response, Float commontotalValue, Float commontotalLeftValue,
            Float dingXiangFlowAmt, Float dingXiangLeftValue, String queryType, boolean isDingXiang,
            boolean isMaxNoLimit, boolean isDingxiangMaxNoLimit) {
        if (CommonConst.YJPlatformConsts.MIN.equals(queryType)) {
            response.setTotalFlowAmt(String.valueOf(commontotalValue + dingXiangFlowAmt));
            response.setTotalLeftFlowAmt(String.valueOf(commontotalLeftValue + dingXiangLeftValue));
            response.setCommonFlowAmt(String.valueOf(commontotalValue));
            response.setCommonLeftFlowAmt(String.valueOf(commontotalLeftValue));
            response.setDingXiangFlowAmt(String.valueOf(dingXiangFlowAmt));
            response.setDingXiangLeftFlowAmt(String.valueOf(dingXiangLeftValue));
            // // 存在定向流量
            // if (isDingXiang) {
            // /**
            // * else 有定向非视讯
            // */
            // if (MktCampaignConst.Number.ZERO < dingXiangFlowAmt) {
            // // 有视讯的定向流量
            // hasVideoDingxiangFlowMin(response, commontotalValue, commontotalLeftValue, dingXiangFlowAmt,
            // dingXiangLeftValue);
            // }
            // } else {
            // // 不存在定向流量

            // }

        } else {

            //定向或通用无最大限制
            if (isMaxNoLimit || isDingxiangMaxNoLimit) {
                response.setTotalFlowAmt(CommonConst.RegExp.LINEAE);
                response.setTotalLeftFlowAmt(CommonConst.RegExp.LINEAE);
            } else {
                response.setTotalFlowAmt(String.valueOf(commontotalValue + dingXiangFlowAmt));
                response.setTotalLeftFlowAmt(String.valueOf(commontotalLeftValue + dingXiangLeftValue));
            }

            //定向无限制
            if (isDingxiangMaxNoLimit) {
                response.setDingXiangFlowAmt(CommonConst.RegExp.LINEAE);
                response.setDingXiangLeftFlowAmt(CommonConst.RegExp.LINEAE);
            } else {
                response.setDingXiangFlowAmt(String.valueOf(dingXiangFlowAmt));
                response.setDingXiangLeftFlowAmt(String.valueOf(dingXiangLeftValue));
            }
            // if (isDingXiang) {
            //
            // if (MktCampaignConst.Number.ZERO < dingXiangFlowAmt) {
            // // 有视讯的定向流量
            // hasVideoDingxiangVideoMax(response, commontotalValue, commontotalLeftValue, dingXiangFlowAmt,
            // dingXiangLeftValue, isMaxNoLimit, isDingxiangMaxNoLimit);
            // }
            //
            // } else {
            //通用无限制
            if (isMaxNoLimit) {
                response.setCommonFlowAmt(CommonConst.RegExp.LINEAE);
                response.setCommonLeftFlowAmt(CommonConst.RegExp.LINEAE);
            } else {
                response.setCommonFlowAmt(String.valueOf(commontotalValue));
                response.setCommonLeftFlowAmt(String.valueOf(commontotalLeftValue));

            }
            // }
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
}
