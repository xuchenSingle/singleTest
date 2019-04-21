package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QueryOrderBusinessReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderBusinessReq {
    /**
     * 标识类型：
     * 01-手机号码
     * 02-固话号码
     * 03-宽带账号
     * 04-vip卡号
     * 05-集团编码
     */
    @NotNull(message = "参数serviceType不能为空")
    @Valid
    private String serviceType;

    /**
     * 标识号码
     */
    @NotNull(message = "参数serviceNumber不能为空")
    @Valid
    private String serviceNumber;

    /**
     * 产品类型：
     * 01-套餐类
     * 02-增值业务类
     * 03-服务功能类
     * 04-营销活动等其他类
     */
    @NotNull(message = "参数busiType不能为空")
    @Valid
    private String busiType;

}
