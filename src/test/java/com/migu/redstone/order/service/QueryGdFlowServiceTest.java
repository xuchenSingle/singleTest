package com.migu.redstone.order.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.QueryFlowInfoReq;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.impl.QueryGdFlowService;
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;
import com.migu.redstone.redis.RedisTemplate;

import net.sf.json.JSONObject;

@PrepareForTest(QueryGdFlowService.class)
@PowerMockIgnore("javax.management.*")

public class QueryGdFlowServiceTest {
 
	@InjectMocks
	private QueryGdFlowService queryGdFlowService;

	@Mock
	private RedisTemplate redisTemplate;

	@Mock
	private ICmbsGDQueryService cmbsGDQueryService;

	@Mock
	private ICfgCommonService cfgCommonService;
	


	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void QueryFlowStaticDataIsNull() {
		PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
				StaticDataConst.StaticCodeName.FLOW_EXPIRE_TYPE, StaticDataConst.StaticCodeName.FLOW_EXPIRE_NAME))
				.thenReturn(null);
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}

	@Test
	public void QueryFlowStaticDataIsNotNull() {
		CmbsStaticData cmbsStaticData = new CmbsStaticData();
		cmbsStaticData.setCodeValue("11");
		PowerMockito.when(cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
				StaticDataConst.StaticCodeName.FLOW_EXPIRE_TYPE, StaticDataConst.StaticCodeName.FLOW_EXPIRE_NAME))
				.thenReturn(cmbsStaticData);
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}

	@Test
	public void QueryFlowRedisKeyIsNotNullResponseNotNull() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		PowerMockito.when(redisTemplate.exists(anyString())).thenReturn(true);
		QueryFlowInfoResp rsp = new QueryFlowInfoResp();
		rsp.setResult(new Result());
		rsp.setCommonFlowAmt("098908");
		PowerMockito.when(redisTemplate.get(anyObject(), anyObject())).thenReturn(rsp);
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}

	@Test
	public void QueryFlowRedisKeyIsNotNullResponseNull() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		PowerMockito.when(redisTemplate.exists(anyString())).thenReturn(true);
		PowerMockito.when(redisTemplate.get(anyObject(), anyObject())).thenReturn(null);		
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void QueryFlowRedisKeyIsNull() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		PowerMockito.when(redisTemplate.exists(anyString())).thenReturn(false);		
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}

	@Test
	public void QueryFlowEntityIsNotBlank() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {			
			JSONObject jsonObjectResult = new JSONObject();
			jsonObjectResult.put("alltotal", "aa");
			jsonObjectResult.put("allused", "aa");
			jsonObjectResult.put("allcanuse", "aa");
			
			JSONObject jsonObjectDingXiang = new JSONObject();
			jsonObjectDingXiang.put("monthprodtotal", "aa");
			jsonObjectDingXiang.put("used", "aa");	
			
			JSONObject jsonObjectTotalInfo = new JSONObject();
			jsonObjectTotalInfo.put("total", "aa");
			jsonObjectTotalInfo.put("useable", "aa");
			jsonObjectTotalInfo.put("used", "aa");	
			  
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_CODE, IResultCode.GD_SERVICE_SUCCESS_CODE);
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_TYPE, IResultCode.GD_SERVICE_SUCCESS_TYPE);
			jsonObjectResult.put("dingXiangInfo", jsonObjectDingXiang.toString());
			jsonObjectResult.put("totalInfo", jsonObjectTotalInfo.toString());
			jsonObject.put("result", jsonObjectResult.toString());
				 				
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenReturn(jsonObject.toString());			
			try {
				queryGdFlowService.queryFlow(flowInfoReq,"");
			} catch (Exception e) {
			}
		} catch (Exception e) {
	
		}
	}

	@Test
	public void QueryFlowEntityIsNotBlankTwo() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {			
			JSONObject jsonObjectResult = new JSONObject();
			jsonObjectResult.put("alltotal", "aa");
			jsonObjectResult.put("allused", "aa");
			jsonObjectResult.put("allcanuse", "aa");
			
			JSONObject jsonObjectDingXiang = new JSONObject();
			jsonObjectDingXiang.put("monthprodtotal", "");
			jsonObjectDingXiang.put("used", "aa");	
			
			JSONObject jsonObjectTotalInfo = new JSONObject();
			jsonObjectTotalInfo.put("total", "aa");
			jsonObjectTotalInfo.put("useable", "aa");
			jsonObjectTotalInfo.put("used", "aa");	
			 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_CODE, IResultCode.GD_SERVICE_SUCCESS_CODE);
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_TYPE, IResultCode.GD_SERVICE_SUCCESS_TYPE);
			jsonObjectResult.put("dingXiangInfo", jsonObjectDingXiang.toString());
			jsonObjectResult.put("totalInfo", jsonObjectTotalInfo.toString());
			jsonObject.put("result", jsonObjectResult.toString());
				 				
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenReturn(jsonObject.toString());			
			try {
				queryGdFlowService.queryFlow(flowInfoReq,"");
			} catch (Exception e) {
			}
		} catch (Exception e) {
	
		}
	}

	
	@Test
	public void QueryFlowEntityIsNotBlankThree() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_CODE, "1");
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_TYPE, "1");			 				
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenReturn(jsonObject.toString());			
			try {
				queryGdFlowService.queryFlow(flowInfoReq,"");
			} catch (Exception e) {
			}
		} catch (Exception e) {	
		}
	}

	@Test
	public void QueryFlowEntityIsNotBlankFour() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("respdesc", "1");
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_CODE, "1");
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_TYPE, "1");			 				
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenReturn(jsonObject.toString());			
			try {
				queryGdFlowService.queryFlow(flowInfoReq,"");
			} catch (Exception e) {
			}
		} catch (Exception e) {	
		}
	}

	@Test
	public void QueryFlowEntityIsNotBlankFive() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("respdesc", "1");
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_CODE, IResultCode.GD_SERVICE_SUCCESS_CODE);
			jsonObject.put(MktCampaignConst.GDApplicationService.RESP_TYPE, IResultCode.GD_SERVICE_SUCCESS_TYPE);
			jsonObject.put("result","");
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenReturn(jsonObject.toString());			
			try {
				queryGdFlowService.queryFlow(flowInfoReq,"");
			} catch (Exception e) {
			}
		} catch (Exception e) {	
		}
	}
	
	
	@Test
	public void QueryFlowEntityThrowException() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenThrow(new Exception("抛出异常"));
		} catch (Exception e1) {
		}
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}

	@Test
	public void QueryFlowStaticDate() {
		QueryFlowInfoReq flowInfoReq = new QueryFlowInfoReq();
		try {
			PowerMockito.when(cmbsGDQueryService.queryAllFlow(anyObject())).thenThrow(new Exception("抛出异常"));
		} catch (Exception e1) {
		}
		try {
			queryGdFlowService.queryFlow(flowInfoReq,"");
		} catch (Exception e) {
		}
	}
}
