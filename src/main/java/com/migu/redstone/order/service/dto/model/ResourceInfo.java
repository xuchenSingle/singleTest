package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResourceInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceInfo {
    /**
     * 01:语音
     * 02:短信
     * 03:彩信
     * 04:GPRS
     * 05:WLAN
     */
    private String resourceCode;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 总量
     */
    private String totalAmount;
    /**
     * 使用量
     */
    private String usedAmount;
    /**
     * 剩余量
     */
    private String remainAmount;
    /**
     * 生效时间，格式yyyyMMddHHmmss
     */
    private String validDate;
    /**
     * 单位
     * 01：分钟
     * 02：条数
     * 03：KB
     * 04：MB
     * 05：GB
     */
    private String unit;
}
