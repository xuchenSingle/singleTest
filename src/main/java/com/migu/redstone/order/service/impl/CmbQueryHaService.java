/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import com.migu.redstone.client.dto.request.MiGuVideoPushTemplateInfoReq;
import com.migu.redstone.client.dto.response.MiGuVideoPushTemplateInfoResp;
import com.migu.redstone.client.enums.MiGuVideoPushMsgCode;
import com.migu.redstone.client.service.interfaces.IMiGuVideoOutClientService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.PushTemplateInfoReq;
import com.migu.redstone.order.service.dto.response.PushTemplateInfoResp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryHaService;
import com.migu.redstone.utils.JsonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * 河南接口服务
 */
@Service
public class CmbQueryHaService implements ICmbsQueryHaService {

    @Autowired
    private IMiGuVideoOutClientService miGuVideoOutClientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CmbQueryHaService.class);

    @Override
    public PushTemplateInfoResp pushTemplateInfo(PushTemplateInfoReq req)
            throws Exception{
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>>> process the method pushTemplateInfo  req=" + JsonUtil.objectToString(req));
        }
        //初始化
        MiGuVideoPushTemplateInfoReq miguReq = new MiGuVideoPushTemplateInfoReq();
        PushTemplateInfoResp resp = new PushTemplateInfoResp();

        //转换PushTemplateInfoReq成MiGuVideoPushTemplateInfoReq
        BeanUtils.copyProperties(miguReq, req);
        MiGuVideoPushTemplateInfoResp miguRes = miGuVideoOutClientService.pushTemplateInfo(miguReq);
        if (StringUtils.equals(miguRes.getResultCode(), MiGuVideoPushMsgCode.SUCCESS.getStatus())
				|| StringUtils.equals(miguRes.getResultCode(), MiGuVideoPushMsgCode.ACCEPTED.getStatus())) {
			return resp;
		} else {
            LOGGER.error(">>>>>> process the method pushTemplateInfo  resp="+JsonUtil.objectToString(miguRes));
            resp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, miguRes.getResultDesc()));
            return resp;
        }
    }

}
