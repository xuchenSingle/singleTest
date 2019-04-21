/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.order.service.dto.model.BizQualificationProd;


/**
* 类作用:    一级能开-业务办理资格校验request
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.dto.request
* 类名称:    CmbsBizQualificationReq
* 类描述:    一级能开-业务办理资格校验request
* 创建人:    jianghao
* 创建时间:   2018年11月30日 下午1:02:41
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmbsBizQualificationReq {
    /**
     * 01：手机号码 ;02：固话号码 ;03：宽带帐号 ;04：vip卡号 ;
     * 05：集团编码;06：第三方互联网账号;07：物联网号码

     */
    private String numberType;

    /**
     * 商品编码:goodsId.
     */
    private String goodsId;

    /**
     * 账号信息.
     */
    private String numberInfo;

    /**
     * 产品列表:一个商品下只有一个产品.
     */
    private List<BizQualificationProd> productList;

    
}
