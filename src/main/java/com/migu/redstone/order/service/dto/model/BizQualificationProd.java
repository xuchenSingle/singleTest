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
* 类作用:    BizQualificationProd
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.model
* 类名称:    BizQualificationProd
* 类描述:    BizQualificationProd
* 创建人:    jianghao
* 创建时间:   2018年12月21日 下午5:18:39
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizQualificationProd {
    /**
     * productType
     */
    private String productType;
    /**
     * productId
     */
    private String productId;
    /**
     * serviceIdList
     */
    private String serviceIdList;
}
