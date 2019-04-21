/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.client.dto.request.MiGuVideoPushListMsgReq;
import com.migu.redstone.client.dto.request.MiGuVideoPushSingleMsgReq;
import com.migu.redstone.client.dto.response.MiGuVideoPushListMsgRes;
import com.migu.redstone.client.dto.response.MiGuVideoPushSingleMsgRes;
import com.migu.redstone.client.enums.MiGuVideoPushMsgCode;
import com.migu.redstone.client.service.interfaces.IMiGuVideoOutClientService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.PushListMsgReq;
import com.migu.redstone.order.service.dto.request.PushSingleMsgReq;
import com.migu.redstone.order.service.dto.response.PushListMsgResp;
import com.migu.redstone.order.service.dto.response.PushSingleMsgResp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryJsService;

/**
 * 江苏接口服务
 */
@Service
public class CmbQueryJsService implements ICmbsQueryJsService {

    @Autowired
    private IMiGuVideoOutClientService miGuVideoOutClientService;

    @Override
    public PushSingleMsgResp pushSingleMsg(PushSingleMsgReq req)
            throws IllegalAccessException, InvocationTargetException {
        MiGuVideoPushSingleMsgReq miguReq = new MiGuVideoPushSingleMsgReq();
        BeanUtils.copyProperties(miguReq, req);
        MiGuVideoPushSingleMsgRes miguRes = miGuVideoOutClientService.pushSingleMsg(miguReq);
        PushSingleMsgResp resp = new PushSingleMsgResp();
        if (StringUtils.equals(miguRes.getResultCode(), MiGuVideoPushMsgCode.SUCCESS.getStatus())
				|| StringUtils.equals(miguRes.getResultCode(), MiGuVideoPushMsgCode.ACCEPTED.getStatus())) {
			return resp;
		} else {
            resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, miguRes.getResultDesc()));
            return resp;
        }
    }

    @Override
    public PushListMsgResp pushListMsg(PushListMsgReq req) throws IllegalAccessException, InvocationTargetException {
        MiGuVideoPushListMsgReq miguReq = new MiGuVideoPushListMsgReq();
        BeanUtils.copyProperties(miguReq, req);
        MiGuVideoPushListMsgRes miguRes = miGuVideoOutClientService.pushListMsg(miguReq);
        PushListMsgResp resp = new PushListMsgResp();
		if (StringUtils.equals(miguRes.getResultCode(), MiGuVideoPushMsgCode.SUCCESS.getStatus())
				|| StringUtils.equals(miguRes.getResultCode(), MiGuVideoPushMsgCode.ACCEPTED.getStatus())) {
			return resp;
		} else {
            resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, miguRes.getResultDesc()));
            return resp;
        }
    }

}
