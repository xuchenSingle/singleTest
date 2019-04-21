package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类作用: 用户标签
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.model
 * 类名称: UserTagLabel
 * 类描述: 用户标签
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagLabel {
    /**
     * 标签名.
     */
    private String key;

    /**
     * 标签值.
     */
    private String value;
}