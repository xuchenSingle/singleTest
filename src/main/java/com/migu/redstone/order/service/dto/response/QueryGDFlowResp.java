package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.dto.Header;
import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryGDFlowResp {
	/**
	 * result
	 */
	private Result result = Result.success();
	
	/**
	 * gdFlowInfo
	 */
	private String gdFlowInfo;
}
