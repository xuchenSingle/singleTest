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
 * 广东省分查询账户余额
 * @项目名称   cmbs-query-order
 * @包         com.migu.redstone.order.service.dto.request  
 * @类名称     QueryAccountsBalanceReq
 * @类描述   广东省分查询账户余额
 * @创建人     xuchen
 * @创建时间   2018年11月16日 下午2:01:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryGdServiceReq {
/*    @Valid
    @NotNull(message="参数requestHeader不能为空")
    private Header requestHeader;*/
    /**
     * requestHeader.
     */
//    @NotNull(message = "参数gdRequestHeader不能为空")
//    @Valid
    private GDRequestHeader gdRequestHeader;

    /**
     * phoneNumber
     */
    @Valid
    @NotNull(message = "参数phoneNumber不能为空")
    private String  phoneNumber;

    /**
     * menuid
     */
    @Valid
    @NotNull(message = "参数menuid不能为空")
    private String menuid;

    /**
     * process_code
     */
    @Valid
    @NotNull(message = "参数process_code不能为空")
    private String process_code;

    /**
     * operatorid
     */
    @Valid
    @NotNull(message = "参数operatorid不能为空")
    private String operatorid;

    /**
     * channelid
     */
    @Valid
    @NotNull(message = "参数channelid不能为空")
    private String channelid;

    /**
     * unitid
     */
    @Valid
    @NotNull(message = "参数unitid不能为空")
    private String unitid;
}
