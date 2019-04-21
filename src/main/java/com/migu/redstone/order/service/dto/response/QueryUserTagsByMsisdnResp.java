package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.UserTagGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 类作用: 根据手机号码查询用户标签
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.dto.response
 * 类名称: QueryUserTagsByMsisdnResp
 * 类描述: 根据手机号码查询用户标签
 * 创建人: yangyuan3
 * 创建时间: 2019/01/24 15:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserTagsByMsisdnResp {
    /**
     * 返回结果对象.
     */
    private Result result;

    /**
     * 状态：0 成功 1 失败.
     */
    private String status;

    /**
     * 用户标签组.
     */
    private List<UserTagGroup> uerTagGroups;
}