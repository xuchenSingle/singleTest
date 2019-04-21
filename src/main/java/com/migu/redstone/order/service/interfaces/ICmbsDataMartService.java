package com.migu.redstone.order.service.interfaces;

import com.migu.redstone.order.service.dto.request.BatchQueryUserTagsReq;
import com.migu.redstone.order.service.dto.request.QueryUserTagsByMsisdnReq;
import com.migu.redstone.order.service.dto.response.BatchQueryUserTagsResp;
import com.migu.redstone.order.service.dto.response.QueryUserTagsByMsisdnResp;

/**
 * 类作用: 调用数据集市接口
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.interfaces
 * 类名称: ICmbsDataMartService
 * 类描述: 调用数据集市接口
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 14:54
 */
public interface ICmbsDataMartService {
    /**
     * 〈queryUserTagsByMsisdn〉.
     * 〈根据手机号码查询用户标签〉
     *
     * @param req [QueryUserTagsByMsisdnReq]
     * @return [QueryUserTagsByMsisdnResp]
     * @throws Exception
     * @author yangyuan3
     */
    QueryUserTagsByMsisdnResp queryUserTagsByMsisdn( QueryUserTagsByMsisdnReq req) throws Exception;

    /**
     * 〈batchQueryUserTags〉.
     * 〈自定义条件批量查询用户标签〉
     *
     * @param req [BatchQueryUserTagsReq]
     * @return [BatchQueryUserTagsResp]
     * @throws Exception
     * @author yangyuan3
     */
    BatchQueryUserTagsResp batchQueryUserTags(BatchQueryUserTagsReq req) throws Exception;
}
