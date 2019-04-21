package com.migu.redstone.order.service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;

/**
 * QueryJSFlowInfoResp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryJSFlowInfoResp {
    /**
     * result
     */
    private Result result = Result.success();
    /**
     * msg
     */
    private String msg;
    /**
     * phoneNumber
     */
    private String phoneNumber;
    /**
     * 系统时间
     */
    private String time;
    /**
     * 10001：有 10002：无 10003：异常（包括系统异常、未能从IT获取流量数据、后台无生效业务信息）
     */
    private String buscode;
    /**
     * 1、提示百分比； 2，返回文本信息
     */
    private int type;
    /**
     * 咪咕定向流量百分比
     */
    private double percentage;
    /**
     * message
     */
    private String message;
    /**
     * 活动链接地址，飞起
     */
    private String url;

    /**
     * 定向流量的剩余量，单位KB
     */
    private String subtotal;

    /**
     * 活动链接地址
     */
    private String acturl;

    /**
     * 活动名称
     */
    private String acttitle;

    /**
     * 活动详细描述
     */
    private String actdesc;
}
