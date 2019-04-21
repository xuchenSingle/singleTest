/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import com.migu.redstone.dto.Header;

/**
* 类作用:    查询单个子订单请求
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    QuerySubOrderReq
* 类描述:    查询单个子订单请求
* 创建人:    jianghao
* 创建时间:   2018年11月5日 下午12:31:50
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySubOrderReq {
    /**
     * 公共请求头.
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private Header requestHeader;

    /**
     * 手机号.
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, min = 1, message = "参数phoneNumber长度最长11位")
    private String phoneNumber;

    /**
     * 子订单ID（TSG统一能力平台）.
     */
    @NotBlank(message = "参数subOrderId不能为空")
    @Length(max = 32, min = 1, message = "参数subOrderId长度最长32位")
    private String subOrderId;
}
