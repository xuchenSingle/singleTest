package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 江苏查询流量返回结果
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.model
 * @类名称 JSFlowResult
 * @类描述 江苏查询流量返回结果
 * @创建人 xuchen
 * @创建时间 2018年11月22日 上午11:18:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class JSFlowResult {

    /**
     * buscode
     */
    private String buscode;
    /**
     * type
     */
    private String type;
    /**
     * percentage
     */
    private String percentage;
    /**
     * message
     */
    private String message;
    /**
     * url
     */
    private String url;
}
