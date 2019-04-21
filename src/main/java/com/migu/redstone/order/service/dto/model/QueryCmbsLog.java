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
* 类作用:    查询日志的po
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.model
* 类名称:    QueryCmbsLog
* 类描述:    查询日志的po
* 创建人:    jianghao
* 创建时间:   2019年3月6日 下午2:42:09
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCmbsLog {
    /**
     * 方法名.
     */
    private String methodName;

    /**
     * 入参.
     */
    private String params;

    /**
     * 出参.
     */
    private String response;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 耗时
     */
    private String costTime;

    /**
     * 用户归属.
     */
    private String provinceName;

    /**
     * 订单id
     */
    private String orderId;
}
