/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.PushTemplateInfoReq;
import com.migu.redstone.order.service.dto.response.PushTemplateInfoResp;

import java.lang.reflect.InvocationTargetException;

/**
 * 河南接口服务
 */
public interface ICmbsQueryHaService {

    /**
     * 消息模板接收接口
     */
    PushTemplateInfoResp pushTemplateInfo(PushTemplateInfoReq req) throws Exception;

}