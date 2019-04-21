package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.FamilyNetGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类作用:    查询亲情网response
 * 项目名称:   cmbs-query-order
 * 包:       com.migu.redstone.order.service.dto.response
 * 类名称:    QueryFamilyNetGroupResp
 * 类描述:    查询亲情网response
 * 创建人:    zhaoke
 * 创建时间:   2019年3月28日 下午3:29:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFamilyNetGroupResp {
    /**
     * result.
     */
    private Result result = Result.success();

    private List<FamilyNetGroup> familyNetGroups;
}
