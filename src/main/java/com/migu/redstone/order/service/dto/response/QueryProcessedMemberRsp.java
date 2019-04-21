/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.order.service.dto.model.ProcessedMemberInfo;


/**
* 类作用:    一级能开-已办理会员查询response
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.response
* 类名称:    QueryProcessedMemberRsp
* 类描述:    一级能开-已办理会员查询response
* 创建人:    jianghao
* 创建时间:   2018年11月28日 下午3:29:11
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryProcessedMemberRsp {
    /**
     * 返回码(0000：成功（是咪咕用户）; 0001：不是咪咕用户; 0002：其他原因失败).
     */
    private String bizCode;

    /**
     * 错误结果描述.
     */
    private String bizDesc;

    /**
     * 会员等级信息.
     */
    private List<ProcessedMemberInfo> memberLevelList;
}
