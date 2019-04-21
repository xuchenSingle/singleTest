/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.migu.redstone.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 查询流量使用情况
 * 
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.request
 * @类名称 QueryExclusiveFlowUseReq
 * @类描述 查询流量使用情况
 * @创建人 xuchen
 * @创建时间 2018年11月6日 下午2:06:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFlowInfoReq {
    /**
     * requestHeader.
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private Header requestHeader;

    /**
     * phoneNumber.
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, min = 11, message = "参数phoneNumber长度11位")
    private String phoneNumber;

    /**
     * phoneNumber.
     */
    @NotBlank(message = "参数queryType不能为空")
    @Length(max = 6, min = 1, message = "参数queryType长度最长6位")
    private String queryType;
}
