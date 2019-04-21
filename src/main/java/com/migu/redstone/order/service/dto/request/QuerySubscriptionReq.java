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
 * 视讯--用户订购关系请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySubscriptionReq {

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
	 * 活动
	 */
	@NotBlank(message = "参数offerId不能为空")
	@Length(max = 32, message = "参数offerId长度最长32位")
	private String offerId;
}
