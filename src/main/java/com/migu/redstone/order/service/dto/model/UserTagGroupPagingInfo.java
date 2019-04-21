package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类作用: 用户标签组分页信息
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.model
 * 类名称: UserTagGroupPagingInfo
 * 类描述: 用户标签组分页信息
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 09:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagGroupPagingInfo {
    /**
     * 标签分组名称.
     */
    private String groupName;

    /**
     * 用户标签分页信息列表.
     */
    private List<UserTagLabelPagingInfo> groups;
}