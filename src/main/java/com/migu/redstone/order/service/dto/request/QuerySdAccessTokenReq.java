package com.migu.redstone.order.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 山东offer推荐
 * @author wfl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuerySdAccessTokenReq {
    /**
     *  固定传入：client_credentials
     */
    @JsonProperty("grant_type")
    private String grantType;

    /**
     *  应用ID。
     */
    @JsonProperty("client_id")
    private String clientd;

    /**
     * 申请应用时分配的应用密钥。
     */
    @JsonProperty("client_secret")
    private String clientSecret;

}
