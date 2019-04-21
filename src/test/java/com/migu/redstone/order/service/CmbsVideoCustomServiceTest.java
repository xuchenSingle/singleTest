package com.migu.redstone.order.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import com.migu.redstone.client.dto.model.ActivityData;
import com.migu.redstone.client.dto.model.ActivityInfo;
import com.migu.redstone.client.dto.model.JsCampaignActivityData;
import com.migu.redstone.client.dto.model.ResultData;
import com.migu.redstone.client.dto.request.JsMarketingPositionReq;
import com.migu.redstone.client.dto.response.BroadbandAddressQueryResp;
import com.migu.redstone.client.dto.response.CheckBroadbandStatusResp;
import com.migu.redstone.client.dto.response.JsBaseRsp;
import com.migu.redstone.client.dto.response.JsCampaignActivityRes;
import com.migu.redstone.client.dto.response.JsCheckOrderBroadbandRsp;
import com.migu.redstone.client.dto.response.JsMarketingPositionRes;
import com.migu.redstone.client.dto.response.JsPlayerMarketingPositionFlowerRes;
import com.migu.redstone.client.dto.response.JsPlayerMarketingPositionFlowerRetObjRes;
import com.migu.redstone.client.dto.response.JsQueryBroadbandOrderRsp;
import com.migu.redstone.client.dto.response.QueryOrderBusinessRsp;
import com.migu.redstone.client.dto.response.TargetUserQueryResp;
import com.migu.redstone.client.service.interfaces.IJsOutClientService;
import com.migu.redstone.common.interfaces.IQualificationService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.dto.Header;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.CheckBroadbandIdentityReq;
import com.migu.redstone.order.service.dto.request.CheckMktPlanQualificationReq;
import com.migu.redstone.order.service.dto.request.CheckOrderBroadbandReq;
import com.migu.redstone.order.service.dto.request.QueryActivityDataReq;
import com.migu.redstone.order.service.dto.request.QueryActivityDetailReq;
import com.migu.redstone.order.service.dto.request.QueryActivityPositionReq;
import com.migu.redstone.order.service.dto.request.QueryBannerActivityReq;
import com.migu.redstone.order.service.dto.request.QueryBroadbandAddressReq;
import com.migu.redstone.order.service.dto.request.QueryBroadbandOrderReq;
import com.migu.redstone.order.service.dto.request.QueryJSFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryOrderBusinessReq;
import com.migu.redstone.order.service.dto.request.QueryOrderedBusinessReq;
import com.migu.redstone.order.service.dto.request.QueryTargetUserReq;
import com.migu.redstone.order.service.dto.request.QueryUserPackageInfoReq;
import com.migu.redstone.order.service.dto.request.QueryUserStatusReq;
import com.migu.redstone.order.service.dto.response.CheckBroadbandIdentityResp;
import com.migu.redstone.order.service.dto.response.CheckMktPlanQualificationResp;
import com.migu.redstone.order.service.dto.response.CheckOrderBroadbandResp;
import com.migu.redstone.order.service.dto.response.QueryActivityDataResp;
import com.migu.redstone.order.service.dto.response.QueryActivityDetailResp;
import com.migu.redstone.order.service.dto.response.QueryActivityPositionResp;
import com.migu.redstone.order.service.dto.response.QueryBannerActivityResp;
import com.migu.redstone.order.service.dto.response.QueryBroadbandAddressResp;
import com.migu.redstone.order.service.dto.response.QueryBroadbandOrderResp;
import com.migu.redstone.order.service.dto.response.QueryJSFlowInfoResp;
import com.migu.redstone.order.service.dto.response.QueryMonthFlowUseResponse;
import com.migu.redstone.order.service.dto.response.QueryOrderedBusinessResp;
import com.migu.redstone.order.service.dto.response.QueryTargetUserResp;
import com.migu.redstone.order.service.dto.response.QueryUserStatusResp;
import com.migu.redstone.order.service.impl.CmbsVideoCustomService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryOrderBusinessService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJPlatFormService;

