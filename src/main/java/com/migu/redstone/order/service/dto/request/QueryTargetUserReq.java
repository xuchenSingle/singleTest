package com.migu.redstone.order.service.dto.request;

import java.util.List;

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
public class QueryTargetUserReq {

    /**
     * 公共请求头
     */
    @Valid
    @NotNull(message="requestHeader不可为空")
    private Header requestHeader;

    /**
     * 用户手机号
     */
    @NotBlank(message = "参数phoneNumber不能为空")
    @Length(max = 11, min = 1, message = "参数phoneNumber长度最长11位")
    private String phoneNumber;

    /**
     * 创建活动时配置的活动场景
     */
    private List<String> scene;
}
