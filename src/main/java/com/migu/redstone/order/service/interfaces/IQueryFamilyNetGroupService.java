package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.QueryFamilyNetGroupReq;
import com.migu.redstone.order.service.dto.response.QueryFamilyNetGroupResp;

/**
 * IQueryFamilyNetGroupService
 */
public interface IQueryFamilyNetGroupService {

    /**
     * 查询亲情网
     */
    QueryFamilyNetGroupResp queryFamilyNetGroup(QueryFamilyNetGroupReq req) throws Exception;
}
