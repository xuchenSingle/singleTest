package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import com.migu.redstone.entity.Result;
/**
 * 手机号群推消息模板接口
 * @author wfl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushListMsgResp implements Serializable {
    /**
     * 返回结果对象
     */
    private Result result = Result.success();
}
