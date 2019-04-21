package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /**
     * 产品编码
     * 必填
     */
    private String productId;

    /**
     * 产品名称
     * 必填
     */
    private String productName;

    /**
     * 产品描述
     * 必填
     */
    private String productDesc;

    /**
     * 产品生效时间
     * 生效时间YYYYMMDDHH24MISS
     * 必填
     */
    private String productEffDate;

    /**
     * 产品失效时间
     * 失效时间YYYYMMDDHH24MISS
     * 必填
     */
    private String productExpDate;

}
