package com.migu.redstone.order.service.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.migu.redstone.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 山东offer推荐
 * 
 * @author wfl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRecommendOfferReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 公共请求头
	 */
	@Valid
	@NotNull(message = "参数requestHeader不能为空")
	private Header requestHeader;

	/**
	 * 用户手机号
	 */
	@NotBlank(message = "参数phoneNumber不能为空")
	@Length(max = 11, min = 11, message = "phoneNumber字段长度为11")
	private String phoneNumber;

	/**
	 * 事件编码 客服系统营销助手 CRM_BSACKF_LOGIN 营销助手 CRM_BSACHAL_LOGIN APP渠道接入事件
	 * APP_CHANNEL_LOGIN 便利店手机号码登陆事件 CRM_BSACCVS_LOGIN 网厅登陆事件 CRM_BSACNB_LOGIN
	 * 自助终端充值 CRM_BSACATSV_CHARGE
	 */
	@NotBlank(message = "参数eventCode不能为空")
	@Length(max = 32, message = "eventCode长度不能超过32")
	private String eventCode;

	/**
	 * 版本号，固定传1.0.1
	 */
	@NotBlank(message = "参数version不能为空")
	@Length(max = 5, message = "version长度不能超过5")
	private String version;

	/**
	 * 根据事件编码传递属性。具体渠道对接事件与智能营销平台(ISSS)商议，参考7.8 ISSS外部接口事件
	 */
	private List<Map<String, Object>> eventAttrMap;
}
