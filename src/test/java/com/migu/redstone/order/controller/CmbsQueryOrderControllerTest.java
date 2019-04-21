/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.controller;

import static org.mockito.Matchers.anyObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.dto.Header;
import com.migu.redstone.order.controller.CmbsQueryOrderController;
import com.migu.redstone.order.service.dto.request.QuerySubOrderReq;
import com.migu.redstone.order.service.dto.response.QuerySubOrderRsp;
import com.migu.redstone.order.service.impl.CmbsQueryOrderService;

/**
* 类作用:    查询订单controller测试类
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order
* 类名称:    CmbsQueryOrderControllerTest
* 类描述:    奖品发放任务controller
* 创建人:    jianghao
* 创建时间:   2018年11月6日 下午1:14:13
*/
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class CmbsQueryOrderControllerTest {
    @InjectMocks
    CmbsQueryOrderController cmbsQueryOrderController;

    @Mock
    CmbsQueryOrderService cmbsQueryOrderService;

    @Test
    public void testQueryOrder() throws Exception {
        QuerySubOrderRsp responsePowerMock = new QuerySubOrderRsp();
        PowerMockito.when(cmbsQueryOrderService.querySubOrder(anyObject())).thenReturn(responsePowerMock);

        QuerySubOrderReq request = new QuerySubOrderReq();
        request.setRequestHeader(getRequestHeader());
        request.setSubOrderId("1");
        request.setPhoneNumber("1");
        QuerySubOrderRsp response = cmbsQueryOrderController.querySubOrder(request);
        Assert.assertEquals(response.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

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
