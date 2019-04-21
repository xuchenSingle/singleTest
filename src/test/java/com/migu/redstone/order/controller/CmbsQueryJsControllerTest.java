package com.migu.redstone.order.controller;

import static org.mockito.Matchers.anyObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.dto.Header;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.QueryUserTagsReq;
import com.migu.redstone.order.service.dto.response.QueryUserTagsResp;
import com.migu.redstone.order.service.interfaces.ICmbsMiguVideoService;

public class CmbsQueryJsControllerTest {

    @InjectMocks
    private CmbsQueryJsController queryJsContro;

    @Mock
    private ICmbsMiguVideoService cmbsMiguVodeoService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsNewUserAndMember() throws Exception {
        PowerMockito.when(cmbsMiguVodeoService.isNewUserAndMember(anyObject())).thenReturn(new QueryUserTagsResp(new Result().success(), true, true));
        QueryUserTagsResp resp = queryJsContro.isNewUserAndMember(new QueryUserTagsReq(new Header(), "111"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }


}
