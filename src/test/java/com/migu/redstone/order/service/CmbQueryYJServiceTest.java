package com.migu.redstone.order.service;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.net.URI;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.cfg.product.mapper.po.CmbsProdChannelAbilityCfg;
import com.migu.redstone.cfg.product.service.interfaces.ICmbsProdService;
import com.migu.redstone.client.GdServerFeignClient;
import com.migu.redstone.client.dto.response.EncsMsisdnSectionRes;
import com.migu.redstone.client.service.interfaces.IRestService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.dto.Header;
import com.migu.redstone.order.service.dto.request.QueryChargeBalanceReq;
import com.migu.redstone.order.service.dto.request.QueryFamilyMemberInfoReq;
import com.migu.redstone.order.service.dto.request.QueryFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.response.QueryChargeBalanceResp;
import com.migu.redstone.order.service.dto.response.QueryFamilyMemberInfoResp;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.impl.CmbQueryYJService;
import com.migu.redstone.order.service.impl.QueryGdFlowService;
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJPlatFormService;
import com.migu.redstone.utils.SpringContextUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CmbQueryYJService.class, SpringContextUtil.class })
@PowerMockIgnore("javax.management.*")
public class CmbQueryYJServiceTest {
	@InjectMocks
	private CmbQueryYJService cmbQueryYJService;

	@Mock
	private IRestService restService;
	@Mock
	private ICfgCommonService cfgCommonService;
	@Mock
	private ICmbsProdService cmbsProdService;
	@Mock
	private QueryGdFlowService queryGdFlowService;
	@Mock
	private ICmbsGDQueryService cmbsGDQueryService;
	@Mock
	private ICmbsQueryYJPlatFormService cmbsQueryYJPlatFormService;
	@Mock
	private GdServerFeignClient gdServerFeignClient;

