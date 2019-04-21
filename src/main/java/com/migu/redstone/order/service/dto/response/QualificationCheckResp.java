package com.migu.redstone.order.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QualificationCheckResp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationCheckResp {
    /**
     * 结果码
     */
    private String bizCode;
    /**
     * 结果描述
     */
    private String bizDesc;
}
