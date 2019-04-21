package com.migu.redstone.order.controller;

import com.migu.redstone.order.service.dto.request.QueryFamilyNetGroupReq;
import com.migu.redstone.order.service.dto.response.QueryFamilyNetGroupResp;
import com.migu.redstone.order.service.interfaces.IQueryFamilyNetGroupService;
import com.migu.redstone.utils.JsonUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cmbs/query")
public class QueryFamilyNetGroupController {

    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(QueryFamilyNetGroupController.class);

    /**
     * videoCustomService
     */
    @Autowired
    private IQueryFamilyNetGroupService queryFamilyNetGroupService;

    /**
     * 查询亲情网
     * @param queryFamilyNetGroupReq
     * @return queryFamilyNetGroupResp
     * @throws Exception
     */
    @RequestMapping(value = "/queryFamilyNetGroup", method = RequestMethod.POST, produces = { "application/json" })
    @ApiOperation(value = "查询亲情网", notes = "查询亲情网", tags = {"cmbs_video", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "返回", response = QueryFamilyNetGroupResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryFamilyNetGroupResp.class)})
    public QueryFamilyNetGroupResp queryFamilyNetGroup(@RequestBody @Validated QueryFamilyNetGroupReq queryFamilyNetGroupReq) throws Exception {
        LOG.error("CmbsVideoCustomController.queryFamilyNetGroup ,request= " + JsonUtil.objectToString(queryFamilyNetGroupReq));
        return queryFamilyNetGroupService.queryFamilyNetGroup(queryFamilyNetGroupReq);
    }
}
