package com.migu.redstone.order.service.dto.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广东查询家庭网成员家庭成员信息
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.model
 * @类名称 MemberInfo
 * @类描述 广东查询家庭网成员家庭成员信息
 * @创建人 zhengzm3
 * @创建时间 2018年11月23日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {

    /**
     * 手机号码
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private String phoneNumber;

    /**
     * 成员短号
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private String shortNumber;

    /**
     * 是否成为主号
     * 1：主号
     * 0：副号
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private String isMainNumber;

    /**
     * 生效时间
     * 时间格式为：yyyyMMddHHmmss
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private String invalidDate;

    /**
     * 失效时间
     * 时间格式为：yyyyMMddHHmmss
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private String expireDate;
}
