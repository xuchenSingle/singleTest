package com.migu.redstone.order.service.dto.request;

import com.migu.redstone.dto.Header;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 宽带办理校验
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOrderBroadbandReq {
    /**
     * 公共请求头
     */
	@Valid
	@NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;
    /**
     * 用户手机号
     */
    @Length(max = 11, min = 11, message = "用户手机号长度为11位")
    private String phoneNumber;
    /**
     * 身份证号码
     */
    @Length(max = 18, min = 18, message = "身份证号码的长度为18位")
    private String idNo;
    /**
     * 用户姓名
     */
    @Length(max = 128, min = 1, message = "用户姓名长度不超过128位")
    private String userName;
    /**
     * 办理类型
     0新装，1续费
     */
    @Length(max = 1, min = 1, message = "办理类型为0或1的一位数字")
    private String type;
}
