package com.migu.redstone.order.service.dto.request;

import com.migu.redstone.dto.Header;
import com.migu.redstone.order.service.dto.model.UserTagsQueryCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 类作用: 自定义条件批量查询用户标签
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.request
 * 类名称: BatchQueryUserTagsReq
 * 类描述: 自定义条件批量查询用户标签
 * 创建人: yangyuan3
 * 创建时间: 2019/01/24 14:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchQueryUserTagsReq {
    /**
     * 公共请求头.
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * 用户标签查询条件.
     */
    @Valid
    @NotNull(message = "参数userTagsQueryCondition不能为空")
    private UserTagsQueryCondition userTagsQueryCondition;
}