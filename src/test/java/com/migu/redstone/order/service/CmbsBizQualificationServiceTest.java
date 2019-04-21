package com.migu.redstone.order.service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.client.CmbsCommonHttpClient;
import com.migu.redstone.client.MiguVideoFeignClient;
import com.migu.redstone.client.dto.response.RulesWorkerCheckRsp;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.request.RulesWorkerCheckReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;
import com.migu.redstone.order.service.impl.CmbsBizQualificationService;
import com.migu.redstone.utils.CheckEnumUtil;
import com.migu.redstone.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URI;
import java.util.Date;

import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CmbsBizQualificationService.class, JsonUtil.class, CheckEnumUtil.class, StringUtils.class})
@PowerMockIgnore("javax.management.*")
public class CmbsBizQualificationServiceTest {
    @InjectMocks
    private CmbsBizQualificationService cmbsBizQualificationService;
    @Mock
    private CmbsCommonHttpClient cmbsCommonHttpClient;
    @Mock
    private MiguVideoFeignClient miguVideoBizQualificationFeignClient;
    @Mock
    private ICfgCommonService cfgCommonService;
    @Test
    public void testCmbsBizQualificationServiceSuccess() throws Exception {
        RulesWorkerCheckReq req = new RulesWorkerCheckReq("01","01");
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.when(JsonUtil.objectToString(req)).thenReturn("{ \"phoneNumber\": 13795274234, \"merchandiseld\": 01 }");
        CmbsStaticData clientIdData = new CmbsStaticData("11111111","1",new Date(),new Date(),1,"1","1");
        PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO,
            StaticDataConst.StaticCodeName.CLIENT_ID)).thenReturn(clientIdData);
        PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.CHECK_BIZE_QUALIFICATION_URL)).thenReturn(clientIdData);
        PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.MIGU_VIDEO_KEY, StaticDataConst.StaticCodeName.MIGU_VIDEO_PRIVATE_KEY)).thenReturn(clientIdData);

        PowerMockito.when(cmbsCommonHttpClient.processSign(anyString(), anyString())).thenReturn("123");
        PowerMockito.when(miguVideoBizQualificationFeignClient.checBizQualification(new URI(anyString()), anyString(), anyString(), anyString()))
            .thenReturn(new RulesWorkerCheckRsp("success", "error", ""));
        CmbsBizQualificationReq req1 = new CmbsBizQualificationReq("01","01","13795274234",null);
        CmbsBizQualificationRsp response = cmbsBizQualificationService.processBiz(req1, "01");
        Assert.assertEquals(response.getBizCode(), IResultCode.ABILITY_BIZ_QUALIFICATION_SUCCESS_CODE);
    }

    @Test
    public void testCmbsBizQualificationServiceFail() throws Exception {
        RulesWorkerCheckReq req = new RulesWorkerCheckReq("01","01");
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.when(JsonUtil.objectToString(req)).thenReturn("{ \"phoneNumber\": 13795274234, \"merchandiseld\": 01 }");
        CmbsStaticData clientIdData = new CmbsStaticData("11111111","1",new Date(),new Date(),1,"1","1");
        PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO,
            StaticDataConst.StaticCodeName.CLIENT_ID)).thenReturn(clientIdData);
        PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.CHECK_BIZE_QUALIFICATION_URL)).thenReturn(clientIdData);
        PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.MIGU_VIDEO_KEY, StaticDataConst.StaticCodeName.MIGU_VIDEO_PRIVATE_KEY)).thenReturn(clientIdData);

        PowerMockito.when(cmbsCommonHttpClient.processSign(anyString(), anyString())).thenReturn("123");
        PowerMockito.when(miguVideoBizQualificationFeignClient.checBizQualification(new URI(anyString()), anyString(), anyString(), anyString()))
            .thenReturn(new RulesWorkerCheckRsp("fail", "error", "error"));
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.when(!StringUtils.equalsIgnoreCase(IResultCode.SUCCESS_MESSAGE, "success")).thenReturn(false);
        CmbsBizQualificationReq req1 = new CmbsBizQualificationReq("01","01","13795274234",null);
        CmbsBizQualificationRsp response = cmbsBizQualificationService.processBiz(req1, "01");
        Assert.assertEquals(response.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }
}
