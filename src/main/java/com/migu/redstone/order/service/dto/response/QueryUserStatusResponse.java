package com.migu.redstone.order.service.dto.response;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * 项目名称:  cmbs-query-order
 * 包:        com.migu.redstone.service.dto.model
 * 类名称:    QueryUserStatusResponse
 * 类描述:    查询用户状态信息
 * 创建时间:   2018年11月27日 下午3:03:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserStatusResponse implements Serializable {
    /**
     * 返回码
     * 1-成功   2-失败
     */
    private String bizCode;
    /**
     * 错误信息描述
     */
    private String bizDesc;
    /**
     * 用户状态
     */
    private String userStatus;
}
