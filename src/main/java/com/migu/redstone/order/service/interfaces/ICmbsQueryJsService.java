/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.interfaces;

import java.lang.reflect.InvocationTargetException;

import com.migu.redstone.order.service.dto.request.PushListMsgReq;
import com.migu.redstone.order.service.dto.request.PushSingleMsgReq;
import com.migu.redstone.order.service.dto.response.PushListMsgResp;
import com.migu.redstone.order.service.dto.response.PushSingleMsgResp;

/**
 * 江苏接口服务
 */
public interface ICmbsQueryJsService {

    /**
     * 手机号单推
     */
    PushSingleMsgResp pushSingleMsg(PushSingleMsgReq req) throws IllegalAccessException, InvocationTargetException;

    /**
     * 手机号群推
     */
    PushListMsgResp pushListMsg(PushListMsgReq req) throws IllegalAccessException, InvocationTargetException;
}