package com.migu.redstone.order.service.impl;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.client.dto.model.FirstIOPRequest;
import com.migu.redstone.client.dto.model.FirstIOPRequestHead;
import com.migu.redstone.client.dto.model.FirstIOPRequestUserInfo;
import com.migu.redstone.client.dto.model.FirstIOPResponseUserInfo;
import com.migu.redstone.client.dto.request.QueryFamilyNetGroupRequest;
import com.migu.redstone.client.dto.response.QueryFamilyNetGroupResponse;
import com.migu.redstone.client.proxy.interfaces.IFirstIOPFeginProxy;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.FamilyNetGroup;
import com.migu.redstone.order.service.dto.request.QueryFamilyNetGroupReq;
import com.migu.redstone.order.service.dto.response.QueryFamilyNetGroupResp;
import com.migu.redstone.order.service.interfaces.IQueryFamilyNetGroupService;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

@Service
public class QueryFamilyNetGroupService implements IQueryFamilyNetGroupService {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryFamilyNetGroupService.class);

    @Autowired
    private ICfgCommonService cfgCommonService;

    @Autowired
    private IFirstIOPFeginProxy firstIOPFeginProxy;

    @Override
    public QueryFamilyNetGroupResp queryFamilyNetGroup(QueryFamilyNetGroupReq req) throws Exception {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug(">>>>> enter the method  queeryFamilyNetGroup . req=" + JsonUtil.objectToString(req));
        }
        // 获取一级IOP请求所需的staticDate
        String secretId = "";
        String serviceCode = "";
        String requestRefId = "";
        String uri = "";
        List<CmbsStaticData> cmbsStaticDataList = cfgCommonService.getCmbsStaticDataByCodeType(StaticDataConst.StaticCodeType.FIRSTIOP_FEIGNCLIENT);
        if(CommonUtil.isNotEmptyCollection(cmbsStaticDataList)){
            for (CmbsStaticData cmbsStaticDate : cmbsStaticDataList) {
                if(StaticDataConst.StaticCodeName.FIRSTIOP_SECRET_ID.equals(cmbsStaticDate.getCodeName())){
                    secretId = cmbsStaticDate.getCodeValue();
                } else if(StaticDataConst.StaticCodeName.FIRSTIOP_SERVICE_CODE.equals(cmbsStaticDate.getCodeName())){
                    serviceCode = cmbsStaticDate.getCodeValue();
                } else if(StaticDataConst.StaticCodeName.FIRSTIOP_REQUEST_REF_ID.equals(cmbsStaticDate.getCodeName())){
                    requestRefId = cmbsStaticDate.getCodeValue();
                } else if(StaticDataConst.StaticCodeName.FIRSTIOP_QUERY_FAMILY_NET_GROUP_URL.equals(cmbsStaticDate.getCodeName())){
                    uri = cmbsStaticDate.getCodeValue();
                }
            }
        }
        // 视讯response
        QueryFamilyNetGroupResp resp = new QueryFamilyNetGroupResp();
        // 一级iop request
        QueryFamilyNetGroupRequest queryFamilyNetGroupRequest = new QueryFamilyNetGroupRequest();
        // 一级iop response
        QueryFamilyNetGroupResponse queryFamilyNetGroupResp = new QueryFamilyNetGroupResponse();
        // 请求头head
        FirstIOPRequestHead requestHead = new FirstIOPRequestHead();
        requestHead.setSecretId(secretId);
        requestHead.setServiceCode(serviceCode);
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,25);
        requestHead.setRequestRefId(requestRefId + uuid);
        // 请求参数request
        FirstIOPRequest request = new FirstIOPRequest();
        FirstIOPRequestUserInfo requestUserInfo = new FirstIOPRequestUserInfo();
        requestUserInfo.setServNum(req.getServiceNum());
        requestUserInfo.setEmpowerNo(req.getEmpowerNo());
        List<FirstIOPRequestUserInfo> userInfos = new ArrayList<>();
        userInfos.add(requestUserInfo);
        request.setUserInfos(userInfos);

        queryFamilyNetGroupRequest.setHead(requestHead);
        queryFamilyNetGroupRequest.setRequest(request);

        try{
            //queryFamilyNetGroupResp =  firstIOPFeignClient.queryFamilyNetGroup(new URI(uri),queryFamilyNetGroupRequest);
            Map<String, Object> expandMap = new HashMap<String, Object>();
            expandMap.put("MSISDN", req.getServiceNum());
            queryFamilyNetGroupResp = firstIOPFeginProxy.queryFamilyNetGroup(expandMap, new URI(uri),queryFamilyNetGroupRequest);
            if(CommonUtil.isNotNull(queryFamilyNetGroupResp)){
                if(CommonUtil.isNotNull(queryFamilyNetGroupResp.getHead())){
                    String responseCode = queryFamilyNetGroupResp.getHead().getResponseCode();
                    if(MktCampaignConst.IOP.SUCCESS_CODE.equals(responseCode)){
                        if(CommonUtil.isNotNull(queryFamilyNetGroupResp.getResponse())){
                            List<FamilyNetGroup> familyNetGroupList = new ArrayList<>();
                            List<FirstIOPResponseUserInfo> userInfoList = queryFamilyNetGroupResp.getResponse().getUserInfos();
                            if(CommonUtil.isNotEmptyCollection(userInfoList)){
                                for (FirstIOPResponseUserInfo responseUserInfo : userInfoList) {
                                    String serviceNum = responseUserInfo.getServNum();
                                    String familyNum = responseUserInfo.getFamilyNum();
                                    FamilyNetGroup familyNetGroup = new FamilyNetGroup();
                                    familyNetGroup.setFamilyNum(familyNum);
                                    familyNetGroup.setServiceNum(serviceNum);
                                    familyNetGroupList.add(familyNetGroup);
                                }
                                resp.setFamilyNetGroups(familyNetGroupList);
                                return resp;
                            }
                        }
                    } else {
                        String respCode = queryFamilyNetGroupResp.getHead().getResponseCode();
                        String responseMsg = queryFamilyNetGroupResp.getHead().getResponseMsg();
                        resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, respCode + responseMsg));
                        return resp;
                    }
                }
            } else {
                LOGGER.error(">>>>> enter the method  queryFamilyMemberInfo : 无亲情网信息!");
                resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "无亲情网信息"));
                return resp;
            }
        } catch(Exception e) {
            LOGGER.error(">>>>> enter the method queryFamilyNetGroup : e = " + e.getMessage());
            resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, e.getMessage()));
        }
        return resp;
    }
}
