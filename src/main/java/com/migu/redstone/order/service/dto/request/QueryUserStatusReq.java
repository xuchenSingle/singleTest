package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.Header;


/**
 * 查询用户状态请求信息
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.request
 * @类名称 QueryUserStatusReq
 * @类描述 查询用户状态请求信息
 * @创建人 xuchen
 * @创建时间 2018年12月8日 上午11:04:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserStatusReq {
    /**
     * requestHeader
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * phoneNumber
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, message = "参数phoneNumber长度最长11位")
    private String phoneNumber;
}
