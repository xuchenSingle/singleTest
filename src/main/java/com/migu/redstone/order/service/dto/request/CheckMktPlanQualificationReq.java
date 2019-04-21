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
 * CheckBusinessQualificationReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckMktPlanQualificationReq {

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
    @Length(max = 11, message = "参数phoneNumber长度最长11位")
    private String phoneNumber;
    /**
     * 产品编码
     */
    @NotBlank(message = "参数productId不能为空")
    @Length(max = 32, message = "参数productId长度最长32位")
    private String productId;
    /**
     * 用户参与小额流量与话费活动的时候，该值需要传，1)随机流量赠送大小，单位M，校验随机流量营销案时传递
     * 2)优惠充值金额，单位是厘，例如：1元，传值：1000，校验小额话费赠送营销案时传递
     */
    private String quantity;
}
