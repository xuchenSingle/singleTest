package com.migu.redstone.order.service.dto.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PackageInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageInfo {
    /**
     * 套餐标识
     */
    private String planId;
    /**
     * 套餐名称
     */
    private String planName;
    /**
     * 生效时间
     */
    private String validate;
    /**
     * 失效时间
     */
    private String outdate;
    /**
     * 套餐内资源信息
     */
    private List<ResourceInfo> resourceInfoList;
}
