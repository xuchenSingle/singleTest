/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;

/**
 *
 * 查询定向流量包使用百分比。
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.response
 * @类名称 QueryExclusiveFlowUseRsp
 * @类描述 查询定向流量包使用百分比。
 * @创建人 xuchen
 * @创建时间 2018年11月6日 下午2:08:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFlowInfoResp implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * result.
     */
    private Result result = Result.success();
    /**
     * totalFlowAmt.
     */
    private String totalFlowAmt;

//    private String totalUsedFlowAmt;
    /**
     * totalLeftFlowAmt.
     */
    private String totalLeftFlowAmt;
    /**
     * commonFlowAmt.
     */
    private String commonFlowAmt;

//    private String commonUsedFlowAmt;
    /**
     * commonLeftFlowAmt.
     */
    private String commonLeftFlowAmt;
    /**
     * dingXiangFlowAmt.
     */
    private String dingXiangFlowAmt;

//    private String dingXiangUsedFlowAmt;
    /**
     * dingXiangLeftFlowAmt.
     */
    private String dingXiangLeftFlowAmt;
}
