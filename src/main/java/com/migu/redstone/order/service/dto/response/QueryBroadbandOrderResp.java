/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    查询宽带订单response
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.response
* 类名称:    QueryBroadbandOrderResp
* 类描述:    查询宽带订单response
* 创建人:    jianghao
* 创建时间:   2019年1月8日 下午5:19:34
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBroadbandOrderResp {
    /**
     * 返回结果对象.
     */
    private Result result;

    /**
     * 订单状态，(0,"已下单"),(1,"已提交"),(2,"待确认"), 
     * (3,"已确认"), (4, "办理成功"), (5,"等待中断"), 
     * (6,"已撤单"), (7,"已作废"), (8,"未知异常"), 
     * (9,"办理失败"), (10,"业务办理中"), (11,"已安装"), (-1, "其他");
     */
    private String orderStatus;

    /**
     * 订单状态描述.
     */
    private String statusMsg;
}
