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
public class CheckMktPlanQualificationResp {
    /**
     * result
     */
    private Result result = Result.success();
    /**
     * True：可以办理
     * False：不可以办理
     */
    private boolean canOrder;
}
