package com.migu.redstone.order.service.dto.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.PackageInfo;

/**
 * QueryUserPackageInfoResp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserPackageInfoResp {

    /**
     * result
     */
    private Result result = Result.success();
    /**
     * 用户订购的套餐列表
     */
    private List<PackageInfo> packageList;

}
