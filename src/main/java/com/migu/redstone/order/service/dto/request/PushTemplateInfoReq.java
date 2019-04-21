package com.migu.redstone.order.service.dto.request;


import com.migu.redstone.dto.Header;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * PushTemplateInfoReq
 * 6.1.3	消息模板接收接口
 * 接入渠道->能力平台
 * @author zhuhd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushTemplateInfoReq {
    /**
     * 公共请求头
     */
    @Valid
    @NotNull(message = "参数requestHeader不能为空")
    private Header requestHeader;
    /**
     * 分省id,用于区分消息来自哪个分省
     */
    @NotBlank(message = "参数provinceId不能为空")
    @Length(max = 3, message = "provinceId字段长度最长3")
    private String provinceId;
    /**
     * 消息ID,推送平台内的消息ID
     */
    @NotBlank(message = "参数msgId不能为空")
    @Length(max = 128, message = "msgId字段长度最长128")
    private String msgId;
    /**
     * 消息主标题
     */
    @NotBlank(message = "参数title不能为空")
    @Length(max = 21, message = "消息主标题字段长度最长21")
    private String title;
    /**
     * 消息副标题
     */
    @NotBlank(message = "参数subTitle不能为空")
    @Length(max = 68, message = "消息副标题字段长度最长68")
    private String subTitle;
    /**
     * 内容类型
     */
    @NotBlank(message = "参数contentType不能为空")
    @Length(max = 2, message = "内容类型字段长度最长2")
    private String contentType;
    /**
     * 内容id或者url
     */
    @NotBlank(message = "参数contentValue不能为空")
    @Length(max = 128, message = "contentValue字段长度最长128")
    private String contentValue;
    /**
     * 消息展示方式：
     * 1:系统通知
     * 2:消息中心
     * 3:系统通知和消息中心
     * 固定为1
     */
    @NotBlank(message = "参数showType不能为空")
    @Length(max = 1, message = "showType字段长度最长1")
    private String showType;
    /**
     * 时间戳
     */
    @NotBlank(message = "参数timestamp不能为空")
    @Length(max = 14, message = "timestamp字段长度最长14")
    private String timestamp;
    /**
     * 消息状态,APPROVED:已批准的，无需再审批，UNAPPROVER:未批准的，需要再审批,默认UNAPPROVER，河南默认置空即可
     */
    @Length(max = 16, message = "status字段长度最长16")
    private String status;
    /**
     * 扩展字段
     * {“keyName1”:“keyValue1”,“keyName2”:“keyValue2”}
     */
    private Map extendProperty;
}
