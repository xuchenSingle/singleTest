/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视讯--用户订购关系返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuerySubscriptionResp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * result.
	 */
	private Result result = Result.success();

	/**
	 * 业务标识
	 */
	private String offerId;

	/**
	 * 业务名称
	 */
	private String offerName;

	/**
	 * 业务状态: -1未订购，1-待激活, 2-有效, 3-半停, 4-暂停,8-已退订（用户预销户，可以恢复）,9-已退订
	 */
	private String status;

}
