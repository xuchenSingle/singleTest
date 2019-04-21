package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;

/**
 * 弹窗营销位返回
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryActivityPositionResp implements Serializable {

    private static final long serialVersionUID = 9108837994613423925L;

    /**
     * 返回结果对象
     */
    private Result result = Result.success();

    /**
     * 活动标识id
     */
    private String code;

    /**
     * 弹框提示语
     */
    private String msg;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 接口返回状态码 0:接口正常 -1:接口失败 -2:参数缺失 -3:非移动号码 -4:无用户可参与的活动
     */
    private String retCode;
}
