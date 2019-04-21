package com.migu.redstone.order.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取token的返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuerySdAccessTokenRsp {
    /**
     * 获取到的访问令牌（AT或UIAT）
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 访问令牌的有效期（以秒为单位）
     */
    @JsonProperty("expires_in")
    private String expiresIn;

    /**
     * 错误编码
     */
    @JsonProperty("error")
    private String error;

    /**
     * 错误描述
     */
    @JsonProperty("error_description")
    private String errorDescription;
}
