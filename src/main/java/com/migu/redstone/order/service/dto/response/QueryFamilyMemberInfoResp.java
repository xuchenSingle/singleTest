package com.migu.redstone.order.service.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.MemberInfo;

/**
 * QueryFamilyMemberInfoResp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFamilyMemberInfoResp {

    /**
     * result
     */
    private Result result = Result.success();

    /**
     * 家庭成员列表
     */
    private List<MemberInfo> memberList;

}
