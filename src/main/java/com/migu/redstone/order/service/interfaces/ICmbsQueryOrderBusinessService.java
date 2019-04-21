package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.client.dto.response.QueryOrderBusinessRsp;
import com.migu.redstone.order.service.dto.request.QueryOrderBusinessReq;
//import com.migu.redstone.order.service.dto.response.QueryOrderBusinessRsp;

/**
 * ICmbsQueryOrderBusinessService
 */
public interface ICmbsQueryOrderBusinessService {
    /**
     * queryOrderBusiness
     * @param req [QueryOrderBusinessReq]
     * @return QueryOrderBusinessRsp
     * @throws Exception [Exception]
     */
    QueryOrderBusinessRsp queryOrderBusiness(QueryOrderBusinessReq req) throws Exception;
}
