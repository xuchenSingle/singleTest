package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.migu.redstone.dto.Header;

/**
 * CheckBusinessQualificationReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBusinessQualificationReq {

    /**
     * requestHeader
     */
    @NotNull(message = "参数requestHeader不能为空")
    @Valid
    private Header requestHeader;

    /**
     * phoneNumber
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, min = 11,message = "参数phoneNumber长度11位")
    private String phoneNumber;
    /**
     * 商品编码
     */
    @NotBlank(message = "参数goodsId不能为空")
    @Length(max = 32, message = "参数goodsId长度最长32位")
    private String goodsId;
    
    /**
     * 是否同步，0:异步，1:同步，默认为1:同步
     */
    @Length(max = 1, message = "参数isSync长度最长1位")
    private String isSync;
    
    /**
     * 当办理的视讯定向流量包时候，如有多个则用分号分隔
     */
    private String serviceIdList;
}
