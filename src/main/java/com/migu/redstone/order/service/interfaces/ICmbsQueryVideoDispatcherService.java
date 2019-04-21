/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;

/**
* 类作用:     查询视讯接口分发service接口
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.interfaces
* 类名称:    ICmbsQueryVideoDispatcherService
* 类描述:    查询视讯接口分发service接口
* 创建人:    jianghao
* 创建时间:   2018年11月30日 下午1:16:22
*/
public interface ICmbsQueryVideoDispatcherService {
    /**
     *<checkBizQualification>.
     *<一级能开业务办理资格校验>
     * @param  request   [request]
     * @param  productCode   [productCode]
     * @return [返回校验结果 ]
     * @throws Exception [Exception]
     * @author jianghao
     */
    CmbsBizQualificationRsp processBiz(CmbsBizQualificationReq request, String productCode) throws Exception;
}
