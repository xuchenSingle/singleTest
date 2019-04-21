/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package com.migu.redstone.order.service.dto.request;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.Header;


/**
 * 营销活动数据请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryActivityDataReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * requestHeader
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * 手机号
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, message = "参数phoneNumber长度最长11位")
    private String phoneNumber;

    /**
     * 活动id
     */
    @NotBlank(message = "参数activityId不能为空")
    @Length(max = 32, message = "参数activityId长度最长32位")
    private String activityId;

}
