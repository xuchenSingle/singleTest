/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package com.migu.redstone.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.migu.redstone.common.dto.CheckBusinessQualificationResp;
import com.migu.redstone.common.dto.QualificationCheckReq;
import com.migu.redstone.common.interfaces.IQualificationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;




/**
 * 业务办理资格校验
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.controller
 * @类名称 QualificationController
 * @类描述 业务办理资格校验
 * @创建人 xuchen
 * @创建时间 2018年11月27日 下午3:10:29
 */
@RestController
@RequestMapping(value = "/cmbs/query")
public class QualificationController {
    /**
     * qualificationService
     */
    @Autowired
    private IQualificationService qualificationService;

    /**
     * qualificationCheck
     * @param qualificationCheckReq 业务办理资格校验
     * @return CheckBusinessQualificationResp
     * @throws Exception [Exception]
     */
    @RequestMapping(value = "/test/qualification", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "业务办理资格校验", notes = "业务办理资格校验", response = CheckBusinessQualificationResp.class, tags = {
        "Cmbs_Query_YJPlatform", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = CheckBusinessQualificationResp.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = CheckBusinessQualificationResp.class)})
    public CheckBusinessQualificationResp qualificationCheck(
        @RequestBody @Validated QualificationCheckReq qualificationCheckReq) throws Exception {
        return qualificationService.qualificationCheck(qualificationCheckReq);
    }


}
