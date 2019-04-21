//package com.migu.redstone.order.service;
//
//import static org.mockito.Matchers.anyBoolean;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyObject;
//import static org.mockito.Matchers.anyString;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PowerMockIgnore;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import com.migu.redstone.client.dto.response.QueryOrderBusinessRsp;
//import com.migu.redstone.common.impl.QueryTokenService;
//import com.migu.redstone.constants.IResultCode;
//import com.migu.redstone.dto.QueryTokenResp;
//import com.migu.redstone.entity.Result;
//import com.migu.redstone.excetion.BusinessException;
//import com.migu.redstone.order.service.dto.request.QueryOrderBusinessReq;
//import com.migu.redstone.order.service.impl.CmbsQueryOrderBusinessService;
//import com.migu.redstone.redis.RedisTemplate;
//import com.migu.redstone.utils.DateCommonUtil;
//import com.migu.redstone.utils.JsonUtil;
//import com.migu.redstone.utils.UrlUtil;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ CmbsQueryOrderBusinessService.class, UrlUtil.class, DateCommonUtil.class, JsonUtil.class })
//@PowerMockIgnore("javax.management.*")
//public class CmbsQueryOrderBusinessServiceTest {
//
//    @InjectMocks
//    private CmbsQueryOrderBusinessService queryOrderBusinessService;
//
//    @Mock
//    private RedisTemplate redisTemplate;
//
//    @Mock
//    private QueryTokenService queryTokenService;
//
//    @Mock
//    private YJRestHttpclient yjRestHttpclient;
//
//    @Test
//    public void testQueryOrderBusinessSuccess() throws Exception {
//        PowerMockito.when(redisTemplate.get(anyString())).thenReturn("token");
//        PowerMockito.when(queryTokenService.queryAuthCode(anyObject(), anyString(), anyString())).thenReturn("authCode");
//        PowerMockito.mockStatic(DateCommonUtil.class);
//        PowerMockito.when(DateCommonUtil.dateToString(anyObject(), anyString())).thenReturn("yyyyMMddHHmmssSSS");
//        PowerMockito.mockStatic(UrlUtil.class);
//        PowerMockito.when(UrlUtil.packagingUrl(anyString(), anyObject())).thenReturn("uri");
//        PowerMockito.when(yjRestHttpclient.get(anyObject(), anyString(), anyBoolean(), anyString(), anyInt())).thenReturn("responseStr");
//        PowerMockito.mockStatic(JsonUtil.class);
//        PowerMockito.when(JsonUtil.stringToObject(anyString(), anyObject())).thenReturn(new QueryOrderBusinessRsp("1", null, null, null));
//        QueryOrderBusinessReq req = new QueryOrderBusinessReq("01", "15651928301", "01");
//        QueryOrderBusinessRsp resp = queryOrderBusinessService.queryOrderBusiness(req);
//        Assert.assertEquals(resp.getBizCode(), "1");
//    }
//
//    @Test
//    public void testQueryOrderBusinessNoToken() throws Exception {
//        PowerMockito.when(redisTemplate.get(anyString())).thenReturn("");
//        PowerMockito.when(queryTokenService.queryToken(anyObject())).thenReturn(new QueryTokenResp(Result.success(), "1", "1"));
//        PowerMockito.when(queryTokenService.queryAuthCode(anyObject(), anyString(), anyString())).thenReturn("authCode");
//        PowerMockito.mockStatic(DateCommonUtil.class);
//        PowerMockito.when(DateCommonUtil.dateToString(anyObject(), anyString())).thenReturn("yyyyMMddHHmmssSSS");
//        PowerMockito.mockStatic(UrlUtil.class);
//        PowerMockito.when(UrlUtil.packagingUrl(anyString(), anyObject())).thenReturn("uri");
//        PowerMockito.when(yjRestHttpclient.get(anyObject(), anyString(), anyBoolean(), anyString(), anyInt())).thenReturn("responseStr");
//        PowerMockito.mockStatic(JsonUtil.class);
//        PowerMockito.when(JsonUtil.stringToObject(anyString(), anyObject())).thenReturn(new QueryOrderBusinessRsp("1", null, null, null));
//        QueryOrderBusinessReq req = new QueryOrderBusinessReq("01", "15651928301", "01");
//        QueryOrderBusinessRsp resp = queryOrderBusinessService.queryOrderBusiness(req);
//        Assert.assertEquals(resp.getBizCode(), "1");
//    }
//
//    @Test
//    public void testQueryOrderBusinessException() throws Exception {
//        PowerMockito.when(redisTemplate.get(anyString())).thenReturn("");
//        PowerMockito.when(queryTokenService.queryToken(anyObject())).thenReturn(new QueryTokenResp());
//        QueryOrderBusinessReq req = new QueryOrderBusinessReq("01", "15651928301", "01");
//        QueryOrderBusinessRsp resp = new QueryOrderBusinessRsp();
//        try {
//            resp = queryOrderBusinessService.queryOrderBusiness(req);
//            Assert.assertEquals(resp.getBizCode(), "1");
//        } catch (BusinessException e) {
//            Assert.assertEquals(e.getResult().getResultCode(), IResultCode.CMBS_SYS_ERROR);
//        }   
//    }
//
//}
