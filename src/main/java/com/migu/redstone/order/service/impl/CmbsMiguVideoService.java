/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.cfg.product.mapper.CmbsProdMapper;
import com.migu.redstone.cfg.product.mapper.po.CmbsProd;
import com.migu.redstone.cfg.product.mapper.po.CmbsProdChannelAbilityCfg;
import com.migu.redstone.cfg.product.mapper.po.CmbsProdChar;
import com.migu.redstone.cfg.product.service.interfaces.ICmbsProdService;
import com.migu.redstone.client.CmbsCommonHttpClient;
import com.migu.redstone.client.MiguVideoFeignClient;
import com.migu.redstone.client.dto.model.TagInfo;
import com.migu.redstone.client.dto.model.Tags;
import com.migu.redstone.client.dto.request.NewUserAndMemberReq;
import com.migu.redstone.client.dto.response.NewUserAndMemberRsp;
import com.migu.redstone.client.dto.response.UserTagsRsp;
import com.migu.redstone.client.proxy.interfaces.IMiguVideoFeignProxy;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.Identity;
import com.migu.redstone.order.service.dto.model.ProcessedMemberInfo;
import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.request.QueryProcessedMemberReq;
import com.migu.redstone.order.service.dto.request.QueryUserTagsReq;
import com.migu.redstone.order.service.dto.request.UserTagsAbilityReq;
import com.migu.redstone.order.service.dto.request.UserTagsReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;
import com.migu.redstone.order.service.dto.response.QueryProcessedMemberRsp;
import com.migu.redstone.order.service.dto.response.QueryUserTagsResp;
import com.migu.redstone.order.service.dto.response.UserTagsAbilityRsp;
import com.migu.redstone.order.service.interfaces.ICmbsMiguVideoService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryVideoDispatcherService;
import com.migu.redstone.utils.CheckEnumUtil;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;
import com.migu.redstone.utils.SpringContextUtil;

import net.sf.json.JSONObject;


/**
 * 类作用:    视讯service
 * 项目名称:   cmbs-query-order
 * 包:       com.migu.redstone.order.service.impl
 * 类名称:    CmbsMiguVodeoService
 * 类描述:    视讯service
 * 创建人:    jianghao
 * 创建时间:   2018年11月23日 上午10:11:21
 */
