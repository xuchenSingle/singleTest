package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.migu.redstone.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBroadbandAddressReq {
    /**
     * 公共请求头
     */
    @Valid
    @NotNull(message="requestHeader不可为空")
    private Header requestHeader;

    /**
     * 地市，苏州、无锡、常州、南通、镇江、扬州、泰州、徐州、淮安、连云港、宿迁、南京、盐城
     */
    @NotBlank
    @Length(max=8,message="region长度不可超过8位")
    private String region;

    /**
     * 地址名称
     */
    @NotBlank
    @Length(max=128,message="address长度不可超过128位")
    private String address;

    /**
     * 地址id，江苏省99
     */
    @NotBlank
    @Length(max=4,message="addressId长度不可超过4位")
    private String addressId;

    /**
     * 查询类型，0：省查地市1：地市查区县2：区县查小区3：查小区详情
     */
    @NotBlank
    @Length(max=1,message="type长度不可超过1位")
    private String type;

    /**
     * 查询页数，查询类型2 3时可选
     */
    @Length(max=12,message="page长度不可超过12位")
    private String page;

    /**
     * 每页查询个数，查询类型2 3时可选
     */
    @Length(max=12, message="limit长度不可超过12位")
    private String limit;
}
