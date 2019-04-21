package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.Header;


/**
 * 查询家庭网成员
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.request
 * @类名称 QueryFamilyMemberInfoReq
 * @类描述 查询家庭网成员
 * @创建人 zhengzm3
 * @创建时间 2018年11月23日
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFamilyMemberInfoReq {

    /**
     * requestHeader
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private Header requestHeader;

    /**
     * phoneNumber
     */
    @Valid
    @NotEmpty(message = "参数phoneNumber不能为空")
    private String phoneNumber;

}
