/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;

/**
 *
 * 广东相关服务查询功能接口
 * 
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.interfaces
 * @类名称 ICmbsGDQueryService
 * @类描述 广东相关服务查询功能接口
 * @创建人 xuhcen
 * @创建时间 2018年11月16日 下午2:36:09
 */
public interface ICmbsGDQueryService {

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
    String queryAccountsBalance(QueryGdServiceReq req) throws Exception;

    /**
     *
     * 〈查询短号群聊网网内现有成员〉 〈查询短号群聊网网内现有成员〉
     *
     * @throws Exception
     *             [Exception]
     * @param req
     *            [QueryGdServiceReq]
     * @return String
     */
    String queryDHQLWMembers(QueryGdServiceReq req) throws Exception;

    /**
     *
     * 〈查询流量信息〉 〈查询流量信息〉
     * 
     * @param req
     *            [QueryGdServiceReq]
     * @return String
     * @throws Exception
     *             [Exception]
     */
    String queryAllFlow(QueryGdServiceReq req) throws Exception;

}
