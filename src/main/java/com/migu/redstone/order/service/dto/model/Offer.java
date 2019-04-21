package com.migu.redstone.order.service.dto.model;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 5.2.14Offer套餐信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    /**
     * offer类型
     1 套餐
     2 营销案
     3 SP
     4 主体产品
     5 个人虚拟网产品
     6 个人虚拟网优惠
     7 网厅商品
     */
    @NotBlank(message = "offerType不能为空")
    @Length(max = 1, min = 1, message = "参数offerType长度为1")
    private String offerType;

    /**
     * 电信offer
     * 广告
     * 1 固定广告
     * 2 浮动广告
     */
    @NotBlank(message = "displayMode不能为空")
    @Length(max = 1, min = 1, message = "参数displayMode长度为1")
    private String displayMode;

    /**
     * 电信offer时为offer表的Id
     * 广告时为广告编号
     */
    @NotBlank(message = "dofferId不能为空")
    @Length(max = 12, message = "参数offerId长度不超过12")
    private String offerId;

    /**
     * Offer名称
     */
    @NotBlank(message = "offerName不能为空")
    @Length(max = 256, message = "参数offerName长度不超过256")
    private String offerName;

    /**
     * 描述
     */
    @NotBlank(message = "desc不能为空")
    private String desc;

    /**
     * 展示url
     */
    @NotBlank(message = "url不能为空")
    private String url;

    /**
     * 点击url，ISSS预留
     */
    @NotBlank(message = "clickUrl不能为空")
    private String clickUrl;

    /**
     * 数值精确到两位小数，数值越大，优先级越高。
     */
    @NotBlank(message = "priority不能为空")
    private String priority;

    /**
     扩展属性，key的取值如下：
     treatment_id（treatment分支ID）
     campaign_id（战役分支ID）
     offerCode（产品CODE）
     activity_name（活动名称）
     marketingInfo (营销用语。外围系统调用离席短信下发接口，将短信用语下发给最终用户)
     introduceInfo (营销话术)
     campaignName(战役名称)
     activityPriority （活动优先级）
     activityLevel（活动级别）
     needAdvertise （是否展示广告）
     advertiseID（广告编码）
     */
    private List<Map<String, String>> extAttr;
}
