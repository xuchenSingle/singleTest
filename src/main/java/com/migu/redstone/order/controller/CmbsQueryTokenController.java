package com.migu.redstone.order.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.migu.redstone.common.dto.AccessTokenRsp;
import com.migu.redstone.common.dto.QueryTokenResp;
import com.migu.redstone.common.dto.YJPlatFormAccessTokenReq;
import com.migu.redstone.common.interfaces.ICmbsQueryTokenService;

/**
 * CmbsQueryTokenController
 */
@RestController
@Api(value = "查询Token服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsQueryTokenController {

    /**
     * cmbsQueryTokenService
     */
    @Autowired
    private ICmbsQueryTokenService cmbsQueryTokenService;

    /**
     * queryToken
     * @param appKey [String]
     * @param tmpSecret [String]
     * @param timestamp [String]
     * @return QueryTokenResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/queryToken", method = RequestMethod.GET)
    @ApiOperation(value = "Token查询", notes = "Token查询", response = QueryTokenResp.class, tags = {"Cmbs_Query_Token", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryTokenResp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QueryTokenResp.class)})
    public QueryTokenResp queryToken(String appKey, String tmpSecret, String timestamp) throws Exception {

        QueryTokenResp resp = new QueryTokenResp();
        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(tmpSecret) || StringUtils.isBlank(timestamp)) {
            return resp;
        }
        resp = cmbsQueryTokenService.queryToken(appKey, tmpSecret, timestamp);
        return resp;
    }

    /**
     * queryToken
     * @param req [YJPlatFormAccessTokenReq]
     * @return AccessTokenRsp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/api/v2/auth/access-tokens", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "申请token", notes = "申请token", response = QueryTokenResp.class, tags = {"Cmbs_Query_Token", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryTokenResp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = QueryTokenResp.class)})
    public AccessTokenRsp accessTokens(@RequestBody YJPlatFormAccessTokenReq req) throws Exception {

        AccessTokenRsp resp = new AccessTokenRsp();
        if (StringUtils.isBlank(req.getAppKey()) || StringUtils.isBlank(req.getTmpSecret())) {
            return resp;
        }
        resp = cmbsQueryTokenService.accessTokens(req);
        return resp;
    }


}
