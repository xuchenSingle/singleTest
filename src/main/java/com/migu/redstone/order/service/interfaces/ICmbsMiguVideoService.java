/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.interfaces;

import org.springframework.web.bind.annotation.RequestBody;

import com.migu.redstone.order.service.dto.request.CmbsBizQualificationReq;
import com.migu.redstone.order.service.dto.request.QueryProcessedMemberReq;
import com.migu.redstone.order.service.dto.request.QueryUserTagsReq;
import com.migu.redstone.order.service.dto.request.UserTagsAbilityReq;
import com.migu.redstone.order.service.dto.response.CmbsBizQualificationRsp;
import com.migu.redstone.order.service.dto.response.QueryProcessedMemberRsp;
import com.migu.redstone.order.service.dto.response.QueryUserTagsResp;
import com.migu.redstone.order.service.dto.response.UserTagsAbilityRsp;

/**
* 类作用:    视讯service接口
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.interfaces
* 类名称:    ICmbsMiguVodeoService
* 类描述:    视讯service接口
* 创建人:    jianghao
* 创建时间:   2018年11月23日 上午10:11:04
*/
public interface ICmbsMiguVideoService {
    /**
     *<isNewUserAndMember>.
     *<江苏-视讯: 获取用户是否是会员以及是否是新用户接口>
     * @param  request   [request]
     * @return [返回用户是否是会员以及是否是新用户结果]
     * @throws Exception [Exception]
     * @author jianghao
     */
    QueryUserTagsResp isNewUserAndMember(QueryUserTagsReq request) throws Exception;

    /**
     *<queryProcessedMember>.
     *<一级能开已办理会员查询>
     * @param  request  [request]
     * @return [返回已办理会员]
     * @throws Exception [Exception]
     * @author jianghao
     */
    QueryProcessedMemberRsp queryProcessedMember(QueryProcessedMemberReq request) throws Exception;

    /**
     *<getUserTags>.
     *<一级能开活跃与沉默用户使用情况查询>
     * @param  request   [request]
     * @return [返回活跃与沉默用户使用情况]
     * @throws Exception [Exception]
     * @author jianghao
     */
    UserTagsAbilityRsp getUserTags(UserTagsAbilityReq request) throws Exception;

    /**
    *<checkBizQualification>.
    *<一级能开业务办理资格校验>
    * @param  request   [request]
    * @return [返回校验结果]
    * @throws Exception [Exception]
    * @author jianghao
    */
    CmbsBizQualificationRsp checkBizQualification(
        @RequestBody CmbsBizQualificationReq request) throws Exception;
}
