package com.migu.redstone.order.service.dto.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.order.service.dto.model.BizInfo;

/**
 * QueryOrderBusinessRsp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderBusinessRsp {

    /**
     * 返回码
     * 必填
     */
    private String bizCode;

    /**
     * 错误描述信息
     */
    private String bizDesc;

    /**
     * 结果对应时间戳
     * 此时间是由省BOSS返回给平台，代表查询返回内容的时间点。
     * 查询成功时返回。
     */
    private String oprTime;

    /**
     * 业务信息
     */
    private List<BizInfo> bizInfoList;

}
