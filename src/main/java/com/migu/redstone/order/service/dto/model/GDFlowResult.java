package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广东查询流量返回结果
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.model
 * @类名称 GDFlowResult
 * @类描述 广东查询流量返回结果
 * @创建人 xuchen
 * @创建时间 2018年11月22日 上午11:20:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GDFlowResult {

    /**
     * groupnum
     */
    private String groupnum;
    /**
     * grouptotal
     */
    private String grouptotal;
    /**
     * groupused
     */
    private String groupused;
    /**
     * groupleft
     */
    private String groupleft;

}
