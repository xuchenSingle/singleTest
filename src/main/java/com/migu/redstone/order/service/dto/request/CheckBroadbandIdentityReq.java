package com.migu.redstone.order.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.migu.redstone.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBroadbandIdentityReq {

    /**
     * 公共请求头
     */
    @Valid
    @NotNull(message="requestHeader不可为空")
    private Header requestHeader;

    /**
     * 用户手机号
     */
    @NotBlank
    @Length(max=11,min=11,message="phoneNumber填写有误")
    private String phoneNumber;

    /**
     * 身份证号码
     */
    @NotBlank
    @Length(max=18,min=18,message="idNo填写有误")
    private String idNo;

    /**
     * 用户姓名
     */
    @NotBlank(message="userName不可为空")
    private String userName;
}
