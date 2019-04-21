package com.migu.redstone.order.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.migu.redstone.client.FirstAbilityFeignClient;
import com.migu.redstone.common.dto.CheckBusinessQualificationResp;
import com.migu.redstone.common.dto.QualificationCheckReq;
import com.migu.redstone.common.dto.QualificationCheckRsp;
import com.migu.redstone.common.impl.QualificationService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;

@PrepareForTest(QualificationService.class)
@PowerMockIgnore("javax.management.*")
public class QualificationServiceTest {

	@InjectMocks
	private QualificationService qualificationService;

	@Mock
	private FirstAbilityFeignClient firstAbilityFeignClient;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void CheckBusinessQualificationRespSuccess() {
		QualificationCheckReq req = new QualificationCheckReq();
		req.setNumType(MktCampaignConst.Number.STRING_ONE);
		req.setGoodsId(  "11");
		req.setNumber("123123123");
		req.setServiceIdList("1");
		PowerMockito.when(firstAbilityFeignClient.queryQualification(anyObject(),anyString(), anyString(),
				anyString(), anyString())).thenReturn(new QualificationCheckRsp(IResultCode.OSPB_YJ_PLATFORM_RETURN_SUCCESS,""));		
		try {
			CheckBusinessQualificationResp resp = qualificationService.qualificationCheck(req);
			Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void CheckBusinessQualificationRespFail() {
	    QualificationCheckReq req = new QualificationCheckReq();
        req.setNumType(MktCampaignConst.Number.STRING_ONE);
        req.setGoodsId(  "11");
        req.setNumber("123123123");
        req.setServiceIdList("1");
		PowerMockito.when(firstAbilityFeignClient.queryQualification(anyObject(),anyString(), anyString(),
				anyString(), anyString())).thenReturn(new QualificationCheckRsp(IResultCode.OSPB_YJ_PLATFORM_NO_PRODUCTID,""));		
		try {
			CheckBusinessQualificationResp resp = qualificationService.qualificationCheck(req);
			Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.YJ_PLATFORM_ERROR_CODE);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void CheckBusinessQualificationRespException() {
	    QualificationCheckReq req = new QualificationCheckReq();
        req.setNumType(MktCampaignConst.Number.STRING_ONE);
        req.setGoodsId(  "11");
        req.setNumber("123123123");
        req.setServiceIdList("1");
		PowerMockito.when(firstAbilityFeignClient.queryQualification(anyObject(),anyString(), anyString(),
				anyString(), anyString())).thenThrow(new Exception("抛出异常"));
		try {
			CheckBusinessQualificationResp resp = qualificationService.qualificationCheck(req);
		} catch (Exception e) {
		}
	}

}
