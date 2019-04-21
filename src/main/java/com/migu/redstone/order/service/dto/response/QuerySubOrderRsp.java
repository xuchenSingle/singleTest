/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.business.order.service.dto.model.SubOrderInfo;
import com.migu.redstone.entity.Result;
/**
* 类作用:    查询单个子订单response
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.response
* 类名称:    QuerySubOrderRsp
* 类描述:    查询单个子订单response
* 创建人:    jianghao
* 创建时间:   2018年11月5日 上午11:32:57
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySubOrderRsp {
    /**
     * result.
     */
    private Result result = Result.success();

    /**
     * orderInfo.
     */
    private SubOrderInfo orderInfo;
}