	@Test
	public void testQueryFlowInfoSuccess() throws Exception {
		QueryFlowInfoReq req = new QueryFlowInfoReq(null, "13795274234", "01");
		PowerMockito.when(restService.getMsisdnSection(req.getPhoneNumber()))
				.thenReturn(new EncsMsisdnSectionRes("250", "1", "01", "01", "01", null));
		PowerMockito
				.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
						StaticDataConst.StaticCodeType.QUERY_FLOW_PRODUCT,
						StaticDataConst.StaticCodeName.QUERY_FLOW_PRODUCT_CODE_NAME))
				.thenReturn(new CmbsStaticData("01", "01", new Date(), new Date(), 1, "01", "01"));
		PowerMockito
				.when(cmbsProdService.getCmbsProdChannelAbilityCfgByProductIdAndChannelAndType(anyLong(), anyString(),
						anyString()))
				.thenReturn(new CmbsProdChannelAbilityCfg(Long.parseLong("01"), Long.parseLong("01"), "01", "250", "01",
						"01", "1", new Date(), new Date(), "01", "01"));
		// IQueryFlowService queryFlowService = (IQueryFlowService)
		// SpringContextUtil.getBean(processName);
		// flowInfoResp = queryFlowService.queryFlow(flowInfoReq);
		PowerMockito.mockStatic(SpringContextUtil.class);
		PowerMockito.when(SpringContextUtil.getBean(anyString())).thenReturn(queryGdFlowService);
		PowerMockito.when(queryGdFlowService.queryFlow(req,"")).thenReturn(new QueryFlowInfoResp());
		QueryFlowInfoResp resp = cmbQueryYJService.queryFlowInfo(req);
		Assert.assertEquals(resp.getResult().getResultCode(), "8000000000");
	}

	@Test
	public void testQueryFlowInfoFail() throws Exception {
		QueryFlowInfoReq req = new QueryFlowInfoReq(null, "13795274234", "01");
		PowerMockito.when(restService.getMsisdnSection(req.getPhoneNumber()))
				.thenReturn(new EncsMsisdnSectionRes("250", "1", "01", "01", "01", null));
		PowerMockito
				.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
						StaticDataConst.StaticCodeType.QUERY_FLOW_PRODUCT,
						StaticDataConst.StaticCodeName.QUERY_FLOW_PRODUCT_CODE_NAME))
				.thenReturn(new CmbsStaticData("01", "01", new Date(), new Date(), 1, "01", "01"));
		PowerMockito
				.when(cmbsProdService.getCmbsProdChannelAbilityCfgByProductIdAndChannelAndType(anyLong(), anyString(),
						anyString()))
				.thenReturn(new CmbsProdChannelAbilityCfg(Long.parseLong("01"), Long.parseLong("01"), "01", "250", "01",
						"", "1", new Date(), new Date(), "01", "01"));
		// IQueryFlowService queryFlowService = (IQueryFlowService)
		// SpringContextUtil.getBean(processName);
		// flowInfoResp = queryFlowService.queryFlow(flowInfoReq);
		PowerMockito.mockStatic(SpringContextUtil.class);
		PowerMockito.when(SpringContextUtil.getBean(anyString())).thenReturn(queryGdFlowService);
		PowerMockito.when(queryGdFlowService.queryFlow(req,"")).thenReturn(new QueryFlowInfoResp());
		QueryFlowInfoResp resp = cmbQueryYJService.queryFlowInfo(req);
		Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE);
	}

	@Test
	public void testQueryChargeBalanceSuccess() throws Exception {
		QueryChargeBalanceReq req = new QueryChargeBalanceReq(new Header("1.0", "01", "01", "01", "01", "03", "01",
				"01", "03", "01", "01", "01", "11111111", "01", "01", "01"), "15651928301");
		PowerMockito.when(restService.getMsisdnSection(req.getPhoneNumber()))
				.thenReturn(new EncsMsisdnSectionRes("200", null, null, null, null, null));
		PowerMockito
				.when(cmbsGDQueryService
						.queryAccountsBalance(new QueryGdServiceReq(null, null, null, null, null, null, null)))
				.thenReturn("123");
		QueryChargeBalanceReq request = new QueryChargeBalanceReq(null, "13795274234");
		QueryChargeBalanceResp rsp = cmbQueryYJService.queryChargeBalance(req);
		Assert.assertEquals(rsp.getResult().getResultMessage(), "success");
	}

	@Test
	public void testQueryChargeBalanceFail() throws Exception {
		QueryChargeBalanceReq req = new QueryChargeBalanceReq(new Header(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null), null);
		PowerMockito.when(restService.getMsisdnSection(req.getPhoneNumber()))
				.thenReturn(new EncsMsisdnSectionRes("200", null, null, null, null, null));
		PowerMockito
				.when(cmbsGDQueryService
						.queryAccountsBalance(new QueryGdServiceReq(null, null, null, null, null, null, null)))
				.thenReturn("123");
		PowerMockito.when(gdServerFeignClient.queryAccountsBalanceGD(new URI(anyString()), anyObject()))
				.thenReturn("123");

		PowerMockito.when(cmbsGDQueryService.queryAccountsBalance(anyObject()))
				.thenReturn("{ " + "\"respcode\": \"0\", \"resptype\":\"0\", \"result\":\"<a><b><c>c</c></b></a>\"}");
		QueryChargeBalanceResp rsp = cmbQueryYJService.queryChargeBalance(req);
		Assert.assertEquals(rsp.getResult().getResultMessage(), "success");
	}

	@Test
	public void testQueryFamilyMemberInfoSuccess() throws Exception {
		QueryFamilyMemberInfoReq req = new QueryFamilyMemberInfoReq(new Header("1.0", "01", "01", "01", "01", "03",
				"01", "01", "03", "01", "01", "01", "01", "01", "01", "01"), "13795274234");
		PowerMockito.when(cmbsGDQueryService.queryDHQLWMembers(anyObject()))
				.thenReturn("{\"respcode\":\"0\",\"respdesc\":\"未申请此业务\","
						+ "\"resptype\":\"0\",\"result\":{\"list\":{\"shortnumbermemberdetailinfomation\": [{\"shortnumber\":\"1\", "
						+ "\"number\":\"1\", \"effecttime\":\"1\", \"invalidtime\":\"1\"}]}}}");
		QueryFamilyMemberInfoResp rsp = cmbQueryYJService.queryFamilyMemberInfo(req);
		Assert.assertEquals(rsp.getResult().getResultMessage(), "success");
	}

	@Test
	public void testQueryFamilyMemberInfoFail() throws Exception {
		QueryFamilyMemberInfoReq req = new QueryFamilyMemberInfoReq(new Header("1.0", "01", "01", "01", "01", "03",
				"01", "01", "03", "01", "01", "01", "01", "01", "01", "01"), "13795274234");
		PowerMockito.when(cmbsGDQueryService.queryDHQLWMembers(anyObject())).thenReturn(
				"{\"respcode\":\"1\",\"respdesc\":\"未申请此业务\",\"resptype\":\"600\",\"result\":{\"list\":{}}}");
		QueryFamilyMemberInfoResp rsp = cmbQueryYJService.queryFamilyMemberInfo(req);
		Assert.assertEquals(rsp.getResult().getResultMessage(), "未申请此业务");
	}
}
