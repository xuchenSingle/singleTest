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
import com.migu.redstone.client.CmbsCommonHttpClient;
import com.migu.redstone.client.MiguVideoFeignClient;
import com.migu.redstone.client.dto.response.RulesWorkerCheckRsp;
import com.migu.redstone.client.proxy.interfaces.IMiguVideoFeignProxy;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.request.RulesWorkerCheckReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryVideoDispatcherService;
import com.migu.redstone.utils.CheckEnumUtil;
import com.migu.redstone.utils.JsonUtil;

/**
* 类作用:    一级能开调用视讯-业务办理资格校验service实现类
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.impl
* 类名称:    CmbsBizQualificationService
* 类描述:    一级能开调用视讯-业务办理资格校验service实现类
* 创建人:    jianghao
* 创建时间:   2018年11月30日 下午1:16:36
*/
@Service("cmbsBizQualificationService")
public class CmbsBizQualificationService implements ICmbsQueryVideoDispatcherService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsBizQualificationService.class);

    /**
     * cmbsCommonHttpClient
     */
    @Autowired
    private CmbsCommonHttpClient cmbsCommonHttpClient;

    /**
     * miguVideoBizQualificationFeignClient.
     */
    @Autowired
    private IMiguVideoFeignProxy miguVideoFeignProxy;

    /**
     * cfgCommonService.
     */
    @Autowired
    private ICfgCommonService cfgCommonService;

    /**
     *<checkBizQualification>.
     *<一级能开业务办理资格校验>
     * @param  request   [request]
     * @param  productCode   [productCode]
     * @return [返回校验结果 ]
     * @exception/throws [违例类型] [Exception]
     * @author jianghao
     */
    @Override
    public CmbsBizQualificationRsp processBiz(CmbsBizQualificationReq request, String productCode) throws Exception {
        //设置请求参数
        String msisdn = request.getNumberInfo();
        if (msisdn.length() > 11) {
            msisdn = msisdn.substring(msisdn.length() - 11,
                    msisdn.length());
        }
        Map<String, Object> expandMap = new HashMap<String, Object>();
        expandMap.put("MSISDN", msisdn);
        RulesWorkerCheckReq rulesWorkerCheckReq = new RulesWorkerCheckReq();
        rulesWorkerCheckReq.setMerchandiseId(productCode);
        rulesWorkerCheckReq.setPhoneNumber(msisdn);
        String reqJsonStr = JsonUtil.objectToString(rulesWorkerCheckReq);

        String signPrivatekey = "";
        String clientId = "";
        String uriStr = "";
        CmbsStaticData clientIdData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.CLIENT_ID);
        CmbsStaticData checkBizQualificationData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.CHECK_BIZE_QUALIFICATION_URL);
        CmbsStaticData privateKeyData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.MIGU_VIDEO_KEY, StaticDataConst.StaticCodeName.MIGU_VIDEO_PRIVATE_KEY);
        if (clientIdData != null) {
            clientId = clientIdData.getCodeValue();
        }
        if (checkBizQualificationData != null) {
            uriStr = checkBizQualificationData.getCodeValue();
        }
        if (privateKeyData != null) {
            signPrivatekey = privateKeyData.getCodeValue();
        }
        String signStr = cmbsCommonHttpClient.processSign(reqJsonStr, signPrivatekey);
        URI uri = new URI(uriStr);

        CmbsBizQualificationRsp response = new CmbsBizQualificationRsp();
        try {
            //远程调用
            RulesWorkerCheckRsp rulesWorkerCheckRsp
                = miguVideoFeignProxy.checBizQualification(expandMap, uri, clientId, signStr, reqJsonStr);
            LOG.error("CmbsBizQualificationService.checkBizQualification,"
                + " responseStr=" + JsonUtil.objectToString(rulesWorkerCheckRsp) + " ,uri=" + uriStr
                + " ,clientId=" + clientId + " ,sign=" + signStr + " ,request=" + reqJsonStr);
             //设置response
            String resultCode = rulesWorkerCheckRsp.getResultCode();
            String errorCode = rulesWorkerCheckRsp.getErrorCode();
            String resultDesc = rulesWorkerCheckRsp.getResultDesc();
            if (IResultCode.SUCCESS_MESSAGE.equalsIgnoreCase(resultCode)) {
                response.setBizCode(IResultCode.ABILITY_BIZ_QUALIFICATION_SUCCESS_CODE);
                response.setBizDesc(resultDesc);
            }
            else {
                response.setBizCode(resultCode);
                response.setBizDesc(resultDesc);
            }
            if (StringUtils.isNotBlank(errorCode)) {
                response.setBizCode(errorCode);
                response.setBizDesc(resultDesc);
            }
            if (!CheckEnumUtil.checkBizQualificationRsp(response.getBizCode())) {
                response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            }
        }
        catch (Exception e) {
            LOG.error("CmbsBizQualificationService.checkBizQualification error,"
                + " errorMessage=" + e.getMessage(), e);
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开调视讯业务资格校验远程调用失败");
        }
        return response;
    }
}
