/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.migu.redstone.client.dto.response.GdQueryErrorRes;
import com.migu.redstone.dto.Header;
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.response.QueryResp;
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 类作用: 广东省查询相关信息 项目名称: cmbs-query-order 包: com.migu.redstone.order.controller 类名称: CmbsGDQueryController 类描述: 广东省查询相关信息
 * 创建人: xuhcen 创建时间: 2018年11月15日 上午11:15:08
 */
@RestController
@Api(value = "查询订单服务")
@RequestMapping(value = "/cmbs/query")
public class CmbsGDQueryController {
    /**
     * cmbsGDQueryService
     */
    @Autowired
    private ICmbsGDQueryService cmbsGDQueryService;
    
    @Autowired
    private TransportClient  client;

    /**
     *
     * 〈查询账户余额〉 〈查询账户余额〉
     * 
     * @param req
     *            [QueryGdServiceReq]
     * @throws Exception
     *             [Exception]
     * @return String
     */
    @RequestMapping(value = "/test/GD/queryAccountsBalance", produces = {
            "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "余额查询", notes = "余额查询", response = GdQueryErrorRes.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = GdQueryErrorRes.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = GdQueryErrorRes.class) })
    public String queryAccountsBalance(@RequestBody @Validated QueryGdServiceReq req) throws Exception {
        String response = cmbsGDQueryService.queryAccountsBalance(req);
        return response;
    }

    /**
     *
     * 〈查询账户余额〉 〈查询账户余额〉
     * 
     * @param req
     *            [QueryGdServiceReq]
     * @throws Exception
     *             [Exception]
     * @return String
     */
    @RequestMapping(value = "/test/GD/queryDHQLWMembers", produces = {
            "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "查询短号群聊网网内现有成员", notes = "查询短号群聊网网内现有成员", response = QueryResp.class, tags = {
            "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = QueryResp.class),
            @ApiResponse(code = 200, message = "Unexpected error", response = QueryResp.class) })
    public String queryDHQLWMembers(@RequestBody @Validated QueryGdServiceReq req) throws Exception {
        String response = cmbsGDQueryService.queryDHQLWMembers(req);
        return response;
    }

    /**
     *
     * 〈查询账户余额〉 〈查询账户余额〉
     * 
     * @param req
     *            [QueryGdServiceReq]
     * @throws Exception
     *             [Exception]
     * @return String
     */
    @RequestMapping(value = "/test/GD/queryAllFlow", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "全流量查询接口", notes = "全流量查询接口", response = QueryResp.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回"),
            @ApiResponse(code = 200, message = "Unexpected error") })
    public String queryAllFlow(@RequestBody @Validated QueryGdServiceReq req) throws Exception {
        String response = cmbsGDQueryService.queryAllFlow(req);
        return response;
    }
    
    @RequestMapping(value = "/test/ES/queryByElasticSearch", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "es测试接口", notes = "es测试接口", response = ResponseEntity.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回"),
            @ApiResponse(code = 200, message = "Unexpected error") })
    public ResponseEntity queryByElasticSearch(@RequestBody @Validated String  id) throws Exception {
    	GetResponse result=client.prepareGet("people", "info", id).get();
        return new ResponseEntity(result.getSource(),HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/test/ES/addForElasticSearch", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "es测试接口", notes = "es测试接口", response = ResponseEntity.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回"),
            @ApiResponse(code = 200, message = "Unexpected error") })
    public ResponseEntity addForElasticSearch(@RequestBody @Validated Header  id) throws Exception {
    	
    	JSONObject  json=(JSONObject) JSONObject.toJSON(id);
    	IndexResponse result=client.prepareIndex("book", "test").setSource(json).get();
    	
        return new ResponseEntity(result.getId(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/test/ES/deleteFromElasticSearch", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "es测试接口", notes = "es测试接口", response = ResponseEntity.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回"),
            @ApiResponse(code = 200, message = "Unexpected error") })
    public ResponseEntity deleteFromElasticSearch(@RequestBody @Validated String  id) throws Exception {
    	
//    	JSONObject  json=(JSONObject) JSONObject.toJSON(id);
    	DeleteResponse result=client.prepareDelete("people","info", id).get();
    	
        return new ResponseEntity(result.getId(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/test/ES/updateForElasticSearch", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "es测试接口", notes = "es测试接口", response = ResponseEntity.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回"),
            @ApiResponse(code = 200, message = "Unexpected error") })
    public ResponseEntity updateForElasticSearch(@RequestBody @Validated Header head, String  id) throws Exception {
    	
    	JSONObject  json=(JSONObject) JSONObject.toJSON(head);
    	UpdateRequest   req=new UpdateRequest("people","info", id);
    	req.doc(json);
    	UpdateResponse  result = client.update(req).get();
    	
        return new ResponseEntity(result.getId(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/test/ESqueryComplexElasticSearch", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "es测试接口", notes = "es测试接口", response = ResponseEntity.class, tags = { "Cmbs_Query_GD", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回"),
            @ApiResponse(code = 200, message = "Unexpected error") })
    public ResponseEntity ESqueryComplexElasticSearch(@RequestParam("version") String version,@RequestParam("companyId")  String  companyId) throws Exception {
    	
    	BoolQueryBuilder query=QueryBuilders.boolQuery();
    	
    	query.must(QueryBuilders.matchQuery("version", version));
    	query.must(QueryBuilders.matchQuery("companyId", companyId));
//    	JSONObject  json=(JSONObject) JSONObject.toJSON(head);
//    	UpdateRequest   req=new UpdateRequest("people","info", id);
//    	req.doc(json);
    	SearchResponse  result = client.prepareSearch("people").setQuery(query).get();
    	
    	List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
    	for(SearchHit hit:result.getHits()) {
    		list.add(hit.getSource());
    	}
    	
        return new ResponseEntity(list,HttpStatus.OK);
    }
}
