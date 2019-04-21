/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    业务办理资格校验远程调用req
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    RulesWorkerCheckReq
* 类描述:    业务办理资格校验远程调用req
* 创建人:    jianghao
* 创建时间:   2018年12月2日 上午10:42:53
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RulesWorkerCheckReq {
    /**
     * ⽤用户⼿手机号码.
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    private String phoneNumber;

    /**
     * 商品id.
     */
    @NotBlank(message = "参数merchandiseId不能为空")
    private String merchandiseId;
}
