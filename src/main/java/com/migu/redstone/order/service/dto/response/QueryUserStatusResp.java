package com.migu.redstone.order.service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;

/**
 QueryUserStatusResp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserStatusResp {
    /**
     * result
     */
    private Result result = Result.success();
    /**
     * userStatus
     */
    private String userStatus;
}
