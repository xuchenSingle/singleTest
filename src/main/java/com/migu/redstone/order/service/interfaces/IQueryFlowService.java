/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.QueryFlowInfoReq;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;

/**
 * 流量查询接口，配置处理类
 */
public interface IQueryFlowService {

	/**
	 * 用户套餐流量
	 * @param flowInfoReq
	 * @return
	 */
	QueryFlowInfoResp  queryFlow(QueryFlowInfoReq flowInfoReq,String provinceCode) throws Exception;

}
