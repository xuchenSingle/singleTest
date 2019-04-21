package com.migu.redstone.order.service.dto.request;

import com.migu.redstone.dto.Header;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 查询亲情网请求信息
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.request
 * @类名称 QueryFamilyMemberReq
 * @类描述 查询亲情网请求信息
 * @创建人 zhaoke
 * @创建时间 2019年3月28日 上午11:04:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFamilyNetGroupReq{
    /**
     * requestHeader
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * serviceNum
     */
    @NotBlank(message = "参数serviceNum不能为空")
    @Length(max = 11,min = 11, message = "参数serviceNum长度为11位")
    private String serviceNum;

    /**
     * empowerNo
     */
    @NotBlank(message = "参数empowerNo不能为空")
    @Length(max = 20, min = 20, message = "参数empowerNo长度为20位")
    private String empowerNo;
}
