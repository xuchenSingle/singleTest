package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;

/**
 * 项目名称:  cmbs-query-order
 * 包:        com.migu.redstone.service.dto.model
 * 类名称:    QueryAccountBalanceResp
 * 类描述:    账户余额结果对象
 * 创建时间:   2018年11月27日 下午3:03:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryAccountBalanceResp implements Serializable {
    /**
     * 返回结果对象
     */
    private Result result;
    /**
     * 当前用户总余额，单位元，小数点后保持两位
     */
    private String totalAvailableAmt;
    /**
     * 当前用户可用余额，单位元，小数点后保持两位
     */
    private String currentAvailableAmt;
}
