package com.migu.redstone.order.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.client.dto.response.QueryOrderBusinessRsp;
import com.migu.redstone.client.proxy.interfaces.IFirstAbilityFeignProxy;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.order.service.dto.request.QueryOrderBusinessReq;
//import com.migu.redstone.order.service.dto.response.QueryOrderBusinessRsp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryOrderBusinessService;

/**
 * CmbsQueryOrderBusinessService
 */
@Service
public class CmbsQueryOrderBusinessService implements ICmbsQueryOrderBusinessService {

	/**
	 * LOG
	 */
	private static final Logger logger = LoggerFactory.getLogger(CmbsGDQueryService.class);

	/**
	 * firstAbilityFeignClient
	 */
	@Autowired
	private IFirstAbilityFeignProxy firstAbilityFeignProxy;

	/**
	 * cfgCommonService
	 */
	@Autowired
	private ICfgCommonService cfgCommonService;

	@Override
	public QueryOrderBusinessRsp queryOrderBusiness(QueryOrderBusinessReq req) throws Exception {

		CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
				StaticDataConst.StaticCodeType.FIRSTABILITY_FEIGNCLIENT,
				StaticDataConst.StaticCodeName.FIRST_QUERY_ORDERBUSINESS_URL);
		Map<String, Object> expandMap = new HashMap<String, Object>();
		expandMap.put("MSISDN", req.getServiceNumber());
		QueryOrderBusinessRsp resp = firstAbilityFeignProxy.queryOrderBusiness(expandMap, new URI(staticData.getCodeValue()),
				req.getServiceType(), req.getServiceNumber(), req.getBusiType());
		return resp;
	}
}
