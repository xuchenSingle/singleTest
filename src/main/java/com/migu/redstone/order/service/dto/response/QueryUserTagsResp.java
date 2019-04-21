/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;
/**
* 类作用:    江苏-视讯:获取用户是否是会员以及是否是新用户response
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.response
* 类名称:    QueryUserTagsResp
* 类描述:    江苏-视讯:获取用户是否是会员以及是否是新用户response
* 创建人:    jianghao
* 创建时间:   2018年12月4日 下午6:15:06
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserTagsResp {
    /**
     * 返回结果对象.
     */
    private Result result;

    /**
     * 标记是否为新用户;true：新用户;false：老用户
     */
    private Boolean isNew;

    /**
     * 是否为视讯会员:true：会员; false：非会员
     */
    private Boolean isMember;
}
