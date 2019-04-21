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
* 类作用:    Identity
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.model
* 类名称:    Identity
* 类描述:    Identity
* 创建人:    jianghao
* 创建时间:   2018年11月29日 上午12:35:10
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Identity {
    /**
     * identityKey
     */
    private String identityKey;
    /**
     * identityType
     */
    private String identityType;
}
