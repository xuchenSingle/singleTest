/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 *用户信息
 * @项目名称   cmbs-query-order
 * @包         com.migu.redstone.order.service.dto.model  
 * @类名称     UserInfo
 * @类描述     用户信息
 * @创建人     xuchen
 * @创建时间   2018年11月16日 下午2:09:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataBean {
    /**
     * 手机号.
     */
    private String totalbalnce;
    /**
     * monthbalnce.
     */
    private String monthbalnce;
    /**
     * monthrealtime.
     */
    private String monthrealtime;
    /**
     * availbalance.
     */
    private String availbalance;
    /**
     * balance.
     */
    private String balance;
    /**
     * giftamount.
     */
    private String giftamount;
    /**
     * cleardate.
     */
    private String cleardate;
}
