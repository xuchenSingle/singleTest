/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    已办理会员信息
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.model
* 类名称:    ProcessedMemberInfo
* 类描述:    ProcessedMemberInfo
* 创建人:    jianghao
* 创建时间:   2018年12月21日 下午4:52:10
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedMemberInfo {
    /**
     * memberLevel
     */
    private String memberLevel;
    /**
     * memberLevelName
     */
    private String memberLevelName;
    /**
     * subType
     */
    private String subType;
    /**
     * validDate
     */
    private String validDate;
    /**
     * expireDate
     */
    private String expireDate;
}
