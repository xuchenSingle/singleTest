package com.migu.redstone.order.controller;

import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.order.service.dto.model.BizQualificationProd;
import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.request.QueryProcessedMemberReq;
import com.migu.redstone.order.service.dto.request.UserTagsAbilityReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;
import com.migu.redstone.order.service.dto.response.QueryProcessedMemberRsp;
import com.migu.redstone.order.service.dto.response.UserTagsAbilityRsp;
import com.migu.redstone.order.service.interfaces.ICmbsMiguVideoService;

public class CmbsQueryAbilityControllerTest {

    @InjectMocks
    private CmbsQueryAbilityController queryAbilityContr;

    @Mock
    private ICmbsMiguVideoService cmbsMiguVideoService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryProcessedMemberPhoneNumberNull() throws Exception {
        QueryProcessedMemberRsp resp = queryAbilityContr.queryProcessedMember(new QueryProcessedMemberReq(null,"1"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testQueryProcessedMemberPhoneNumberLength() throws Exception {
        QueryProcessedMemberRsp resp = queryAbilityContr.queryProcessedMember(new QueryProcessedMemberReq("111111111111","1"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testQueryProcessedMemberSubTypeNull() throws Exception {
        QueryProcessedMemberRsp resp = queryAbilityContr.queryProcessedMember(new QueryProcessedMemberReq("11111111111",null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testQueryProcessedMemberSubTypeLength() throws Exception {
        QueryProcessedMemberRsp resp = queryAbilityContr.queryProcessedMember(new QueryProcessedMemberReq("11111111111","111"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testQueryProcessedMemberCheckTrue() throws Exception {
        PowerMockito.when(cmbsMiguVideoService.queryProcessedMember(anyObject())).thenReturn(new QueryProcessedMemberRsp("0000",null,null));
        QueryProcessedMemberRsp resp = queryAbilityContr.queryProcessedMember(new QueryProcessedMemberReq("11111111111","11"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_SUCCESS_CODE);
    }

    @Test
    public void testGetUserTacgsPhoneNumberNull() throws Exception {
        UserTagsAbilityRsp resp = queryAbilityContr.getUserTacgs(new UserTagsAbilityReq(null,"1"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testGetUserTacgsPhoneNumberLength() throws Exception {
        UserTagsAbilityRsp resp = queryAbilityContr.getUserTacgs(new UserTagsAbilityReq("111111111111","1"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testGetUserTacgsSubTypeNull() throws Exception {
        UserTagsAbilityRsp resp = queryAbilityContr.getUserTacgs(new UserTagsAbilityReq("11111111111",null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testGetUserTacgsSubTypeLength() throws Exception {
        UserTagsAbilityRsp resp = queryAbilityContr.getUserTacgs(new UserTagsAbilityReq("11111111111","111"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
    }

    @Test
    public void testGetUserTacgsCheckTrue() throws Exception {
        PowerMockito.when(cmbsMiguVideoService.getUserTags(anyObject())).thenReturn(new UserTagsAbilityRsp("0000", null, null));
        UserTagsAbilityRsp resp = queryAbilityContr.getUserTacgs(new UserTagsAbilityReq("11111111111","11"));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_PROCESSED_MEMBER_SUCCESS_CODE);
    }

    @Test
    public void testCheckBizQualificationNumberTypeNull() throws Exception {
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq());
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationNumberTypeLength() throws Exception {
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("111", null, null, null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationGoodsIdNull() throws Exception {
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", null, null, null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationGoodsIdLength() throws Exception {
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11",
                "1111111111111111111111111111111111", null, null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationNumberInfoNull() throws Exception {
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1", null, null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationNumberInfoLength() throws Exception {
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1",
                "111111111111", null));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationListProductIdNull() throws Exception {
        BizQualificationProd bizQualificationProd = new BizQualificationProd();
        List<BizQualificationProd> productList = new ArrayList<>();
        productList.add(bizQualificationProd);
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1",
                "11111111111", productList));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationListProductIdLength() throws Exception {
        BizQualificationProd bizQualificationProd = new BizQualificationProd(null, "1111111111111111111111111111111111", null);
        List<BizQualificationProd> productList = new ArrayList<>();
        productList.add(bizQualificationProd);
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1",
                "11111111111", productList));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationListProductTypeNull() throws Exception {
        BizQualificationProd bizQualificationProd = new BizQualificationProd(null, "1", null);
        List<BizQualificationProd> productList = new ArrayList<>();
        productList.add(bizQualificationProd);
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1",
                "11111111111", productList));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationListProductTypeLength() throws Exception {
        BizQualificationProd bizQualificationProd = new BizQualificationProd("1111111111111111111111111111111111", "1", null);
        List<BizQualificationProd> productList = new ArrayList<>();
        productList.add(bizQualificationProd);
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1",
                "11111111111", productList));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
    }

    @Test
    public void testCheckBizQualificationCheckTrue() throws Exception {
        PowerMockito.when(cmbsMiguVideoService.checkBizQualification(anyObject())).thenReturn(new CmbsBizQualificationRsp("0000", ""));
        BizQualificationProd bizQualificationProd = new BizQualificationProd("1", "1", null);
        List<BizQualificationProd> productList = new ArrayList<>();
        productList.add(bizQualificationProd);
        CmbsBizQualificationRsp resp = queryAbilityContr.checkBizQualification(new CmbsBizQualificationReq("11", "1",
                "11111111111", productList));
        Assert.assertEquals(resp.getBizCode(), IResultCode.ABILITY_BIZ_QUALIFICATION_SUCCESS_CODE);
    }
}
