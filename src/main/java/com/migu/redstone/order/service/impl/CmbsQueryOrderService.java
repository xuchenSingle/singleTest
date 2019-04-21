/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.business.order.mapper.CmbsOrderItemMapper;
import com.migu.redstone.business.order.mapper.po.CmbsOrderItem;
import com.migu.redstone.business.order.service.dto.model.SubOrderInfo;
import com.migu.redstone.cfg.product.mapper.CmbsProdMapper;
import com.migu.redstone.cfg.product.mapper.po.CmbsProd;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.order.service.dto.request.QuerySubOrderReq;
import com.migu.redstone.order.service.dto.response.QuerySubOrderRsp;
import com.migu.redstone.order.service.interfaces.ICmbsQueryOrderService;
import com.migu.redstone.utils.DateCommonUtil;
import com.migu.redstone.utils.JsonUtil;

/**
* 类作用:    cmbs-query-order服务实现类
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.impl
* 类名称:    cmbs-query-order服务实现类
* 类描述:    cmbs查询订单service实现类
* 创建人:    jianghao
* 创建时间:   2018年11月5日 下午12:48:51
*/
@Service
public class CmbsQueryOrderService implements ICmbsQueryOrderService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsQueryOrderService.class);

    /**
     * cmbsOrderMapper.
     */
    @Autowired
    private CmbsOrderItemMapper cmbsOrderItemMapper;

    /**
     * cmbsProdMapper.
     */
    @Autowired
    private CmbsProdMapper cmbsProdMapper;

    /**
     *<querySubOrder>.
     *<查询单个子订单信息>
     * @param  request   [request]
     * @return [返回订单信息]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
    @Override
    public QuerySubOrderRsp querySubOrder(QuerySubOrderReq request) throws Exception {
        String subOrderId = request.getSubOrderId();
        String phoneNumber = request.getPhoneNumber();
        QuerySubOrderRsp response = new QuerySubOrderRsp();
        //获取订单信息
        CmbsOrderItem cmbsOrderItem = cmbsOrderItemMapper.getSubOrderInfoByIdAndPhone(
            Long.parseLong(subOrderId), phoneNumber);
        if (cmbsOrderItem == null) {
            LOG.error("CmbsQueryOrderService.querySubOrder error,"
                + " request=" + JsonUtil.objectToString(request)
                + "子订单(subOrderId=" + subOrderId + ",phoneNumber=" + phoneNumber + ")不存在");
            return response;
        }

        //获取商品信息
        String productId = cmbsOrderItem.getProductId();
        if (StringUtils.isBlank(productId)) {
            LOG.error("CmbsQueryOrderService.querySubOrder error, productId is null,"
                + " request=" + JsonUtil.objectToString(request)
                + "子订单(subOrderId=" + subOrderId + ",phoneNumber=" + phoneNumber + ")没有匹配的的产品id");
            processResponse(subOrderId, response, cmbsOrderItem, productId, null);
            return response;
        }
        CmbsProd model = new CmbsProd();
        model.setProductId(Long.parseLong(productId));
        CmbsProd cmbsProd = cmbsProdMapper.selectByCmbsProd(model);
        if (cmbsProd == null || StringUtils.isBlank(cmbsProd.getProductName())) {
            LOG.error("CmbsQueryOrderService.querySubOrder error,"
                + " request=" + JsonUtil.objectToString(request)
                + "子订单(subOrderId=" + subOrderId + ",phoneNumber=" + phoneNumber + ")没有匹配的的产品信息");
            processResponse(subOrderId, response, cmbsOrderItem, productId, null);
            return response;
        }

        //设置返回值
        processResponse(subOrderId, response, cmbsOrderItem, productId, cmbsProd.getProductName());
        return response;
    }

    /**
    *<processResponse>.
    *<设置返回值>
    * @param  subOrderId   [subOrderId]
    * @param  response   [response]
    * @param  cmbsOrderItem   [cmbsOrderItem]
    * @param  productId   [productId]
    * @param  productName   [productName]
    * @author jianghao
    */
    private void processResponse(String subOrderId, QuerySubOrderRsp response,
        CmbsOrderItem cmbsOrderItem, String productId, String productName) {
        SubOrderInfo orderInfo = new SubOrderInfo();

        orderInfo.setCreateTime(DateCommonUtil.getFormatDateStr(cmbsOrderItem.getGmtCreate(),
            CommonConst.DATEFORMAT.TIMESTAMP_FORMAT));
        orderInfo.setFailReason(cmbsOrderItem.getFailMsg());
        orderInfo.setSubOrderId(subOrderId);
        orderInfo.setOrderResult(cmbsOrderItem.getStatus());
        orderInfo.setProductId(productId);
        orderInfo.setProductName(productName);

        response.setOrderInfo(orderInfo);
    }
}
