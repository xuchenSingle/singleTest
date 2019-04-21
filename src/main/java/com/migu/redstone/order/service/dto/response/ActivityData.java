package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询营销活动数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityData implements Serializable {

    private static final long serialVersionUID = 9108837994613423925L;

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 活动标识
     */
    private String activityId;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订购时间，格式yyyyMMddHHmmss
     */
    private String orderTime;

    /**
     * 区域
     */
    private String area;

    /**
     * ip
     */
    private String ip;

    /**
     * 渠道标识
     */
    private String channelId;

}
