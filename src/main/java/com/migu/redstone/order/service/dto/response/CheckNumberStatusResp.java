package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 校验号码状态 resp
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.request
 * @类名称 CheckNumberStatusResp
 * @类描述 校验号码状态 resp
 * @创建人 zhaoke
 * @创建时间 2019年4月2日 上午11:04:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckNumberStatusResp {

    /**
     * result
     */
    private Result result = Result.success();

    /**
     * 上游返回码
     */
    private String upResultCode;

    /**
     * 上游返回描述
     */
    private String upResultMsg;
}
