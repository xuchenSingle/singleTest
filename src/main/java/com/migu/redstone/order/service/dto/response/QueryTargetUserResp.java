package com.migu.redstone.order.service.dto.response;

import java.util.List;

import com.migu.redstone.client.dto.model.ActivityInfo;
import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryTargetUserResp {

    /**
     * result
     */
    private Result result = Result.success();

    /**
     * activityList
     */
    private List<ActivityInfo> activityList;
}
