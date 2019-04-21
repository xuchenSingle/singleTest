/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.order.service.dto.model.Identity;



/**
* 类作用:    用户标签查询远程调用request
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    UserTagsReq
* 类描述:    用户标签查询远程调用request
* 创建人:    jianghao
* 创建时间:   2018年11月29日 上午12:30:58
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagsReq {
    /**
     * 用户身份标识组.
     */
    @NotNull(message = "参数identityList不能为空")
    private List<Identity> identityList;

    /**
     * 用户标签列表.
     */
    @NotNull(message = "参数tagList不能为空")
    private List<String> tagList;
}
