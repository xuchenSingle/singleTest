/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.*;
import com.migu.redstone.order.service.dto.response.*;

/**
 * 类作用:    cmbs-query-order服务接口
 * 项目名称:   cmbs-query-order
 * 包:       com.migu.redstone.order.service.interfaces
 * 类名称:    ICmbsQueryExclusiveFlowUseService
 * 类描述:    cmbs-query-order服务接
 * 创建人:    xuchen3
 * 创建时间:   2018年11月5日 下午12:48:40
 */
public interface ICmbsQueryYJService {

    /**
     *
     *〈查询流量使用百分比〉
     *〈查询流量使用百分比〉
     * @param queryExclusiveFlowUseReq [QueryFlowInfoReq]
     * @throws Exception [Exception]
     * @return QueryFlowInfoResp
     */
    QueryFlowInfoResp queryFlowInfo(QueryFlowInfoReq queryExclusiveFlowUseReq) throws Exception;

    /**
     * 查询话费余额（广东接口，提供给视讯使用）
     * @param req [QueryChargeBalanceReq]
     * @return QueryChargeBalanceResp
     * @throws Exception [Exception]
     */
    QueryChargeBalanceResp queryChargeBalance(QueryChargeBalanceReq req) throws Exception;

    /**
     * 查询家庭网成员信息(广东接口，提供给视讯使用)
     * @param req [QueryFamilyMemberInfoReq]
     * @return QueryFamilyMemberInfoResp
     * @throws Exception [Exception]
     */
    QueryFamilyMemberInfoResp queryFamilyMemberInfo(QueryFamilyMemberInfoReq req) throws Exception;

}
