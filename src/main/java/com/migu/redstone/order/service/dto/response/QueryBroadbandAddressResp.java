package com.migu.redstone.order.service.dto.response;

import java.util.List;

import com.migu.redstone.client.dto.model.AddressInfo;
import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBroadbandAddressResp {
    /**
     * 返回结果对象
     */
    private Result result = Result.success();

    /**
     * 地址集合
     */
    private List<AddressInfo> addressList;
}
