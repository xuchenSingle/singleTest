package com.migu.redstone.order.controller;

import com.migu.redstone.order.service.dto.request.CheckNumberStatusReq;
import com.migu.redstone.order.service.dto.response.CheckNumberStatusResp;
import com.migu.redstone.order.service.interfaces.ICheckNumberStatusService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 号码状态校验
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.controller
 * @类名称 CheckNumberStatusController
 * @类描述 号码状态校验
 * @创建人 zhaoke
 * @创建时间 2019年4月2日 下午3:10:29
 */
@RestController
@RequestMapping(value = "/cmbs/query")
public class CheckNumberStatusController {

    @Autowired
    private ICheckNumberStatusService checkNumberStatusService;

    @RequestMapping(value = "/checkNumberStatus", produces = {"application/json"}, method = RequestMethod.POST)
    @ApiOperation(value = "一级能开校验号码状态", notes = "一级能开校验号码状态", response = CheckNumberStatusResp.class, tags = {
            "cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = CheckNumberStatusResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = CheckNumberStatusResp.class)})
    public CheckNumberStatusResp checkNumberStatus(@RequestBody @Validated CheckNumberStatusReq req) throws Exception {
        return checkNumberStatusService.checkNumberStatus(req);
    }
}
