/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.Header;


/**
 * 
 * 广东省分查询账户余额 （广东接口，提供给视讯使用）
 * @项目名称   cmbs-query-order
 * @包         com.migu.redstone.order.service.dto.request  
 * @类名称     QueryChargeBalanceReq
 * @类描述     广东省分查询账户余额 （广东接口，提供给视讯使用）
 * @创建人    xuchen
 * @创建时间   2018年11月21日 下午3:15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryChargeBalanceReq {
    /**
     * requestHeader.
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private Header requestHeader;

    /**
     * phoneNumber
     */
    @Valid
//    @NotNull(message = "参数phoneNumber不能为空")
    @NotEmpty(message = "参数phoneNumber不能为空")
    private String  phoneNumber;
}
