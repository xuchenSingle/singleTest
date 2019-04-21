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

import com.migu.redstone.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    查询宽带订单request
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    QueryBroadbandOrderReq
* 类描述:    查询宽带订单request
* 创建人:    jianghao
* 创建时间:   2019年1月8日 下午5:13:58
*/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryBroadbandOrderReq {
    /**
     * 公共请求头.
     */
	@Valid
    @NotNull(message="参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * 专业能开的子订单id.
     */
    @NotBlank(message="参数subOrderId不能为空")
    @Length(max=32, min=1, message="参数subOrderId长度最长32位")
    private String subOrderId;

    /**
     * 1：宽带网销订单;
     * 2：宽带预约订单.
     */
    @NotBlank(message="参数orderType不能为空")
    @Length(max=5, min=1, message="参数subOrderId长度最长5位")
    private String orderType;
}
