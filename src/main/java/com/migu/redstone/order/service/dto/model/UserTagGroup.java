package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类作用: 用户标签组
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.model
 * 类名称: UserTagGroup
 * 类描述: 用户标签组
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagGroup {
    /**
     * 标签分组名称.
     */
    private String groupName;

    /**
     * 用户标签.
     */
    private List<UserTagLabel> userTagLabels;
}