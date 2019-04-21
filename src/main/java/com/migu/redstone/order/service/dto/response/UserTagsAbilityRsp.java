/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    一级能开-用户标签查询response
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.response
* 类名称:    UserTagsAbilityRsp
* 类描述:    一级能开-用户标签查询response
* 创建人:    jianghao
* 创建时间:   2018年12月3日 下午1:24:20
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagsAbilityRsp {
    /**
     * 返回码(0000：成功（是咪咕用户）; 0001：不是咪咕用户; 0002：其他原因失败).
     */
    private String bizCode;

    /**
     * 错误结果描述.
     */
    private String bizDesc;

    /**
     * 1为是活跃用户,0为沉默用户.
     */
    private String isActivitive;
}
