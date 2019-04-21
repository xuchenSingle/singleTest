package com.migu.redstone.order.service;

import com.migu.redstone.client.dto.request.MiGuVideoPushListMsgReq;
import com.migu.redstone.client.dto.request.MiGuVideoPushSingleMsgReq;
import com.migu.redstone.client.dto.response.MiGuVideoPushListMsgRes;
import com.migu.redstone.client.dto.response.MiGuVideoPushSingleMsgRes;
import com.migu.redstone.client.enums.MiGuVideoPushMsgCode;
import com.migu.redstone.client.service.interfaces.IMiGuVideoOutClientService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.order.service.dto.request.PushListMsgReq;
import com.migu.redstone.order.service.dto.request.PushSingleMsgReq;
import com.migu.redstone.order.service.dto.response.PushListMsgResp;
import com.migu.redstone.order.service.dto.response.PushSingleMsgResp;
import com.migu.redstone.order.service.impl.CmbQueryJsService;
import com.migu.redstone.utils.CommonUtil;
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

import static org.mockito.Matchers.anyObject;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ CmbQueryJsService.class, StringUtils.class})
@PowerMockIgnore("javax.management.*")
public class CmbQueryJsServiceTest {
    @InjectMocks
    private CmbQueryJsService cmbQueryJsService;

    @Mock
    private IMiGuVideoOutClientService miGuVideoOutClientService;

    @Test
    public void testPushSingleMsgSuccess() throws Exception {
        PushSingleMsgReq req = new
            PushSingleMsgReq(null,"250","1","443","13795274234","1545903209","");
        MiGuVideoPushSingleMsgReq request = new MiGuVideoPushSingleMsgReq(req.getProvinceId(), req.getBatchId(),
            req.getMsgId(), req.getToUserPhone(), req.getTimestamp(),req.getStartTime());
        PowerMockito.when(miGuVideoOutClientService.pushSingleMsg(request))
            .thenReturn(new MiGuVideoPushSingleMsgRes("SUCCESS", "1", "1"));
        PushSingleMsgResp resp = cmbQueryJsService.pushSingleMsg(req);
        Assert.assertEquals(resp.getResult().getResultMessage().toUpperCase(), MiGuVideoPushMsgCode.SUCCESS.getStatus());
    }

    @Test
    public void testPushSingleMsgFail() throws Exception {
        PushSingleMsgReq req = new
            PushSingleMsgReq(null,"250","1","443","13795274234","1545903209","");
        MiGuVideoPushSingleMsgReq request = new MiGuVideoPushSingleMsgReq(req.getProvinceId(), req.getBatchId(),
            req.getMsgId(), req.getToUserPhone(), req.getTimestamp(),req.getStartTime());
        PowerMockito.when(miGuVideoOutClientService.pushSingleMsg(request))
            .thenReturn(new MiGuVideoPushSingleMsgRes("1", "1", "1"));
        PushSingleMsgResp resp = cmbQueryJsService.pushSingleMsg(req);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.when(!StringUtils.equals("SUCCESS", MiGuVideoPushMsgCode.SUCCESS.getStatus())).thenReturn(false);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.JS_PLATFORM_ERROR_CODE);
    }

    @Test
    public void testPushListMsgSuccess() throws Exception {
        PushListMsgReq req = new
            PushListMsgReq(null,"250","1","443","test","1545903209","");
        MiGuVideoPushListMsgReq request = new MiGuVideoPushListMsgReq(req.getProvinceId(), req.getBatchId(),
            req.getMsgId(), req.getFileName(), req.getTimestamp(),req.getStartTime());
        PowerMockito.when(miGuVideoOutClientService.pushListMsg(request))
            .thenReturn(new MiGuVideoPushListMsgRes("SUCCESS", "1", "1"));
        PushListMsgResp resp = cmbQueryJsService.pushListMsg(req);
        Assert.assertEquals(resp.getResult().getResultMessage().toUpperCase(), MiGuVideoPushMsgCode.SUCCESS.getStatus());
    }

    @Test
    public void testPushListMsgFail() throws Exception {
        PushListMsgReq req = new
            PushListMsgReq(null,"250","1","443","test","1545903209","");
        MiGuVideoPushListMsgReq request = new MiGuVideoPushListMsgReq(req.getProvinceId(), req.getBatchId(),
            req.getMsgId(), req.getFileName(), req.getTimestamp(),req.getStartTime());
        PowerMockito.when(miGuVideoOutClientService.pushListMsg(request))
            .thenReturn(new MiGuVideoPushListMsgRes("1", "1", "1"));
        PushListMsgResp resp = cmbQueryJsService.pushListMsg(req);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.when(!StringUtils.equals("SUCCESS", MiGuVideoPushMsgCode.SUCCESS.getStatus())).thenReturn(false);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.JS_PLATFORM_ERROR_CODE);
    }
}
