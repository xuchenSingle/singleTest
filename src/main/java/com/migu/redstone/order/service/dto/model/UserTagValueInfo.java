package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类作用: 用户标签值信息
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.model
 * 类名称: UserTagValueInfo
 * 类描述: 用户标签值信息
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 09:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagValueInfo {
    /**
     * 标签值.
     */
    private String value;

    /**
     * 权重.
     */
    private Float weight;
}