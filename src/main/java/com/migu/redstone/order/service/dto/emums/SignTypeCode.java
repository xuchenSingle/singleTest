package com.migu.redstone.order.service.dto.emums;

/**
 * 标志类型code
 *      适用：账号余额查询
 *            按月查询流量使用情况
 * 01：手机号码 02：固话号码 03：宽带帐号 04：vip卡号 05：集团编 码；本期只有01：手机号码
 */
public enum SignTypeCode {
    /**
     * 手机号码
     */
    PHONE_NUMBER("01"),
    /**
     * 固定电话
     */
    FIXED_TELEPHONE("02"),
    /**
     * 宽带账号
     */
    BROADBAND_ACCOUNT("03"),
    /**
     * vip卡号
     */
    VIPCARD_NUMBER("04"),
    /**
     * 集团编码
     */
    GROUP_CODE("05");

    private String serviceType;

    private SignTypeCode(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

}
