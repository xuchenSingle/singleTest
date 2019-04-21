package com.migu.redstone.order.service.dto.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {

    /**
     * 编码类型
     * 产品类型为02.增值业务类时, 取值如下：
     * 01：产品编码；
     * 02：企业代码+业务代码
     */
    private String bizType;

    /**
     * 产品唯一编码
     * 当编码类型为01：产品编码填写
     */
    private String productCode;

    /**
     * 企业代码
     * 当编码类型为02:企业代码+业务代码填写
     */
    private String spCode;

    /**
     * 业务代码
     * 当编码类型为02:企业代码+业务代码填写
     */
    private String bizCode;

    /**
     * 产品
     */
    private List<Product> productList;

    /**
     * 服务功能id
     * 产品类型为03.服务功能类时填写
     */
    private String servFunId;

    /**
     * 服务功能名称
     * 产品类型为03.服务功能类时填写
     */
    private String servFunName;

    /**
     * 营销活动
     * 产品类型为04.营销活动等其他类时填写
     */
    private List<Action> actionList;

    /**
     * 资费描述
     * 格式：XX元/月
     */
    private String bizFee;

    /**
     * 订购时间
     * YYYYMMDDHH24MISS订购记录按照订购时间倒序排序
     */
    private String orderTime;

    /**
     * 生效时间
     * YYYYMMDDHH24MISS
     */
    private String validDate;

    /**
     * 失效时间
     * YYYYMMDDHH24MISS
     */
    private String expireDate;

    /**
     * 计费类型
     * 例如“包月”“包季”“包年”等
     */
    private String feeType;
}
