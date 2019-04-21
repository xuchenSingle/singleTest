package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息模板接收接口
 * @author zhuhd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushTemplateInfoResp implements Serializable {
    /**
     * 返回结果对象
     */
    private Result result = Result.success();
}
