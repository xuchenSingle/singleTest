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
 *渠道信息
 * @项目名称   cmbs-query-order
 * @包         com.migu.redstone.order.service.dto.model  
 * @类名称     ChannelInfo
 * @类描述     渠道信息
 * @创建人     xuchen
 * @创建时间   2018年11月16日 下午2:09:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetInfo {
    /**
     * 手机号.
     */
    private String retmsg;

    /**
     * rettype
     */
    private String rettype;
    /**
     * retcode
     */
    private String retcode;
}
