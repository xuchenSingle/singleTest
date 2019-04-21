package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Action
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {

    /**
     * 营销活动唯一编码
     * 必填
     */
    private String actionId;

    /**
     * 营销活动名称
     * 必填
     */
    private String actionName;

    /**
     * 营销活动描述
     * 必填
     */
    private String actionDesc;

    /**
     * 营销活动生效时间
     * 生效时间YYYYMMDDHH24MISS
     * 必填
     */
    private String actionEffDate;

    /**
     * 营销活动失效时间
     * 失效时间YYYYMMDDHH24MISS
     * 必填
     */
    private String actionExpDate;
}
