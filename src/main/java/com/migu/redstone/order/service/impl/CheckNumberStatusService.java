package com.migu.redstone.order.service.impl;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.client.dto.response.NumberStatusCheckResp;
import com.migu.redstone.client.fallback.BaseServiceFallback;
import com.migu.redstone.client.proxy.interfaces.IFirstAbilityFeignProxy;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.CheckNumberStatusReq;
import com.migu.redstone.order.service.dto.response.CheckNumberStatusResp;
import com.migu.redstone.order.service.interfaces.ICheckNumberStatusService;
import com.migu.redstone.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckNumberStatusService implements ICheckNumberStatusService {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckNumberStatusService.class);

    @Autowired
    private ICfgCommonService cfgCommonService;

    @Autowired
    private IFirstAbilityFeignProxy firstAbilityFeignProxy;

    @Override
    public CheckNumberStatusResp checkNumberStatus(CheckNumberStatusReq req) throws Exception {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug(">>> enter the mthod checkNumberStatus . req = " + JsonUtil.objectToString(req));
        }

        CheckNumberStatusResp resp = new CheckNumberStatusResp();

        try{
            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                    StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
                    StaticDataConst.StaticCodeName.FIRST_CHECK_NUMBER_STATUS_URL);
            /*NumberStatusCheckResp numberStatusCheckResp = firstAbilityFeignClient.checkNumberStatus(new URI(staticData.getCodeValue()),
                     req.getNumberType(), req.getNumber());*/
            Map<String, Object> expandMap = new HashMap<String, Object>();
            if(req.getNumberType().equals(MktCampaignConst.IOP.NUM_TYPE_ONE)){
                if(req.getNumber().length() != 11){
                    LOGGER.error("the method checkNumberStatus error ：手机号码不是11位!");
                    resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "手机号码不为11位!"));
                    return resp;
                }
                expandMap.put("MSISDN", req.getNumber());
            } else {
                expandMap.put("MSISDN", null);
            }
            NumberStatusCheckResp numberStatusCheckResp = firstAbilityFeignProxy.checkNumberStatus(expandMap,
                    new URI(staticData.getCodeValue()), req.getNumberType(), req.getNumber());
            if(BaseServiceFallback.FALLBACK_CODE.equals(numberStatusCheckResp.getBizCode())){
                resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, numberStatusCheckResp.getBizDesc()));
            } else {
                resp.setUpResultCode(numberStatusCheckResp.getBizCode());
                resp.setUpResultMsg(numberStatusCheckResp.getBizDesc());
            }
        } catch (Exception e) {
            LOGGER.error("the method checkNumberStatus error  e= " + e);
            resp.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, e.getMessage()));
        }
        return resp;
    }
}