public class CmbsVideoCustomServiceTest {

    @InjectMocks
    private CmbsVideoCustomService videoCustomService;

    @Mock
    private IJsOutClientService jsOutClientService;

    @Mock
    private ICmbsQueryOrderBusinessService cmbsQueryOrderBusinessService;

    @Mock
    private IQualificationService qualificationService;

    @Mock
    private ICmbsQueryYJPlatFormService cmbsQueryYJPLatFormService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryJsFlowPercentSuccess() throws Exception {
        JsPlayerMarketingPositionFlowerRetObjRes jsResObj = new JsPlayerMarketingPositionFlowerRetObjRes("15651928301",
                new Date().getTime(), "buscode", 1, 1d, "1", "1", "1", "1", 1l);
        JsPlayerMarketingPositionFlowerRes jsRes = new JsPlayerMarketingPositionFlowerRes("msg", "0", "true", jsResObj);
        PowerMockito.when(jsOutClientService.getMiguFlower(anyString())).thenReturn(jsRes);
        QueryJSFlowInfoReq req = new QueryJSFlowInfoReq(null, "15651928301");
        QueryJSFlowInfoResp resp = videoCustomService.queryJsFlowPercent(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryJsFlowPercentFail() throws Exception {
        JsPlayerMarketingPositionFlowerRes jsRes = new JsPlayerMarketingPositionFlowerRes("msg", "-1", "false", null);
        PowerMockito.when(jsOutClientService.getMiguFlower(anyString())).thenReturn(jsRes);
        QueryJSFlowInfoReq req = new QueryJSFlowInfoReq(null, "15651928301");
        QueryJSFlowInfoResp resp = videoCustomService.queryJsFlowPercent(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testQueryActivityPositionSuccess() throws Exception {
        QueryActivityPositionReq req = new QueryActivityPositionReq(null, "15651928301", null, null, null, "1");
        JsMarketingPositionReq jsMarketingPositionReq = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
                req.getIsMember(), req.getIsV15(), req.getLocationid());
        PowerMockito.when(jsOutClientService.getPopupMarketingPosition(jsMarketingPositionReq)).thenReturn(new
                JsMarketingPositionRes("1", "1", "1", "1"));
        QueryActivityPositionResp resp = videoCustomService.queryActivityPosition(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryActivityPositionFail() throws Exception {
        QueryActivityPositionReq req = new QueryActivityPositionReq(null, "15651928301", null, null, null, "1");
        JsMarketingPositionReq jsMarketingPositionReq = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
                req.getIsMember(), req.getIsV15(), req.getLocationid());
        PowerMockito.when(jsOutClientService.getPopupMarketingPosition(jsMarketingPositionReq)).thenReturn(null);
        QueryActivityPositionResp resp = videoCustomService.queryActivityPosition(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testQueryBannerActivitySuccess() throws Exception {
        QueryBannerActivityReq req = new QueryBannerActivityReq(null, "15651928301", null, null, null, "1");
        JsMarketingPositionReq request = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
                req.getIsMember(), req.getIsV15(), req.getLocationid());
        PowerMockito.when(jsOutClientService.getBannerMarketingPosition(request)).thenReturn(new
                JsMarketingPositionRes("1", "1", "1", "1"));
        QueryBannerActivityResp resp = videoCustomService.queryBannerActivity(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryBannerActivityFail() throws Exception {
        QueryBannerActivityReq req = new QueryBannerActivityReq(null, "15651928301", null, null, null, "1");
        JsMarketingPositionReq request = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
                req.getIsMember(), req.getIsV15(), req.getLocationid());
        PowerMockito.when(jsOutClientService.getBannerMarketingPosition(request)).thenReturn(null);
        QueryBannerActivityResp resp = videoCustomService.queryBannerActivity(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testQueryActivityDetailSuccess() throws Exception {
        QueryActivityDetailReq req = new QueryActivityDetailReq(null, "15651928301", null, null, null, "1");
        JsMarketingPositionReq request = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
                req.getIsMember(), req.getIsV15(), req.getLocationid());
        PowerMockito.when(jsOutClientService.getDetailMarketingPosition(request)).thenReturn(new
                JsMarketingPositionRes("1", "1", "1", "1"));
        QueryActivityDetailResp resp = videoCustomService.queryActivityDetail(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryActivityDetailFail() throws Exception {
        QueryActivityDetailReq req = new QueryActivityDetailReq(null, "15651928301", null, null, null, "1");
        JsMarketingPositionReq request = new JsMarketingPositionReq(req.getPhoneNumber(), req.getIsNew(),
                req.getIsMember(), req.getIsV15(), req.getLocationid());
        PowerMockito.when(jsOutClientService.getDetailMarketingPosition(request)).thenReturn(null);
        QueryActivityDetailResp resp = videoCustomService.queryActivityDetail(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testQueryActivityDataSuccess() throws Exception {
        QueryActivityDataReq req = new QueryActivityDataReq(null, "15195756146", "platinumMember");
        JsCampaignActivityData data = new JsCampaignActivityData("1","1","1","1","1","1","1","1");
        PowerMockito.when(jsOutClientService.getCampaignActivity(req.getPhoneNumber(), req.getActivityId())).thenReturn(
                new JsCampaignActivityRes("0","1",data,"true"));
        QueryActivityDataResp resp = videoCustomService.queryActivityData(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryActivityDataFail() throws Exception {
        QueryActivityDataReq req = new QueryActivityDataReq(null, "15195756146", "platinumMember");
        JsCampaignActivityData data = new JsCampaignActivityData("1","1","1","1","1","1","1","1");
        PowerMockito.when(jsOutClientService.getCampaignActivity(req.getPhoneNumber(), req.getActivityId())).thenReturn(null);
        QueryActivityDataResp resp = videoCustomService.queryActivityData(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testQueryOrderedBusinessSuccess() throws Exception {
        QueryOrderedBusinessReq req = new QueryOrderedBusinessReq(null, "15651928301", "01");
        QueryOrderBusinessReq queryOrderBusinessReq = new QueryOrderBusinessReq("01", req.getPhoneNumber(),
                req.getBusiType());
        PowerMockito.when(cmbsQueryOrderBusinessService.queryOrderBusiness(queryOrderBusinessReq)).thenReturn(
                new QueryOrderBusinessRsp("1","1","1",null));
        QueryOrderedBusinessResp resp = videoCustomService.queryOrderedBusiness(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryOrderedBusinessCallBackFalse() throws Exception {
        QueryOrderedBusinessReq req = new QueryOrderedBusinessReq(null, "15651928301", "01");
        QueryOrderBusinessReq queryOrderBusinessReq = new QueryOrderBusinessReq("01", req.getPhoneNumber(),
                req.getBusiType());
        PowerMockito.when(cmbsQueryOrderBusinessService.queryOrderBusiness(queryOrderBusinessReq)).thenReturn(
                new QueryOrderBusinessRsp("0","1","1",null));
        QueryOrderedBusinessResp resp = videoCustomService.queryOrderedBusiness(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.CMBS_CURRENT_PROVINCE_NOT_SUPPRTT);
    }

    @Test
    public void testQueryOrderedBusinessFail() throws Exception {
        QueryOrderedBusinessReq req = new QueryOrderedBusinessReq(null, "15651928301", "01");
        QueryOrderBusinessReq queryOrderBusinessReq = new QueryOrderBusinessReq("01", req.getPhoneNumber(),
                req.getBusiType());
        PowerMockito.when(cmbsQueryOrderBusinessService.queryOrderBusiness(queryOrderBusinessReq)).thenReturn(null);
        QueryOrderedBusinessResp resp = videoCustomService.queryOrderedBusiness(req);
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

//    @Test
//    public void testCheckBusinessQualification() throws Exception {
//        CheckBusinessQualificationResp checkBusinessQualificationResp = new CheckBusinessQualificationResp(Result.success(), true);
//        PowerMockito.when(qualificationService.qualificationCheck(anyObject())).thenReturn(checkBusinessQualificationResp);
//        CheckBusinessQualificationResp resp = videoCustomService.checkBusinessQualification(new
//                CheckBusinessQualificationReq(null, "1", "15651928301", "1"));
//        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
//    }

    @Test
    public void testQueryUserStatus() throws Exception {
        QueryUserStatusResp queryUserStatusResp = new QueryUserStatusResp(Result.success(), "1");
        PowerMockito.when(cmbsQueryYJPLatFormService.queryUserStatusService(anyObject())).thenReturn(queryUserStatusResp);
        QueryUserStatusResp resp = videoCustomService.queryUserStatus(new QueryUserStatusReq(null, "15651928301"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryUserPackageInfo() throws Exception {
        PowerMockito.when(cmbsQueryYJPLatFormService.queryPackageUseInfo(anyObject())).thenReturn(new QueryMonthFlowUseResponse());
        QueryMonthFlowUseResponse resp = videoCustomService.queryUserPackageInfo(new QueryUserPackageInfoReq(null, "15651928301"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testCheckBroadbandIdentitySuccess() throws Exception {
        PowerMockito.when(jsOutClientService.broadbandIdentityCheck(anyObject())).thenReturn(new CheckBroadbandStatusResp("1", "1"));
        CheckBroadbandIdentityResp resp = videoCustomService.checkBroadbandIdentity(new CheckBroadbandIdentityReq(new Header(),
                "12345678901", "111111111111111111", "111"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE);
    }

    @Test
    public void testCheckBroadbandIdentityFail() throws Exception {
        PowerMockito.when(jsOutClientService.broadbandIdentityCheck(anyObject())).thenReturn(null);
        CheckBroadbandIdentityResp resp = videoCustomService.checkBroadbandIdentity(new CheckBroadbandIdentityReq(new Header(),
                "12345678901", "111111111111111111", "111"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testcheckOrderBroadbandSuccess() throws Exception {
        PowerMockito.when(jsOutClientService.getCheckOrderBroadband(anyObject())).thenReturn(new JsCheckOrderBroadbandRsp("0", "1"));
        CheckOrderBroadbandResp resp = videoCustomService.checkOrderBroadband(new CheckOrderBroadbandReq(new Header(),
                "12345678901", "111111111111111111", "111", "0"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testcheckOrderBroadbandFail() throws Exception {
        PowerMockito.when(jsOutClientService.getCheckOrderBroadband(anyObject())).thenReturn(new JsCheckOrderBroadbandRsp("1", "1"));
        CheckOrderBroadbandResp resp = videoCustomService.checkOrderBroadband(new CheckOrderBroadbandReq(new Header(),
                "12345678901", "111111111111111111", "111", "0"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE);
    }

    @Test
    public void testQueryBroadbandAddress0() throws Exception {
        PowerMockito.when(jsOutClientService.broadbandAddressQuery(anyObject())).thenReturn(new BroadbandAddressQueryResp("0", "success", null));
        QueryBroadbandAddressResp resp = videoCustomService.queryBroadbandAddress(new QueryBroadbandAddressReq(new Header(), null, null, null,
                null, null, null));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryBroadbandAddress1() throws Exception {
        PowerMockito.when(jsOutClientService.broadbandAddressQuery(anyObject())).thenReturn(new BroadbandAddressQueryResp("1", "success", null));
        QueryBroadbandAddressResp resp = videoCustomService.queryBroadbandAddress(new QueryBroadbandAddressReq(new Header(), null, null, null,
                null, null, null));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE);
    }

    @Test
    public void testQueryBroadbandAddressFail() throws Exception {
        PowerMockito.when(jsOutClientService.broadbandAddressQuery(anyObject())).thenReturn(null);
        QueryBroadbandAddressResp resp = videoCustomService.queryBroadbandAddress(new QueryBroadbandAddressReq(new Header(), null, null, null,
                null, null, null));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.REMOTE_SERVICE_ERROR_CODE);
    }

    @Test
    public void testQueryBroadbandOrderSuccess() throws Exception {
        PowerMockito.when(jsOutClientService.queryBroadbandOrder(anyString(), anyString())).thenReturn(new JsQueryBroadbandOrderRsp("0", "1",
                new ResultData("1", "1")));
        QueryBroadbandOrderResp resp = videoCustomService.queryBroadbandOrder(new QueryBroadbandOrderReq(new Header(), "1", "1"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryBroadbandOrderFail() throws Exception {
        PowerMockito.when(jsOutClientService.queryBroadbandOrder(anyString(), anyString())).thenReturn(new JsQueryBroadbandOrderRsp("8000000008", "1",
                new ResultData("1", "1")));
        QueryBroadbandOrderResp resp = videoCustomService.queryBroadbandOrder(new QueryBroadbandOrderReq(new Header(), "1", "1"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.CMBS_PARAM_VALIDATE_FAIL);
    }

    @Test
    public void testQueryBroadbandOrderOther() throws Exception {
        PowerMockito.when(jsOutClientService.queryBroadbandOrder(anyString(), anyString())).thenReturn(new JsQueryBroadbandOrderRsp("1", "1",
                new ResultData("1", "1")));
        QueryBroadbandOrderResp resp = videoCustomService.queryBroadbandOrder(new QueryBroadbandOrderReq(new Header(), "1", "1"));
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUB_ORDER_NOT_EXIST);
    }

    @Test
    public void testQueryTargetUserSuccess() throws Exception {
        ActivityInfo activityInfo = new ActivityInfo("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1");
        List<ActivityInfo> activityInfoList = new ArrayList<>();
        activityInfoList.add(activityInfo);
        ActivityData activityData = new ActivityData();
        activityData.setActivity(activityInfoList);
        PowerMockito.when(jsOutClientService.TargetUserQuery(anyObject())).thenReturn(new TargetUserQueryResp("0", "1", activityData));
        QueryTargetUserResp resp = videoCustomService.queryTargetUser(new QueryTargetUserReq());
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testQueryTargetUserFail() throws Exception {
        PowerMockito.when(jsOutClientService.TargetUserQuery(anyObject())).thenReturn(new TargetUserQueryResp("1", "1", null));
        QueryTargetUserResp resp = videoCustomService.queryTargetUser(new QueryTargetUserReq());
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE);
    }

    @Test
    public void testCheckMktPlanQualificationSuccess() throws Exception {
        PowerMockito.when(jsOutClientService.marketplanCheck(anyString(), anyString(), anyString())).thenReturn(new JsBaseRsp("0", "1"));
        CheckMktPlanQualificationResp resp = videoCustomService.checkMktPlanQualification(new CheckMktPlanQualificationReq());
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.SUCCESS_CODE);
    }

    @Test
    public void testCheckMktPlanQualificationFail() throws Exception {
        PowerMockito.when(jsOutClientService.marketplanCheck(anyString(), anyString(), anyString())).thenReturn(new JsBaseRsp("8000000008", "1"));
        CheckMktPlanQualificationResp resp = videoCustomService.checkMktPlanQualification(new CheckMktPlanQualificationReq());
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.CMBS_PARAM_VALIDATE_FAIL);
    }

    @Test
    public void testCheckMktPlanQualificationOther() throws Exception {
        PowerMockito.when(jsOutClientService.marketplanCheck(anyString(), anyString(), anyString())).thenReturn(new JsBaseRsp("1", "1"));
        CheckMktPlanQualificationResp resp = videoCustomService.checkMktPlanQualification(new CheckMktPlanQualificationReq());
        Assert.assertEquals(resp.getResult().getResultCode(), IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE);
    }
}
