/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    日志查询弹框_查询订单项信息po
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.model
* 类名称:    QueryOrderItemWithLog
* 类描述:    日志查询弹框_查询订单项信息po
* 创建人:    jianghao
* 创建时间:   2019年3月7日 下午10:07:19
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderItemWithLog {
    /**
     * 外部渠道.
     */
    private String extChannelCode;

    /**
     * 处理状态:成功/失败等等.
     */
    private String processStatus;

    /**
     * 回调状态:成功/失败.
     */
    private String notifyStatus;

    /**
     * 上游返回的异常原因.
     */
    private String failMsg;

    /**
     * 上游状态码.
     */
    private String upResultCode;
}
