package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;


/**
 * 查询营销活动数据返回
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryActivityDataResp implements Serializable {

    private static final long serialVersionUID = 9108837994613423925L;

    /**
     * 返回结果对象
     */
    private Result result = Result.success();

    /**
     * activityData
     */
    List<ActivityData> activityData;

}
