package com.migu.redstone.order.service.dto.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BizInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizInfo {
    /**
     * 产品类型
     * 01：套餐类
     * 02：增值业务类
     * 03：服务功能类
     * 04：营销活动等其他类
     * 必填
     */
    private String productType;
    /**
     * 产品信息
     * 要求按照时间先后顺序排序（按照生效时间还是失效时间？？？？）
     */
    private List<ProductInfo> productInfo;

}
