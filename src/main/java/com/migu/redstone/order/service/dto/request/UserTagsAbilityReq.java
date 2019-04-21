/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    一级能开-用户标签查询request
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    UserTagsAbilityReq
* 类描述:    一级能开-用户标签查询request
* 创建人:    jianghao
* 创建时间:   2018年12月3日 下午1:18:34
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagsAbilityReq {
    /**
     * 手机号码.
     */
    private String phoneNumber;

    /**
     * 子公司会员分类.
     */
    private String subType;
}
