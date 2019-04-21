/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.Header;



/**
* 类作用:    江苏-视讯:获取用户是否是会员以及是否是新用户request.
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    QueryUserTagsReq
* 类描述:    江苏-视讯:获取用户是否是会员以及是否是新用户request
* 创建人:    jianghao
* 创建时间:   2018年12月4日 下午6:07:06
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserTagsReq {

    /**
     * 公共请求头.
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * 用户手机号
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    private String phoneNumber;
}
