/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.controller;

import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.*;
import com.migu.redstone.order.service.dto.response.*;
import com.migu.redstone.order.service.interfaces.ICmbsDataMartService;
import com.migu.redstone.order.service.interfaces.ICmbsMiguVideoService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryJsService;
import com.migu.redstone.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 江苏接口服务
 */
@RestController
@Api(value = "江苏查询服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsQueryJsController {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(
            CmbsQueryJsController.class);

    /**
     * cmbsMiguVodeoService.
     */
    @Autowired
    private ICmbsMiguVideoService cmbsMiguVodeoService;

    /**
     * cmbsJsQueryService
     */
    @Autowired
    private ICmbsQueryJsService cmbsQueryJsService;

    /**
     * cmbsDataMartService
     */
    @Autowired
    private ICmbsDataMartService cmbsDataMartService;

    /**
     * 单推消息模块（提供给江苏使用） TSG能力对接平台 请求 视讯推送平台
     *
     * @param req PushSingleMsgReq
     * @return resp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/pushSingleMsg", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "单推消息模板", notes = "单推消息模板", response = PushSingleMsgResp.class, tags = {"cmbs_js",})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = PushSingleMsgResp.class)})
    public PushSingleMsgResp pushSingleMsgResp(@RequestBody @Validated PushSingleMsgReq req)
            throws IllegalAccessException, InvocationTargetException {
        PushSingleMsgResp resp = new PushSingleMsgResp();
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConst.DATEFORMAT.DATETIME_FORMAT);
        if(StringUtils.isNotBlank(req.getStartTime())) {
            try {
                Date date = dateFormat.parse(req.getStartTime());
            } catch (ParseException e) {
                resp.setResult(new Result(IResultCode.CMBS_REQUIRED_PATAM_IS_NULL, "参数startTime格式不正确"));
                return resp;
            }
        }
        return cmbsQueryJsService.pushSingleMsg(req);
    }

    /**
     * 手机号群推消息模板接口（提供给江苏使用） TSG能力对接平台 请求 视讯推送平台
     *
     * @param req [PushListMsgReq]
     * @return resp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/pushListMsg", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "手机号群推消息模板", notes = "手机号群推消息模板", response = PushListMsgResp.class, tags = {"cmbs_js",})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = PushListMsgResp.class)})
    public PushListMsgResp pushListMsgResp(@RequestBody @Validated PushListMsgReq req)
            throws IllegalAccessException, InvocationTargetException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConst.DATEFORMAT.DATETIME_FORMAT);
        PushListMsgResp resp = new PushListMsgResp();
        if(StringUtils.isNotBlank(req.getStartTime())) {
            try {
                Date date = dateFormat.parse(req.getStartTime());
            } catch (ParseException e) {
                resp.setResult(new Result(IResultCode.CMBS_REQUIRED_PATAM_IS_NULL, "参数startTime应为yyyy-MM-dd HH:mm:ss"));
                return resp;
            }
        }
        return cmbsQueryJsService.pushListMsg(req);
    }

    /**
     * <isNewUserAndMember>.
     * <获取用户是否是会员以及新用户(江苏调视讯用户标签)>
     *
     * @param request [request]
     * @return [返回查询结果]
     * @throws Exception [Exception]
     * @author jianghao
     */
    @RequestMapping(value = "/isNewUserAndMember", produces = {"application/json"},
            method = RequestMethod.POST)
    @ApiOperation(value = "江苏调视讯获取用户是否是会员以及新用户", notes = "江苏调视讯获取用户是否是会员以及新用户",
            response = QueryUserTagsResp.class, tags = {"cmbs_js",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回", response = QueryUserTagsResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryUserTagsResp.class)})
    public QueryUserTagsResp isNewUserAndMember(
            @RequestBody @Validated QueryUserTagsReq request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.error("CmbsQueryJsController.isNewUserAndMember startTime=" + startTime
                + " ,request=" + JsonUtil.objectToString(request));

        QueryUserTagsResp response = cmbsMiguVodeoService.isNewUserAndMember(request);

        long endTime = System.currentTimeMillis();
        LOG.error("CmbsQueryJsController.isNewUserAndMember startTime=" + startTime
                + " ,endTime=" + endTime + " ,response=" + JsonUtil.objectToString(response));
        return response;
    }

    /**
     * 〈queryUserTagsByMsisdn〉.
     * 〈根据手机号码查询用户标签〉
     *
     * @param req [QueryUserTagsByMsisdnReq]
     * @return response [QueryUserTagsByMsisdnResp]
     * @throws Exception
     * @author yangyuan3
     */
    @RequestMapping(value = "/queryUserTagsByMsisdn", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "根据手机号码查询用户标签", notes = "根据手机号码查询用户标签", response = QueryUserTagsByMsisdnResp.class, tags = {"cmbs_js",})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryUserTagsByMsisdnResp.class)})
    public QueryUserTagsByMsisdnResp queryUserTagsByMsisdn(@RequestBody @Validated QueryUserTagsByMsisdnReq req)
            throws Exception {
        return cmbsDataMartService.queryUserTagsByMsisdn(req);
    }

    /**
     * 〈batchQueryUserTags〉.
     * 〈自定义条件批量查询用户标签〉
     *
     * @param req [BatchQueryUserTagsReq]
     * @return response [BatchQueryUserTagsResp]
     * @throws Exception
     * @author yangyuan3
     */
    @RequestMapping(value = "/batchQueryUserTags", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "自定义条件批量查询用户标签", notes = "自定义条件批量查询用户标签", response = BatchQueryUserTagsResp.class, tags = {"cmbs_js",})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = BatchQueryUserTagsResp.class)})
    public BatchQueryUserTagsResp batchQueryUserTags(@RequestBody @Validated BatchQueryUserTagsReq req)
            throws Exception {
        return cmbsDataMartService.batchQueryUserTags(req);
    }
}
