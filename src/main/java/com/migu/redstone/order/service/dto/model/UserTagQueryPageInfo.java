package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类作用: 用户标签查询分页信息
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.model
 * 类名称: UserTagQueryPageInfo
 * 类描述: 用户标签查询分页信息
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 09:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagQueryPageInfo {
    /**
     * 页码，不传时设置默认值0.
     */
    private Integer currentPage ;

    /**
     * 每页条数，最多支持单页200条，不传时设置默认值200.
     */
    private Integer pageSize;

    /**
     * 是否只返回结果总条数
     * true：结果只返回条数
     * false：结果中返回条数和明细，默认false.
     */
    private Boolean showOnlyCount;
}