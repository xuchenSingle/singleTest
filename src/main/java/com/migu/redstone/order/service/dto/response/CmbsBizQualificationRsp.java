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
* 类作用:    一级能开-业务办理资格校验response
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.response
* 类名称:    CmbsBizQualificationRsp
* 类描述:    一级能开-业务办理资格校验response
* 创建人:    jianghao
* 创建时间:   2018年11月30日 下午1:08:55
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmbsBizQualificationRsp {
    /**
     * 返回码.
     */
    private String bizCode;

    /**
     * 错误结果描述
     */
    private String bizDesc;
}
