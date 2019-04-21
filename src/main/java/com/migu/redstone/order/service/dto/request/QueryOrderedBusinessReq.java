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
 * QueryOrderedBusinessReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderedBusinessReq {
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

    /**
     * 业务类型
     * 01：套餐类
     * 02：增值业务类
     * 03：服务功能类
     * 04：营销活动等
     */
    @NotBlank(message = "参数busiType不能为空")
    private String busiType;
}
