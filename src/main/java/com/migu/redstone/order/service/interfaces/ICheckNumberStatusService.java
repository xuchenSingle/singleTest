package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.CheckNumberStatusReq;
import com.migu.redstone.order.service.dto.response.CheckNumberStatusResp;

public interface ICheckNumberStatusService {
    /**
     * 号码状态校验
     * @param req
     * @return 校验结果CheckNumberStatusResp
     * @throws Exception
     */
    CheckNumberStatusResp checkNumberStatus(CheckNumberStatusReq req) throws Exception;
}
