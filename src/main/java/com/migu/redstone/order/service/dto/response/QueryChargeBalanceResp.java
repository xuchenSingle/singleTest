/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;

/**
 *
 *广东省分查询账户余额 （广东接口，提供给视讯使用）
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.response
 * @类名称 QueryChargeBalanceResp
 * @类描述 广东省分查询账户余额 （广东接口，提供给视讯使用）
 * @创建人 xuchen
 * @创建时间 2018年11月21日 下午3:18:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryChargeBalanceResp {

    /**
     * result
     */
    private Result result = Result.success();

    /**
     * 当前用户总余额
     */
    private String totalAvailableAmt;


    /**
     * 当前用户可用余额
     */
    private String currentAvailableAmt;

}
