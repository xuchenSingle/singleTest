/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 类作用:    导出表查询出参对象
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.model
* 类名称:    CmbsExportRecordDTO
* 类描述:    导出表查询出参对象
* 创建人:    zhuhd
* 创建时间:   2019年3月11日 上午11:23:03
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmbsExportRecordDTO {

    /**
     * 起始时间到结束时间，中间以"~" 间隔.
     */
    private String cycleTime;
    /**
     * 导出时间.
     */
    private String exportTime;
    /**
     * 状态.
     */
    private String state;
    /**
     * 导出人.
     */
    private String exportBy;
    /**
     * attachId.
     */
    private Long attachId;
}
