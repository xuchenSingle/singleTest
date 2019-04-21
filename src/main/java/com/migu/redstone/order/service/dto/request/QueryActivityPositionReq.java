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
 * 弹窗营销位请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryActivityPositionReq implements Serializable {

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
     * 是否是新用户 1：新用户，空：老用户
     */
    @Length(max = 1, message = "参数isNew长度最长1位")
    private String isNew;

    /**
     * 是否会员 1 : 是会员 空 : 不是会员
     */
    @Length(max = 1, message = "参数isMember长度最长1位")
    private String isMember;

    /**
     * 是否V15会员 1 : 是V15包月会员 空 : 不是V15包月会员
     */
    @Length(max = 1, message = "参数isV15长度最长1位")
    private String isV15;

    /**
     * 弹窗位置 1：首页，2：待后续定义，默认为1
     */
    @Length(max = 3, message = "参数locationid长度最长3位")
    private String locationid;

}
