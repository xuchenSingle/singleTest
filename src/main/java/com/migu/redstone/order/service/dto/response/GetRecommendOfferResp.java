package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 山东offer推荐
 * 
 * @author wfl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRecommendOfferResp {
	/**
	 * 返回
	 */
	private Result result;

	/**
	 * 上游返回编码
	 */
	private String upResultCode;
	
	/**
	 * 上游返回信息
	 */
	private String upResultMsg;
	
	/**
	 * 业务
	 */
	private List<Offer> offerList;
}
