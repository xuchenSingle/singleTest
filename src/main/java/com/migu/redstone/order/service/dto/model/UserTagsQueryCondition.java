package com.migu.redstone.order.service.dto.model;

import com.migu.redstone.client.dto.model.DataMartSearchCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * 类作用: 用户标签查询条件
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.model
 * 类名称: UserTagsQueryCondition
 * 类描述: 用户标签查询条件
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagsQueryCondition {
    /**
     * 查询条件列表.
     */
    @NotEmpty(message = "参数searchConditions不能为空")
    private List<DataMartSearchCondition> searchConditions;
//    /**
//     * 标签名.
//     */
//    @NotBlank(message = "参数name不能为空")
//    private String name;
//
//    /**
//     * 标签值.
//     */
//    @NotEmpty(message = "参数value不能为空")
//    private List<String> value;
//
//    /**
//     * 标签类型
//     * 1：单值或多值in查询，默认为1
//     * 2：单值或多值not in查询
//     * 3：区间查询.
//     */
//    @NotBlank(message = "参数type不能为空")
//    private String type;

    /**
     * 标签分类，不传时默认返回全部授权列.
     */
    private List<String> displayColumns;

    /**
     * 用户标签查询分页信息.
     */
    private UserTagQueryPageInfo pageInfo;
}