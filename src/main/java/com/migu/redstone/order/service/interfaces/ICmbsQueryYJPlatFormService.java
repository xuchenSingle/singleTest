package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.dto.OrderFeedbackReq;
import com.migu.redstone.order.service.dto.request.QueryYJPlatFormServiceReq;
import com.migu.redstone.order.service.dto.response.*;

/**
 * 类作用: cmbs-query-month-flow-use服务接口 项目名称: cmbs-query-order 包: com.migu.redstone.order.service.interfaces 类名称:
 * ICmbsQueryAccountService 类描述: cmbs-query-month-flow-use服务接口 创建人: wangfenglin 创建时间: 2018年11月27日 下午2:40:40
 */
public interface ICmbsQueryYJPlatFormService {
    /**
     * <queryMonthFlowUse>. <用户按月查询套餐的使用情况>
     *
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @return [返回使用情况]
     * @throws Exception
     *             [Exception]
     */
    QueryMonthFlowUseResponse queryPackageUseInfo(QueryYJPlatFormServiceReq queryAccountInfoReq) throws Exception;

    /**
     * <queryAcountBalance>. <查询账户余额信息>
     *
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @return [返回账户余额]
     * @throws Exception
     *             [Exception]
     */
    QueryChargeBalanceResp queryAcountBalance(QueryYJPlatFormServiceReq queryAccountInfoReq) throws Exception;

    /**
     * <queryPackageUseInfo>. <提供查询用户的套餐使用情况信息>
     *
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @param queryType
     *            [String]
     * @param string
     * @return [返回]
     * @throws Exception
     *             [Exception]
     */
    QueryFlowInfoResp queryFlowUseInfo(QueryYJPlatFormServiceReq queryAccountInfoReq, String queryType,
            String productLine) throws Exception;

    /**
     * <queryUserStatusService>. <请求查询用户目前的状态>
     *
     * @param queryAccountInfoReq
     *            [QueryAccountInfoReq]
     * @return [返回]
     * @throws Exception
     *             [Exception]
     */
    QueryUserStatusResp queryUserStatusService(QueryYJPlatFormServiceReq queryAccountInfoReq) throws Exception;

    /**
     * queryUserCallback
     * 
     * @param req
     *            [OrderFeedbackReq]
     * @return String
     * @throws Exception
     *             [Exception]
     */
//    String queryUserCallback(OrderFeedbackReq req) throws Exception;
}
