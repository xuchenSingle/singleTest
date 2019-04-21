package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 宽带办理校验
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOrderBroadbandResp {
    /**
     * 返回结果对象
     */
//    private List<Result> result;
    private Result result = Result.success();
    /**
     * 0 成功
     1 失败
     */

    private String status;
    /**
     * message
     */

    private String message;
}
