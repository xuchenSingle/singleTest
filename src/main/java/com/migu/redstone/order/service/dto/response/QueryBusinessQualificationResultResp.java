package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * result
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBusinessQualificationResultResp {
	/**
	 * result
	 */
	private Result result = Result.success();

	/**
	 * 上游返回码
	 */
	private String upResultCode;

	/**
	 * 上游返回信息
	 */
	private String upResultMsg;
}
