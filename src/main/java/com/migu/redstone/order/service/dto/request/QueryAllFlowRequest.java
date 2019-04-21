/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.GDRequestHeader;


/**
 * 
 * 查询流量请求类
 * @项目名称   cmbs-query-order
 * @包         com.migu.redstone.order.service.dto.request  
 * @类名称     QueryAllFlowRequest
 * @类描述     查询流量请求类
 * @创建人     xuchen
 * @创建时间   2018年11月21日 下午4:22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryAllFlowRequest {

    /**
     * requestHeader.
     */
    @NotNull(message = "参数gdRequestHeader不能为空")
    @Valid
    private GDRequestHeader gdRequestHeader;

    /**
     * phoneNumber
     */
    @Valid
    @NotNull(message = "参数phoneNumber不能为空")
    private String  phoneNumber;

}
