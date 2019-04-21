package com.migu.redstone.order.service;

import static org.mockito.Matchers.anyObject;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.migu.redstone.client.GdServerFeignClient;
import com.migu.redstone.client.dto.response.GdQueryErrorRes;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.excetion.BusinessException;
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.response.QueryResp;
import com.migu.redstone.order.service.impl.CmbsGDQueryService;
import com.migu.redstone.order.service.impl.CmbsVideoCustomService;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.DateCommonUtil;
import com.migu.redstone.utils.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CmbsVideoCustomService.class, DateCommonUtil.class, CommonUtil.class })
@PowerMockIgnore("javax.management.*")
public class CmbsGDQueryServiceTest {

	@Mock
	private GdServerFeignClient gdServerFeignClient;
	
	@InjectMocks
	private CmbsGDQueryService gdQueryService;
	
	@Test
	public void testQueryAccountsBalanceSuccess() throws Exception {
		QueryGdServiceReq req = new QueryGdServiceReq(null,"15651928301","1","1","1","1","1");
        PowerMockito.when(gdServerFeignClient.queryAccountsBalanceGD(null,anyObject())).thenReturn("Success");
        String entity = gdQueryService.queryAccountsBalance(req);
        Assert.assertEquals(entity, "Success");
	}
	@Rule
	ExpectedException ex = ExpectedException.none();
	
	@Test
	public void testQueryAccountsBalanceFail() throws Exception {
		QueryGdServiceReq req = new QueryGdServiceReq(null,"15651928301","1","1","1","1","1");
//		ex.expect(BusinessException.class);
//		ex.expectMessage("营销短信任务获取缓存失败");
//		PowerMockito.when(gdServerFeignClient.queryAccountsBalanceGD(anyObject(),anyObject())).thenThrow(new Exception("mock Exception"));
		PowerMockito.doThrow(new Exception("mock Exception")).when(gdServerFeignClient).queryAccountsBalanceGD(anyObject(),anyObject());
		String entity = gdQueryService.queryAccountsBalance(req);
        GdQueryErrorRes userPackageInfoRsp = JsonUtil.stringToObject(entity, GdQueryErrorRes.class); 
        Assert.assertEquals(userPackageInfoRsp.getRespcode(), "1000030016");
		
	}
	
	@Test
	public void testQueryDHQLWMembersSuccess() throws Exception {
		QueryGdServiceReq req = new QueryGdServiceReq(null,"13695647856","1","1","1","1","1");
        PowerMockito.when(gdServerFeignClient.queryDHQLWMembersGd(null,anyObject())).thenReturn("Success");
        String entity = gdQueryService.queryDHQLWMembers(req);
        Assert.assertEquals(entity, "Success");
	}
	
	@Test
	public void testQueryDHQLWMembersFail() throws Exception {
		PowerMockito.when(gdServerFeignClient.queryDHQLWMembersGd(null,anyObject())).thenThrow(new Exception("mock exception"));
		String entity = gdQueryService.queryDHQLWMembers(null);
		QueryResp queryResp = JsonUtil.stringToObject(entity,QueryResp.class); 
        Assert.assertEquals(queryResp.getRespcode(), "1000030016");
	}
	
	@Test
	public void testQueryAllFlowSuccess() throws Exception {
		QueryGdServiceReq req = new QueryGdServiceReq(null,"13695647856","1","1","1","1","1");
	    PowerMockito.when(gdServerFeignClient.queryAllFlow(null,anyObject())).thenReturn("Success");
		String entity = gdQueryService.queryAllFlow(req);
        Assert.assertEquals(entity, "Success");

	}
	
	@Test
	public void testQueryAllFlowFail() throws Exception {
		PowerMockito.when(gdServerFeignClient.queryAllFlow(null,anyObject())).thenThrow(new Exception("mock exception"));
		String entity = gdServerFeignClient.queryAllFlow(null,null);
		QueryResp queryResp = JsonUtil.stringToObject(entity,QueryResp.class); 
        Assert.assertEquals(queryResp.getRespcode(), IResultCode.CMBS_SYS_ERROR);
	}
	
}
