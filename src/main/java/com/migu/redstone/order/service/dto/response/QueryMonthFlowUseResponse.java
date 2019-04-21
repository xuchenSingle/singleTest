package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.migu.redstone.client.dto.model.PackageInfoList;
import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * 项目名称:  cmbs-query-order
 * 包:        com.migu.redstone.service.dto.model
 * 类名称:    AcountBalanceResult
 * 类描述:    用户按月查询流量套餐的使用情况
 * 创建时间:   2018年11月27日 下午3:03:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryMonthFlowUseResponse implements Serializable {
   
    
    private Result result=Result.success();
    /**
     * 服务号码
     */
    private String serviceNumber;
    /**
     * 流量总量 单位KB
     */
    private String billValue;
    /**
     * 已使用总量 单位KB
     */
    private String userValue;
    /**
     * 套餐使用明细
     */
    List<PackageInfoList> packageUsedList = new ArrayList<>();

}
