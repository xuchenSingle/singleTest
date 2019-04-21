/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.QuerySubOrderReq;
import com.migu.redstone.order.service.dto.response.QuerySubOrderRsp;

/**
* 类作用:    cmbs-query-order服务接口
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.interfaces
* 类名称:    ICmbsQueryOrderService
* 类描述:    cmbs-query-order服务接口
* 创建人:    jianghao
* 创建时间:   2018年11月5日 下午12:48:40
*/
public interface ICmbsQueryOrderService {
    /**
    *<querySubOrder>.
    *<查询单个子订单信息>
    * @param  request   [request]
    * @return [返回订单信息]
    * @throws Exception [Exception]
    * @author jianghao
    */
    QuerySubOrderRsp querySubOrder(QuerySubOrderReq request) throws Exception;
}
