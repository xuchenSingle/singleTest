package com.migu.redstone.order.service.dto.response;

import java.util.List;

import com.migu.redstone.client.dto.model.BizInfo;
import com.migu.redstone.entity.Result;
//import com.migu.redstone.order.service.dto.model.BizInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QueryOrderedBusinessResp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderedBusinessResp {
    /**
     * result
     */
    private Result result = Result.success();
    /**
     * bizInfoList
     */
    private List<BizInfo> bizInfoList;
}
