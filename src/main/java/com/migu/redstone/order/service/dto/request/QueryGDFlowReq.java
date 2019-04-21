package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.migu.redstone.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryGDFlowReq {
	/**
	 * requestHeader
	 */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
	private Header requestHeader;
    
    /**
     * phoneNumber
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, min = 11, message = "参数phoneNumber长度11位")
	private String phoneNumber;
}
