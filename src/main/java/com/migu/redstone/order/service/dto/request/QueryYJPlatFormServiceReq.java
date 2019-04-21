package com.migu.redstone.order.service.dto.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QueryYJPlatFormServiceReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryYJPlatFormServiceReq implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 查询标志
     * 01：手机号码 02：固话号码 03：宽带帐号 04：vip卡号 05：集团编 码；本期只有01：手机号码
     */
    private String serviceType;
    /**
     * 手机号码 [String]
     * V 32
     */
    @NotBlank(message = "手机号码长度不为空")
    @Length(min = 1, max = 32, message = "手机号码长度为1-32")
    private String serviceNumber;
}
