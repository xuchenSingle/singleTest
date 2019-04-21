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
 * 校验号码状态 req
 *
 * @项目名称 cmbs-query-order
 * @包 com.migu.redstone.order.service.dto.request
 * @类名称 CheckNumberStatusReq
 * @类描述 校验号码状态 req
 * @创建人 zhaoke
 * @创建时间 2019年4月2日 上午11:04:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckNumberStatusReq {
    /**
     * requestHeader
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;

    /**
     * 号码类型
     */
    @NotBlank(message = "参数numberType不能为空")
    @Length(max = 1,min = 1, message = "参数numberType长度为1位")
    private String numberType;

    /**
     * 号码
     */
    @NotBlank(message = "参数number不能为空")
    @Length(max = 32, message = "参数number长度最长32位")
    private String number;
}