@Service
public class CmbsMiguVideoService implements ICmbsMiguVideoService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsMiguVideoService.class);

    /**
     * cmbsCommonHttpClient.
     */
    @Autowired
    private CmbsCommonHttpClient cmbsCommonHttpClient;

    /**
     * miguVideoCommonFeignClient.
     */
    @Autowired
    private IMiguVideoFeignProxy miguVideoFeignProxy;

    /**
     * cfgCommonService.
     */
    @Autowired
    private ICfgCommonService cfgCommonService;

    /**
     * cmbsProdMapper.
     */
    @Autowired
    private CmbsProdMapper cmbsProdMapper;

    /**
     * cmbsProdService.
     */
    @Autowired
    private ICmbsProdService cmbsProdService;

    /**
     *<isNewUserAndMember>.
     *<江苏-视讯:获取用户是否是会员以及是否是新用户接口>
     * @param  request   [request]
     * @return [返回用户是否是会员以及是否是新用户结果]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
    @Override
    public QueryUserTagsResp isNewUserAndMember(QueryUserTagsReq request) throws Exception {
        //设置请求
        NewUserAndMemberReq newUserAndMemberReq = new NewUserAndMemberReq();
        newUserAndMemberReq.setPhoneNum(request.getPhoneNumber());
        newUserAndMemberReq.setTimestamp(System.currentTimeMillis());
        Map<String, Object> expandMap = new HashMap<String, Object>();
        expandMap.put("MSISDN", request.getPhoneNumber());
        //获取远程调用参数
        String clientId = "";
        String signType = "";
        String uriStr = "";
        String privateKey = "";
        CmbsStaticData clientIdData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.CLIENT_ID);
        CmbsStaticData signTypeData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.SIGN_TYPE);
        CmbsStaticData uriData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.IS_MEMBER_AND_NEW_USER_URL);
        CmbsStaticData privateKeyData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.MIGU_VIDEO_KEY, StaticDataConst.StaticCodeName.MIGU_VIDEO_PRIVATE_KEY);
        if (clientIdData != null) {
            clientId = clientIdData.getCodeValue();
        }
        if (signTypeData != null) {
            signType = signTypeData.getCodeValue();
        }
        if (uriData != null) {
            uriStr = uriData.getCodeValue();
        }
        if (privateKeyData != null) {
            privateKey = privateKeyData.getCodeValue();
        }
        String reqJsonStr = JsonUtil.objectToString(newUserAndMemberReq);
        String signStr = cmbsCommonHttpClient.processSign(reqJsonStr, privateKey);
        URI uri = new URI(uriStr);
        //远程调用
        NewUserAndMemberRsp newUserAndMemberRsp = miguVideoFeignProxy.isNewUserAndMember(expandMap,
            uri, clientId, signStr, signType, reqJsonStr);
        LOG.error("CmbsMiguVideoService.isNewUserAndMember, responseStr="
            + JsonUtil.objectToString(newUserAndMemberRsp) + " ,uri=" + uriStr  + " ,clientId=" + clientId
            +" ,sign=" + signStr + " ,signType=" + signType + " ,request=" + reqJsonStr);

        //设置response
        QueryUserTagsResp response = new QueryUserTagsResp();
        String resultCode = newUserAndMemberRsp.getResultCode();
        String errorCode = newUserAndMemberRsp.getErrorCode();
        if (StringUtils.isBlank(newUserAndMemberRsp.getResultDesc())) {
            if (StringUtils.isBlank(errorCode)) {
                newUserAndMemberRsp.setResultDesc(IResultCode.SUCCESS_MESSAGE);
            }
            else {
                newUserAndMemberRsp.setResultDesc("获取用户是否是会员以及新用户失败");
            } 
        }
        if (IResultCode.SUCCESS_MESSAGE.equalsIgnoreCase(resultCode)) {
            response.setResult(new Result(IResultCode.SUCCESS_CODE, newUserAndMemberRsp.getResultDesc()));
        }
        else {
            response.setResult(new Result(IResultCode.JS_TO_VIDEO_COMMON_ERROR_CODE,
                newUserAndMemberRsp.getResultDesc()));
        }
        response.setIsMember(newUserAndMemberRsp.isMiguvideo_member());
        response.setIsNew(newUserAndMemberRsp.isMiguvideo_newUser());
        return response;
    }

    /**
     *<queryProcessedMember>.
     *<一级能开已办理会员查询>
     * @param  request  [request]
     * @return [返回已办理会员]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
    @Override
    public QueryProcessedMemberRsp queryProcessedMember(QueryProcessedMemberReq request) throws Exception {
        QueryProcessedMemberRsp response = new QueryProcessedMemberRsp();

        //手机号处理
        String phoneNumber = request.getPhoneNumber();
        String subType = request.getSubType();
        if (phoneNumber.length() <= 11) {
            phoneNumber = MktCampaignConst.CmbsMiguVideo.PHONE_NUM_PREFIX + phoneNumber;
        }
        //已办理会员业务处理
        switch (subType) {
            case MktCampaignConst.CmbsMiguVideo.SUB_TYPE_MIGU_VIDEO: {
                //设置远程调用request-identityList
                queryVideoMember(response, phoneNumber, subType);
            }
            break;
            default: {
                LOG.error("CmbsMiguVideoService.queryProcessedMember error: 已办理会员查询当前子公司不支持");
                response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
                response.setBizDesc("已办理会员查询当前子公司不支持");
            }
            break;
        }
        return response;
    }

    /**
     *<queryVideoMember>.
     *<已办理会员查询-视讯>
     * @param  response   [response]
     * @param  phoneNumber   [phoneNumber]
     * @param  subType   [subType]
     * @throws Exception [Exception]
     * @author jianghao
     */
    private void queryVideoMember(QueryProcessedMemberRsp response,
        String phoneNumber, String subType) throws Exception {
        List<String> tagList = new ArrayList<String>();
        tagList.add(MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_MEMBER_CATEGORY);

        //设置response
        try {
            UserTagsRsp userTagsRsp = commonUserTags(phoneNumber, tagList);
            String resultCode = userTagsRsp.getResultCode();
            String resultDesc = userTagsRsp.getResultDesc();
            if (IResultCode.SUCCESS_MESSAGE.equalsIgnoreCase(resultCode)) {
                response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_SUCCESS_CODE);
                response.setBizDesc(resultDesc);
            }
            else {
                response.setBizCode(resultCode);
                response.setBizDesc(resultDesc);
            }
            if (!CheckEnumUtil.checkProcessedMemeberRsp(response.getBizCode())) {
                response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            }
            List<ProcessedMemberInfo> memberLevelList = new ArrayList<ProcessedMemberInfo>();
            //添加memberLevelList
            List<Tags> tagsList = userTagsRsp.getTags();
            if (CommonUtil.isNotEmptyCollection(tagsList)) {
                for (Tags tagsPO : tagsList) {
                    List<ProcessedMemberInfo> memberLevelChild = new ArrayList<ProcessedMemberInfo>();
                    List<TagInfo> identityTags = tagsPO.getIdentityTags();
                    if (CommonUtil.isNotEmptyCollection(identityTags)) {
                        for (TagInfo tagInfo : identityTags) {
                            processMemberRsp(memberLevelChild, tagInfo);
                        }
                    }
                    if (CommonUtil.isNotEmptyCollection(memberLevelChild)) {
                        memberLevelList.addAll(memberLevelChild);
                    }
                }
            }
            if (CommonUtil.isNotEmptyCollection(memberLevelList)) {
                response.setMemberLevelList(memberLevelList);
            }
        }
        catch (Exception e) {
            LOG.error("CmbsMiguVideoService.queryVideoMember error,"
                + " errorMessage=" + e.getMessage(), e);
            response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            response.setBizDesc("一级能开调视讯已办理会员查询远程调用失败");
        }
    }

    /**
     *<processMemberRsp>.
     *<已办理会员结果处理>
     * @param  memberLevelChild   [memberLevelChild]
     * @param  tagInfo   [tagInfo]
     * @author jianghao
     */
    private void processMemberRsp(List<ProcessedMemberInfo> memberLevelChild, TagInfo tagInfo) {
        //初始化
        JSONObject jsonTagValue = JsonUtil.stringToJson(tagInfo.getTagValue());
        if (jsonTagValue == null) {
            return;
        }
        String memberTypeStatic = MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_MEMBER_TYPE;

        //钻石会员
        String diamondName = MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_DIAMOND_NAME;
        String diaMondBool = JsonUtil.getValueFromJson(jsonTagValue, diamondName);
        if ("true".equals(diaMondBool)) {
            processMemberLevel(memberLevelChild, memberTypeStatic, diamondName);
        }

        //黑钻会员
        String blackDiamondName = MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_BLACK_DIAMOND_NAME;
        String blackDiaMondBool = JsonUtil.getValueFromJson(jsonTagValue, blackDiamondName);
        if ("true".equals(blackDiaMondBool)) {
            processMemberLevel(memberLevelChild, memberTypeStatic, blackDiamondName);
        }

        //黄金会员
        String goldenName = MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_GOLDEN_NAME;
        String goldenBool = JsonUtil.getValueFromJson(jsonTagValue, goldenName);
        if ("true".equals(goldenBool)) {
            processMemberLevel(memberLevelChild, memberTypeStatic, goldenName);
        }

        //体验会员
        String trialName = MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_TRIAL_NAME;
        String trialBool = JsonUtil.getValueFromJson(jsonTagValue, trialName);
        if ("true".equals(trialBool)) {
            processMemberLevel(memberLevelChild, memberTypeStatic, trialName);
        }
    }

    /**
     *<processMemberLevel>.
     *<设置会员等级信息>
     * @param  memberLevelChild   [memberLevelChild]
     * @param  memberTypeStatic   [memberTypeStatic]
     * @param  memberName   [memberName]
     * @author jianghao
     */
    private void processMemberLevel(List<ProcessedMemberInfo> memberLevelChild,
                                    String memberTypeStatic, String memberName) {
        CmbsStaticData cmbsStaticData = cfgCommonService
            .getCmbsStaticDataByCodeTypeAndCodeName(memberTypeStatic, memberName);
        String memberLevel = "";
        String memberLevelName = "";
        if (cmbsStaticData != null) {
            memberLevel = cmbsStaticData.getCodeValue();
            memberLevelName = cmbsStaticData.getAlias();
        }
        ProcessedMemberInfo processedMemberInfo = new ProcessedMemberInfo();
        processedMemberInfo.setMemberLevel(memberLevel);
        processedMemberInfo.setMemberLevelName(memberLevelName);
        processedMemberInfo.setSubType(MktCampaignConst.CmbsMiguVideo.SUB_TYPE_MIGU_VIDEO);
        memberLevelChild.add(processedMemberInfo);
    }

    /**
     *<commonUserTags>.
     *<用户标签通用步骤-远程调用>
     * @param  phoneNumber   [phoneNumber]
     * @param  tagList   [tagList]
     * @param  url   [url]
     * @return [返回远程调用结果]
     * @throws Exception [Exception]
     * @author jianghao
     */
    private UserTagsRsp commonUserTags(String phoneNumber, List<String> tagList) throws Exception {
    	Map<String, Object> expandMap = new HashMap<String, Object>();
        expandMap.put("MSISDN", phoneNumber);
        UserTagsReq userTagsReq = new UserTagsReq();
        List<Identity> identityList = new ArrayList<Identity>();
        Identity identity = new Identity();
        identity.setIdentityKey(phoneNumber);
        identity.setIdentityType(MktCampaignConst.CmbsMiguVideo.IDENTITY_TYPE_PHONE);
        identityList.add(identity);
        userTagsReq.setIdentityList(identityList);
        //设置远程调用request-tagList
        userTagsReq.setTagList(tagList);

        //拼接远程调用参数
        String clientId = "";
        String version = "";
        String uriStr = "";
        String signPrivatekey = "";
        CmbsStaticData clientIdData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.CLIENT_ID);
        CmbsStaticData versionData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.VSERSION);
        CmbsStaticData userTagsData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.CMBS_MIGU_VIDEO, StaticDataConst.StaticCodeName.GET_USER_TAGS_URL);
        CmbsStaticData privateKeyData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            StaticDataConst.StaticCodeType.MIGU_VIDEO_KEY, StaticDataConst.StaticCodeName.MIGU_VIDEO_PRIVATE_KEY);
        if (clientIdData != null) {
            clientId = clientIdData.getCodeValue();
        }
        if (versionData != null) {
            version = versionData.getCodeValue();
        }
        if (userTagsData != null) {
            uriStr = userTagsData.getCodeValue();
        }
        if (privateKeyData != null) {
            signPrivatekey = privateKeyData.getCodeValue(); 
        }
        String reqJsonStr = JsonUtil.objectToString(userTagsReq);
        
        String signStr = cmbsCommonHttpClient.processSign(reqJsonStr, signPrivatekey);
        URI uri = new URI(uriStr);

        //远程调用
        UserTagsRsp userTagsRsp = miguVideoFeignProxy.getUserTags(expandMap, uri,
            version, clientId, signStr, reqJsonStr);
        LOG.error("CmbsMiguVideoService.commonUserTags, responseStr=" + JsonUtil.objectToString(userTagsRsp)
            + ",uri=" + uriStr + " ,version=" + version + " ,clientId=" + clientId
            + " ,sign=" + signStr + " ,request=" + reqJsonStr);
        return userTagsRsp;
    }

    /**
     *<getUserTags>.
     *<活跃与沉默用户使用情况查询>
     * @param  request   [request]
     * @return [返回活跃与沉默用户使用情况]
     * @throws Exception [Exception]
     * @author jianghao
     */
    @Override
    public UserTagsAbilityRsp getUserTags(UserTagsAbilityReq request) throws Exception {
        //手机号处理
        String phoneNumber = request.getPhoneNumber();
        if (phoneNumber.length() <= 11) {
            phoneNumber = MktCampaignConst.CmbsMiguVideo.PHONE_NUM_PREFIX + phoneNumber;
        }

        String subType = request.getSubType();
        UserTagsAbilityRsp response = new UserTagsAbilityRsp();
        switch (subType) {
            case MktCampaignConst.CmbsMiguVideo.SUB_TYPE_MIGU_VIDEO: {
                //活跃与沉默用户使用情况查询-视讯
                getVideoUserTags(phoneNumber, response);
            }
            break;
            default: {
                LOG.error("CmbsMiguVideoService.getUserTags error:"
                    + "活跃与沉默用户查询当前子公司不支持.");
                response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
                response.setBizDesc("活跃与沉默用户查询当前子公司不支持");
            }
            break;
        }
        return response;
    }

    /**
     *<getVideoUserTags>.
     *<活跃与沉默用户使用情况查询-视讯>
     * @param  phoneNumber   [phoneNumber]
     * @param  response   [response]
     * @throws Exception [Exception]
     * @author jianghao
     */
    private void getVideoUserTags(String phoneNumber, UserTagsAbilityRsp response) throws Exception {
        List<String> tagList = new ArrayList<String>();
        tagList.add(MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_LOGIN_SILENT_USER);
        tagList.add(MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_LOGIN_ACTIVER_USER);

        //设置response
        try {
            UserTagsRsp userTagsRsp = commonUserTags(phoneNumber, tagList);
            String resultCode = userTagsRsp.getResultCode();
            String resultDesc = userTagsRsp.getResultDesc();
            if (IResultCode.SUCCESS_MESSAGE.equalsIgnoreCase(resultCode)) {
                response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_SUCCESS_CODE);
                response.setBizDesc(resultDesc);
            }
            else {
                response.setBizCode(resultCode);
                response.setBizDesc(resultDesc);
            }
            if (!CheckEnumUtil.checkActivityAndSilentRsp(response.getBizCode())) {
                response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            }
            List<Tags> tagsList = userTagsRsp.getTags();
            if (CommonUtil.isNotEmptyCollection(tagsList)) {
                for (Tags tags : tagsList) {
                    List<TagInfo> identityTags = tags.getIdentityTags();
                    if (CommonUtil.isNotEmptyCollection(identityTags)) {
                        for (TagInfo tagInfo : identityTags) {
                            String tagKey = tagInfo.getTagKey();
                            String tagValue = tagInfo.getTagValue();
                            if (MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_LOGIN_SILENT_USER.equals(tagKey)
                                && "true".equals(tagValue)) {
                                response.setIsActivitive(MktCampaignConst.CmbsMiguVideo.SILENT_USER);
                            }
                            else if (MktCampaignConst.CmbsMiguVideo.MIGUVIDEO_LOGIN_ACTIVER_USER.equals(tagKey)
                                && "true".equals(tagValue)) {
                                response.setIsActivitive(MktCampaignConst.CmbsMiguVideo.ACTIVER_USER);
                            }
                        }
                    }
                }
            }
            //既不是活跃用户与也不是沉默用户特殊处理
            if (IResultCode.ABILITY_ACTIVITY_SILENT_USER_SUCCESS_CODE.equals(response.getBizCode())
                && StringUtils.isBlank(response.getIsActivitive())) {
                response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_NOT_MIGU);
                response.setBizDesc("不是咪咕用户或无活跃状态");
                response.setIsActivitive(MktCampaignConst.CmbsMiguVideo.SILENT_USER);
            }
        }
        catch (Exception e) {
            LOG.error("CmbsMiguVideoService.getVideoUserTags error:", e);
            response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            response.setBizDesc("一级能开调视讯活跃与沉默用户使用情况远程调用失败");
        }
    }

    /**
     *<checkBizQualification>.
     *<一级能开业务办理资格校验>
     * @param  request   [request]
     * @return [返回校验结果]
     * @throws Exception [Exception]
     * @author jianghao
     */
    @Override
    public CmbsBizQualificationRsp checkBizQualification(
        CmbsBizQualificationReq request) throws Exception {
        //没有实现类相关配置默认校验成功
        CmbsBizQualificationRsp response = new CmbsBizQualificationRsp();
        response.setBizCode(IResultCode.ABILITY_BIZ_QUALIFICATION_SUCCESS_CODE);
        response.setBizDesc(IResultCode.ABILITY_BIZ_QUALIFICATION_SUCCESS_DESC);

        //获取产品,channelCode,实现类,业务处理
        String channelCode = "";
        String extProductCode = request.getGoodsId();
        CmbsProd cmbsProd = cmbsProdMapper.selectByCmbsProdExtCode(extProductCode);
        if (cmbsProd != null) {
            Long productId = cmbsProd.getProductId();
            //获取channelCode
            List<CmbsProdChar> cmbsProdCharList = cmbsProdService.getCmbsProdCharsByProductId(productId);
            if (CommonUtil.isNotEmptyCollection(cmbsProdCharList)) {
                for (CmbsProdChar cmbsProdChar : cmbsProdCharList) {
                    String specCharCode = cmbsProdChar.getSpecCharCode();
                    if (MktCampaignConst.CmbsMiguVideo.ABILITY_CHANNEL_CODE.equals(specCharCode)) {
                        channelCode = cmbsProdChar.getVal();
                        break;
                    }
                }
                //获取实现类, 业务处理
                if (StringUtils.isNotBlank(channelCode)) {
                    response = processBizQualification(request, channelCode, cmbsProd, response);
                }
            }
        }
        return response;
    }

    /**
     *<processBizQualification>.
     *<获取业务资格校验实现类,业务处理>
     * @param  request   [request]
     * @param  channelCode   [channelCode]
     * @param  cmbsProd   [cmbsProd]
     * @param  response   [response]
     * @return [返回校验结果]
     * @author jianghao
     */
    private CmbsBizQualificationRsp processBizQualification(CmbsBizQualificationReq request,
        String channelCode, CmbsProd cmbsProd, CmbsBizQualificationRsp response) {
        //获取产品渠道能力配置
        CmbsProdChannelAbilityCfg cmbsProdChannelAbilityCfg
            = cmbsProdService.getCmbsProdChannelAbilityCfgByProductIdAndChannelAndType(
            cmbsProd.getProductId(), channelCode,
            MktCampaignConst.CmbsMiguVideo.ABILITY_QUERY_OPER_TYPE);
        if (cmbsProdChannelAbilityCfg != null
            && StringUtils.isNotBlank(cmbsProdChannelAbilityCfg.getProcessName())) {
            String processName = cmbsProdChannelAbilityCfg.getProcessName();
            String merchandiseId = cmbsProdChannelAbilityCfg.getProductCode();
            try {
                //获取实现类, 业务处理
                ICmbsQueryVideoDispatcherService cmbsBizQualificationService
                    = (ICmbsQueryVideoDispatcherService) SpringContextUtil.getBean(processName);
                response = cmbsBizQualificationService.processBiz(request, merchandiseId);
                if (response == null) {
                    LOG.error("CmbsMiguVideoService.processBizQualification error:"
                        + "一级能开业务办理资格校验失败, responseStr=" + JsonUtil.objectToString(response));
                    response = new CmbsBizQualificationRsp();
                    response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
                    response.setBizDesc("一级能开业务办理资格校验参数非法");
                    return response;
                }
            }
            catch (Exception e) {
                LOG.error("CmbsMiguVideoService.processBizQualification error:"
                    + "一级能开业务办理资格校验失败," + " erromrMessage=" + e.getMessage(), e);
                response = new CmbsBizQualificationRsp();
                response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
                response.setBizDesc("一级能开业务办理资格校验参数非法");
                return response;
            }
        }
        return response;
    }
}
