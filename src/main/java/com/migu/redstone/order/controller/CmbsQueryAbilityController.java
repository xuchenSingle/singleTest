/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.migu.redstone.client.dto.response.UserTagsRsp;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.order.service.dto.model.BizQualificationProd;
import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.request.QueryProcessedMemberReq;
import com.migu.redstone.order.service.dto.request.UserTagsAbilityReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;
import com.migu.redstone.order.service.dto.response.QueryProcessedMemberRsp;
import com.migu.redstone.order.service.dto.response.UserTagsAbilityRsp;
import com.migu.redstone.order.service.interfaces.ICmbsMiguVideoService;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
* 类作用:    一级能开调视讯controller
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.controller
* 类名称:    CmbsQueryAbilityController
* 类描述:    一级能开调视讯controller
* 创建人:    jianghao
* 创建时间:   2018年11月22日 上午11:23:55
*/
@RestController
@Api(value = "一级能开调视讯查询服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsQueryAbilityController {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(
        CmbsQueryAbilityController.class);

    /**
     * cmbsMiguVideoService.
     */
    @Autowired
    private ICmbsMiguVideoService cmbsMiguVideoService;

    /**
    *<queryProcessedMember>.
    *<已办理会员查询(一级能开调视讯)>
    * @param  request  [request]
    * @return [返回已办理会员]
    * @throws Exception [Exception]
    * @author jianghao
    */
    @RequestMapping(value = "/ability/v1/biz/memberlevel", produces = { "application/json" },
        method = RequestMethod.POST)
    @ApiOperation(value = "一级能开调视讯已办理会员查询", notes = "一级能开调视讯已办理会员查询",
        response = QueryProcessedMemberRsp.class, tags = {"Cmbs_Query_Ability", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回", response = QueryProcessedMemberRsp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QueryProcessedMemberRsp.class) })
    public QueryProcessedMemberRsp queryProcessedMember(
        @RequestBody QueryProcessedMemberReq request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.error("CmbsQueryAbilityController.queryProcessedMember startTime=" + startTime
            + " ,request=" + JsonUtil.objectToString(request));

        QueryProcessedMemberRsp response = new QueryProcessedMemberRsp();

        //入参校验
        try {
            boolean flag = checkProcessedMember(request, response);
            if (!flag) {
                return response;
            }
        } catch(Exception e) {
            LOG.error("CmbsQueryAbilityController.queryProcessedMember error,"
                + " errorMessage=" + e.getMessage(), e);
            response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            response.setBizDesc("一级能开已办理会员查询参数非法");
            return response;
        }

        //已办理会员查询业务处理
        response = cmbsMiguVideoService.queryProcessedMember(request);

        long endTime = System.currentTimeMillis();
        LOG.error("CmbsQueryAbilityController.queryProcessedMember startTime=" + startTime
            + " ,endTime=" + endTime + " ,response=" + JsonUtil.objectToString(response));
        return response;
    }


    /**
    *<checkProcessedMember>.
    *<一级能开已办理会员查询请求参数校验>
    * @param  request   [request]
    * @param  response   [response]
    * @return [返回校验结果]
    * @author jianghao
    */
    private boolean checkProcessedMember(QueryProcessedMemberReq request,
        QueryProcessedMemberRsp response) {
        String phoneNumber = request.getPhoneNumber();
        String subType = request.getSubType();

        if (StringUtils.isBlank(phoneNumber)) {
            response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            response.setBizDesc("一级能开已办理会员查询参数phoneNumber不能为空");
            return false;
        }
        if (phoneNumber.length() > 11) {
            response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            response.setBizDesc("一级能开已办理会员查询参数phoneNumber最长11位");
            return false;
        }

        if (StringUtils.isBlank(subType)) {
            response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            response.setBizDesc("一级能开已办理会员查询参数subType不能为空");
            return false;
        }
        if (subType.length() > 2) {
            response.setBizCode(IResultCode.ABILITY_PROCESSED_MEMBER_OTHER_ERROR);
            response.setBizDesc("一级能开已办理会员查询参数subType最长2位");
            return false;
        }
        return true;
    }

    /**
    *<getUserTags>.
    *<一级能开业调视讯:活跃与沉默用户使用情况查询>
    * @param  request   [request]
    * @return [返回活跃与沉默用户使用情况]
    * @throws Exception [Exception]
    * @author jianghao
    */
    @RequestMapping(value = "/ability/v1/biz/user-activity", produces = { "application/json" },
        method = RequestMethod.POST)
    @ApiOperation(value = "一级能开调视讯活跃与沉默用户使用情况查询", notes = "一级能开调视讯活跃与沉默用户使用情况查询",
        response = UserTagsRsp.class, tags = {"Cmbs_Query_Ability", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回", response = UserTagsRsp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = UserTagsRsp.class) })
    public UserTagsAbilityRsp getUserTacgs(@RequestBody UserTagsAbilityReq request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.error("CmbsQueryAbilityController.getUserTags startTime=" + startTime
            + " ,request=" + JsonUtil.objectToString(request));
        UserTagsAbilityRsp response = new UserTagsAbilityRsp();

        //入参校验
        try {
            boolean flag = checkUserTagsRequest(request, response);
            if (!flag) {
                return response;
            }
        } catch(Exception e) {
            LOG.error("CmbsQueryAbilityController.getUserTags error,"
                + " erromrMessage=" + e.getMessage(), e);
            response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            response.setBizDesc("一级能开活跃与沉默用户使用情况请求参数非法");
            return response;
        }

        //一级能开:活跃与沉默用户使用情况查询业务处理
        response = cmbsMiguVideoService.getUserTags(request);

        long endTime = System.currentTimeMillis();
        LOG.error("CmbsQueryAbilityController.getUserTags startTime=" + startTime
            + " ,endTime=" + endTime + " ,response=" + JsonUtil.objectToString(response));
        return response;
    }

    /**
    *<checkUserTagsRequest>.
    *<一级能开:活跃与沉默用户使用情况查询请求参数校验>
    * @param  request   [request]
    * @param  response  [response]
    * @return [返回校验结果]
    * @author jianghao
    */
    private boolean checkUserTagsRequest(UserTagsAbilityReq request,
        UserTagsAbilityRsp response) {
        String phoneNumber = request.getPhoneNumber();
        String subType = request.getSubType();
        if (StringUtils.isBlank(phoneNumber)) {
            response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            response.setBizDesc("一级能开活跃与沉默用户使用情况参数phoneNumber不能为空");
            return false;
        }
        if (phoneNumber.length() > 11) {
            response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            response.setBizDesc("一级能开活跃与沉默用户使用情况参数phoneNumber最长11位");
            return false;
        }
        if (StringUtils.isBlank(subType)) {
            response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            response.setBizDesc("一级能开活跃与沉默用户使用情况参数subType不能为空");
            return false;
        }
        if (subType.length() > 2) {
            response.setBizCode(IResultCode.ABILITY_ACTIVITY_SILENT_USER_OTHER_ERROR);
            response.setBizDesc("一级能开活跃与沉默用户使用情况参数subType最长2位");
            return false;
        }
        return true;
    }

    /**
     *<checkBizQualification>.
     *<业务办理资格校验(一级能开业调视讯)>
     * @param  request   [request]
     * @return [返回校验结果 ]
     * @throws Exception [Exception]
     * @author jianghao
     */
    @RequestMapping(value = "/ability/v1/biz/qualification",
        produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "一级能开调视讯业务办理资格校验", notes = "一级能开调视讯业务办理资格校验",
        response = CmbsBizQualificationRsp.class, tags = {"Cmbs_Query_Ability", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "返回",
            response = CmbsBizQualificationRsp.class),
        @ApiResponse(code = 200, message = "Unexpected error",
            response = CmbsBizQualificationRsp.class)})
    public CmbsBizQualificationRsp checkBizQualification(
        @RequestBody CmbsBizQualificationReq request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.error("CmbsQueryAbilityController.checkBizQualification startTime=" + startTime
            + " ,request=" + JsonUtil.objectToString(request));

        CmbsBizQualificationRsp response = new CmbsBizQualificationRsp();

        try {
            //入参校验
            boolean flag = checkBizQuaficationRequest(request, response);
            if (!flag) {
                return response;
            }
        } catch(Exception e) {
            LOG.error("CmbsQueryAbilityController.checkBizQualification error,"
                + " erromrMessage=" + e.getMessage(), e);
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验请求参数非法");
            return response;
        }

        response = cmbsMiguVideoService.checkBizQualification(request);

        long endTime = System.currentTimeMillis();
        LOG.error("CmbsQueryAbilityController.checkBizQualification startTime=" + startTime
            + " ,endTime=" + endTime + " ,response=" + JsonUtil.objectToString(response));
        return response;
    }

    /**
    *<一级能开业务办理资格校验-request校验>.
    *<一级能开业务办理资格校验-request校验>
    * @param  request   [request]
    * @param  response   [response]
    * @return [返回校验结果]
    * @author jianghao
    */
    private boolean checkBizQuaficationRequest(CmbsBizQualificationReq request,
        CmbsBizQualificationRsp response) {
        String numberType = request.getNumberType();
        String goodsId = request.getGoodsId();
        String numberInfo = request.getNumberInfo();
        List<BizQualificationProd> productList = request.getProductList();

        if (StringUtils.isBlank(numberType)) {
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验参数numberType不能为空");
            return false;
        }
        if (numberType.length() > 2) {
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验参数numberType最长2位");
            return false;
        }

        if (StringUtils.isBlank(goodsId)) {
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验参数goodsId不能为空");
            return false;
        }
        if (goodsId.length() > 32) {
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验参数goodsId最长32位");
            return false;
        }

        if (StringUtils.isBlank(numberInfo)) {
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验参数numberInfo不能为空");
            return false;
        }
        if (numberInfo.length() > 11) {
            response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
            response.setBizDesc("一级能开业务办理资格校验参数numberInfo最长11位");
            return false;
        }

        if (CommonUtil.isNotEmptyCollection(productList)) {
            for (BizQualificationProd prod : productList) {
                String productId = prod.getProductId();
                String productType = prod.getProductType();
                if (StringUtils.isBlank(productId)) {
                    response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
                    response.setBizDesc("一级能开业务办理资格校验参数productList有数据时productId不能为空");
                    return false;
                }
                if (productId.length() > 32) {
                    response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
                    response.setBizDesc("一级能开业务办理资格校验参数productList有数据时productId最长32位");
                    return false;
                }

                if (StringUtils.isBlank(productType)) {
                    response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
                    response.setBizDesc("一级能开业务办理资格校验参数productList有数据时productType不能为空");
                    return false;
                }
                if (productType.length() > 32) {
                    response.setBizCode(IResultCode.ABILITY_BIZE_QUALIFICATION_OTHER_ERROR);
                    response.setBizDesc("一级能开业务办理资格校验参数productList有数据时productType最长32位");
                    return false;
                }
            }
        }
        return true;
    }
}
