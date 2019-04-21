/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.client.GdServerFeignClient;
import com.migu.redstone.client.dto.model.GdQueryUserInfo;
import com.migu.redstone.client.dto.model.ServiceInfo;
import com.migu.redstone.client.dto.request.QueryFlowReq;
import com.migu.redstone.client.dto.request.QueryReq;
import com.migu.redstone.client.dto.response.GdQueryErrorRes;
import com.migu.redstone.client.proxy.interfaces.IGdServerFeignProxy;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.response.QueryResp;
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;
import com.migu.redstone.utils.JsonUtil; 

/**
 *
 * 广东相关服务查询功能接口
 * 
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.interfaces
 * @类名称 ICmbsGDQueryService
 * @类描述 广东相关服务查询功能接口
 * @创建人 xuhcen
 * @创建时间 2018年11月16日 下午2:36:09
 */
@Service
public class CmbsGDQueryService implements ICmbsGDQueryService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsGDQueryService.class);

    /**
     * cfgCommonService
     */
    @Autowired
    private ICfgCommonService cfgCommonService;

    @Autowired
    private IGdServerFeignProxy gdServerFeignProxy;

    /**
     *
     * 〈查询账户余额〉 〈查询账户余额〉
     * 
     * @param req
     * @return
     */
    @Override
    public String queryAccountsBalance(QueryGdServiceReq req) throws Exception {

        if (LOG.isDebugEnabled()) {
            LOG.debug("enter the method queryAccountsBalance. req=" + JsonUtil.objectToString(req));
        }
        try {
        	Map<String, Object> expandMap = new HashMap<String, Object>();
        	expandMap.put("MSISDN", req.getPhoneNumber());
            QueryReq queryReq = new QueryReq();
            // 请求body
            ServiceInfo serviceinfo = new ServiceInfo();
            serviceinfo.setId(MktCampaignConst.GDApplicationService.ACCOUNTS_BALANCE_SEARCH);
            GdQueryUserInfo userinfo = new GdQueryUserInfo();
            userinfo.setServernum(req.getPhoneNumber());
            queryReq.setUserinfo(userinfo);
            queryReq.setServiceinfo(serviceinfo);
            // String body = JsonUtil.objectToString(queryReq);
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.GDPLATFORM, StaticDataConst.StaticCodeName.GD_QUERY_ACCOUNTSBALANCE_URL);
            //            String url="https://221.179.11.204:443/eaop/rest/BSS/bill/query/v1.1.1";
            String entity = gdServerFeignProxy.queryAccountsBalanceGD(expandMap, new URI(staticData.getCodeValue()) ,queryReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug(">>>>> the method queryAccountsBalance entity=" + entity);
            }
            if (StringUtils.isNotBlank(entity)) {
                return entity;
            }
        } catch (Exception e) {
            LOG.error(">>>>> process the method queryAccountsBalance error e=" + e);
            GdQueryErrorRes accountBalanceRes = new GdQueryErrorRes();
            // RetInfo retinfo = new RetInfo();
            accountBalanceRes.setResptype("1000030016");
            accountBalanceRes.setRespcode("1000030016");
            accountBalanceRes.setRespdesc(e.getMessage());
            String entity = JsonUtil.objectToString(accountBalanceRes);
            GdQueryErrorRes userPackageInfoRsp = JsonUtil.stringToObject(entity,
                    GdQueryErrorRes.class); 
            return entity;
        }
        return null;

    }

    /**
     *
     * 〈查询短号群聊网网内现有成员〉 〈查询短号群聊网网内现有成员〉
     * 
     * @param req
     * @return
     */
    @Override
    public String queryDHQLWMembers(QueryGdServiceReq req) throws Exception {

        if (LOG.isDebugEnabled()) {
            LOG.debug("enter the method queryDHQLWMembers. req=" + JsonUtil.objectToString(req));
        }
        Map<String, Object> expandMap = new HashMap<String, Object>();
        expandMap.put("MSISDN", req.getPhoneNumber());
        // 入参校验
        // **广东省份月查询请求
        QueryReq queryReq = new QueryReq();
        // 请求body
        ServiceInfo serviceinfo = new ServiceInfo();
        serviceinfo.setId(MktCampaignConst.GDApplicationService.FAMILY_TALKING);
        GdQueryUserInfo userinfo = new GdQueryUserInfo();
        userinfo.setServernum(req.getPhoneNumber());
        queryReq.setServiceinfo(serviceinfo);
        queryReq.setUserinfo(userinfo);
        QueryResp resp = new QueryResp();
        try {
            //            String body = JsonUtil.objectToString(queryReq);
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.GDPLATFORM, StaticDataConst.StaticCodeName.GD_QUERY_DHQLWMEMBERS_URL);
            String entity = gdServerFeignProxy.queryDHQLWMembersGd(expandMap, new URI(staticData.getCodeValue()),queryReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug(">>>>> the method queryDHQLWMembers gdServerFeignClient  entity=" + entity);
            }
            if (StringUtils.isNotBlank(entity)) {

                return entity;
            }
        } catch (Exception e) {
            LOG.error(">>>>> process the method queryDHQLWMembers error e=" + e);
            resp.setResptype("600");
            resp.setRespcode("1000030016");
            resp.setRespdesc(e.getMessage());
            String entity = JsonUtil.objectToString(resp);
            return entity;
        }

        return null;
    }

    /**
     * 广东查询全流量
     */
    @Override
    public String queryAllFlow(QueryGdServiceReq req) throws Exception {

        if (LOG.isDebugEnabled()) {
            LOG.debug("enter the method queryAllFlow. req=" + JsonUtil.objectToString(req));
        }
        Map<String, Object> expandMap = new HashMap<String, Object>();
        expandMap.put("MSISDN", req.getPhoneNumber());
        // // 入参校验
        // **广东省份月查询请求
        QueryFlowReq queryReq = new QueryFlowReq();
        queryReq.setMobileno(req.getPhoneNumber());

        QueryResp resp = new QueryResp();
        try {
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.GDPLATFORM, StaticDataConst.StaticCodeName.GD_QUERY_ALLFLOW_URL);
            String entity = gdServerFeignProxy.queryAllFlow(expandMap, new URI(staticData.getCodeValue()),queryReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug(">>>>> end the method queryAllFlow. 响应 rsp = " + entity);
            }
            if (StringUtils.isNotBlank(entity)) {
                // JSONObject jsonObject=JSONObject.parseObject(entity);
                return entity;
            }
        } catch (Exception e) {
            LOG.error(">>>>> process the method queryAccountsBalance error e= =" + e);
            resp.setResptype("600");
            resp.setRespcode(IResultCode.CMBS_SYS_ERROR);
            resp.setRespdesc(e.getMessage());
            String entity = JsonUtil.objectToString(resp);
            return entity;
        }
        return null;
    }

}
