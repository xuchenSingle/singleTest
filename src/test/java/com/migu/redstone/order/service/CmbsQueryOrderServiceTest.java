/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import com.migu.redstone.business.order.mapper.CmbsOrderMapper;
import com.migu.redstone.cfg.product.mapper.CmbsProdMapper;
import com.migu.redstone.dto.Header;
import com.migu.redstone.order.service.impl.CmbsQueryOrderService;

/**
* 类作用:    查询订单service测试类
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service
* 类名称:    CmbsQueryOrderServiceTest
* 类描述:    查询订单service测试类
* 创建人:    jianghao
* 创建时间:   2018年11月6日 下午2:12:09
*/
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class CmbsQueryOrderServiceTest {
    @InjectMocks
    CmbsQueryOrderService cmbsQueryOrderService;

    @Mock
    CmbsOrderMapper cmbsOrderMapper;

    @Mock
    CmbsProdMapper cmbsProdMapper;

    /*@Test
    public void testQueryOrderNormal() throws Exception {
        CmbsOrder cmbsOrder = new CmbsOrder();
        cmbsOrder.setOrderId(1L);
        cmbsOrder.setMsisdn("18205190767");
        cmbsOrder.setProductId(1L);
        cmbsOrder.setGmtCreate(new Date());
        PowerMockito.when(cmbsOrderMapper.getOrderByOrderIdAndMsisdn(
            anyObject(), anyString())).thenReturn(cmbsOrder);

        CmbsProd cmbsProd = new CmbsProd();
        cmbsProd.setProductId(1L);
        cmbsProd.setProductName("商品 1");
        PowerMockito.when(cmbsProdMapper.getProdByProdId(anyObject())).thenReturn(cmbsProd);

        QuerySubOrderReq request = new QuerySubOrderReq();
        request.setRequestHeader(getRequestHeader());
        request.setOrderId("1");
        request.setPhoneNumber("18205190767");
        QuerySubOrderRsp response = cmbsQueryOrderService.quryOrder(request);
        Assert.assertEquals(response.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryOrderNoOrder() throws Exception {
        PowerMockito.when(cmbsOrderMapper.getOrderByOrderIdAndMsisdn(
            anyObject(), anyString())).thenReturn(null);

        CmbsProd cmbsProd = new CmbsProd();
        cmbsProd.setProductId(1L);
        cmbsProd.setProductName("商品 1");
        PowerMockito.when(cmbsProdMapper.getProdByProdId(anyObject())).thenReturn(cmbsProd);

        QuerySubOrderReq request = new QuerySubOrderReq();
        request.setRequestHeader(getRequestHeader());
        request.setOrderId("1");
        request.setPhoneNumber("18205190767");
        QuerySubOrderRsp response = cmbsQueryOrderService.quryOrder(request);
        Assert.assertEquals(response.getResult().getResultCode(), IResultCode.CMBS_REQUIRED_PARAM_NOT_RETURNED);
    }

    @Test
    public void testQueryOrderNoProd() throws Exception {
        CmbsOrder cmbsOrder = new CmbsOrder();
        cmbsOrder.setOrderId(1L);
        cmbsOrder.setMsisdn("18205190767");
        cmbsOrder.setProductId(1L);
        cmbsOrder.setGmtCreate(new Date());
        PowerMockito.when(cmbsOrderMapper.getOrderByOrderIdAndMsisdn(
            anyObject(), anyString())).thenReturn(cmbsOrder);

        PowerMockito.when(cmbsProdMapper.getProdByProdId(anyObject())).thenReturn(null);

        QuerySubOrderReq request = new QuerySubOrderReq();
        request.setRequestHeader(getRequestHeader());
        request.setOrderId("1");
        request.setPhoneNumber("18205190767");
        QuerySubOrderRsp response = cmbsQueryOrderService.quryOrder(request);
        Assert.assertEquals(response.getResult().getResultCode(),
            IResultCode.CMBS_REQUIRED_PARAM_NOT_RETURNED);
    }*/

    private Header getRequestHeader() {
        Header reqHeader = new Header();
        reqHeader.setVersion("11111");
        reqHeader.setTransactionId("11111111111111111111111111111111");
        reqHeader.setProductLine("06");
        reqHeader.setPortalType("06");
        reqHeader.setPlatform("06");
        reqHeader.setCompanyId("06");
        reqHeader.setAuthenticatorSource("1");
        reqHeader.setSourceDeviceCode("1");
        reqHeader.setTimeStamp("2018-11-06 14:27:00");
        reqHeader.setImei("zs-imei");
        reqHeader.setImsi("zs-imsi");
        reqHeader.setClientVer("zs-clientVer");
        reqHeader.setAppid("12345678901234567890");
        reqHeader.setAppName("zs-appName");
        reqHeader.setAccessInfo("zs-accessInfo");
        return reqHeader;
    }
}
