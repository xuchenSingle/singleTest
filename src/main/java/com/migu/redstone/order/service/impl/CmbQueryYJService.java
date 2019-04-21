/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.cfg.product.common.enums.CmbsChannelType;
import com.migu.redstone.cfg.product.mapper.po.CmbsProdChannelAbilityCfg;
import com.migu.redstone.cfg.product.service.interfaces.ICmbsProdService;
import com.migu.redstone.client.dto.response.EncsMsisdnSectionRes;
import com.migu.redstone.client.service.interfaces.IRestService;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.MktCampaignConst.CreateOrderConst;
import com.migu.redstone.constants.RedisKey;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.dto.GDRequestHeader;
import com.migu.redstone.dto.Header;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.MemberInfo;
import com.migu.redstone.order.service.dto.request.QueryChargeBalanceReq;
import com.migu.redstone.order.service.dto.request.QueryFamilyMemberInfoReq;
import com.migu.redstone.order.service.dto.request.QueryFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.request.QueryYJPlatFormServiceReq;
import com.migu.redstone.order.service.dto.response.QueryChargeBalanceResp;
import com.migu.redstone.order.service.dto.response.QueryFamilyMemberInfoResp;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJPlatFormService;
import com.migu.redstone.order.service.interfaces.ICmbsQueryYJService;
import com.migu.redstone.order.service.interfaces.IQueryFlowService;
import com.migu.redstone.redis.RedisTemplate;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;
import com.migu.redstone.utils.SpringContextUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类作用: CmbQueryExclusiveFlowUseService 项目名称: cmbs-query-order 包:
 * com.migu.redstone.order.service.impl 类名称: cmbs-query-order服务实现类 类描述:
 * CmbQueryExclusiveFlowUseService 创建人: xuchen 创建时间: 2018年11月5日 下午12:48:51
 */
@Service
public class CmbQueryYJService implements ICmbsQueryYJService {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CmbQueryYJService.class);
	/**
	 * cmbsGDQueryService
	 */
	@Autowired
	private ICmbsGDQueryService cmbsGDQueryService;
	/**
	 * cfgCommonService
	 */
	@Autowired
	private ICfgCommonService cfgCommonService;
	/**
	 * cmbsQueryYJPlatFormService
	 */
	@Autowired
	private ICmbsQueryYJPlatFormService cmbsQueryYJPlatFormService;
	/**
	 * encsClient
	 */
	@Autowired
	private IRestService restService;
	/**
	 * redisTemplate
	 */
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * cmbsProdService.
	 */
	@Autowired
	private ICmbsProdService cmbsProdService;

	// /**
	// * proxySwtich
	// */
	// @Value("${YJplatForm.proxySwtich}")
	// private boolean proxySwtich;
	// /**
	// * proxyHost
	// */
	// @Value("${YJplatForm.proxyHost}")
	// private String proxyHost;
	// /**
	// * proxyPort
	// */
	// @Value("${YJplatForm.proxyPort}")
	// private int proxyPort;

	@Override
	public QueryFlowInfoResp queryFlowInfo(QueryFlowInfoReq flowInfoReq) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>>>>> process the method queryFlowInfo  req=" + JsonUtil.objectToString(flowInfoReq));
		}
		String phoneNumber = flowInfoReq.getPhoneNumber();

		QueryFlowInfoResp flowInfoResp = new QueryFlowInfoResp();
		// 查询号码归属地
		String provinceCode = "";
		EncsMsisdnSectionRes resp = new EncsMsisdnSectionRes();
		try {
//			 resp.setProvinceCode("250");
			resp = restService.getMsisdnSection(phoneNumber);
			if (CommonUtil.isNotNull(resp) && StringUtils.isNotBlank(resp.getProvinceCode())) {
				provinceCode = resp.getProvinceCode();
			}
		} catch (Exception e) {
			LOGGER.error("CmbQueryYJService.queryMsisdnSection, resp=" + JsonUtil.objectToString(resp));
		}

		// 省份编码
		LOGGER.error("CmbQueryYJService.queryMsisdnSection, provinceCode=" + provinceCode);

		// 获取产品Id配置
		CmbsStaticData cmbsStaticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
				StaticDataConst.StaticCodeType.QUERY_FLOW_PRODUCT,
				StaticDataConst.StaticCodeName.QUERY_FLOW_PRODUCT_CODE_NAME);
		String productId = "";
		if (cmbsStaticData != null && StringUtils.isNotBlank(cmbsStaticData.getCodeValue())) {
			productId = cmbsStaticData.getCodeValue();
		}

		// 获取产品分渠道能力配置-获取processName
		String processName = "";
		if (StringUtils.isNotBlank(productId)) {
			String channelCode = CmbsChannelType.PROVINCE.getValue() + provinceCode;
			CmbsProdChannelAbilityCfg cmbsProdChannelAbilityCfg = cmbsProdService
					.getCmbsProdChannelAbilityCfgByProductIdAndChannelAndType(Long.parseLong(productId), channelCode,
							CreateOrderConst.OPER_TYPE_PAYMENT);
			if (cmbsProdChannelAbilityCfg != null) {
				processName = cmbsProdChannelAbilityCfg.getProcessName();
			}
		}

		// 处理业务
		if (StringUtils.isNotBlank(processName)) {
			IQueryFlowService queryFlowService = (IQueryFlowService) SpringContextUtil.getBean(processName);
			flowInfoResp = queryFlowService.queryFlow(flowInfoReq,provinceCode);
		} else {
			flowInfoResp.setResult(new Result(IResultCode.PRODUCTID_NOT_MATCH_CHANNELCODE, "业务办理失败，上游未提供该能力"));
		}
		LOGGER.error(">>>>>> process the method queryFlowInfo end  rsp=" + JsonUtil.objectToString(flowInfoResp));
		return flowInfoResp;
	}

	// /**
	// * 江苏流量查询
	// */
	// private JSFlowResult JSFlowInfo(String phoneNumber) {
	// JSFlowResult result = new JSFlowResult();
	// JsPlayerMarketingPositionFlowerRes rsp = null;
	//// jsFeignClient.queryMiguFlower(this.encryptAES(phoneNumber,
	// jsPlatform.getAESKey()));
	// JsPlayerMarketingPositionFlowerRetObjRes retObj = rsp.getRetObj();
	// result.setBuscode(result.getBuscode());
	// result.setMessage(retObj.getMessage());
	// result.setPercentage(result.getPercentage());
	// result.setType(retObj.getType().toString());
	// result.setUrl(retObj.getUrl());
	// return result;
	// }

	/**
	 * 广东流量查询
	 *
	 * @param flowInfoReq
	 *            [QueryFlowInfoReq]
	 * @throws Exception
	 *             [Exception]
	 * @return QueryFlowInfoResp
	 */
	private QueryFlowInfoResp GDFlowInfo(QueryFlowInfoReq flowInfoReq) throws Exception {
		// GDFlowResult result = new GDFlowResult();

		QueryFlowInfoResp response = new QueryFlowInfoResp();
		String key = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
				flowInfoReq.getPhoneNumber());
		if (redisTemplate.exists(key)) {
			response = redisTemplate.get(key, QueryFlowInfoResp.class);
			if (CommonUtil.isNotNull(response) && CommonUtil.isNotNull(response.getResult())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(">>>>>> process the method GDFlowInfo from redis response="
							+ JsonUtil.objectToString(response));
				}
				return response;
			} else {
				QueryFlowInfoResp resp = new QueryFlowInfoResp();
				resp.setResult(new Result(IResultCode.QUERY_GD_ERROR, "系统繁忙，请稍后"));
				return resp;
			}
		} else {
			response.setResult(null);
			redisTemplate.set(key, response);
		}

		QueryGdServiceReq req = new QueryGdServiceReq();
		req.setPhoneNumber(flowInfoReq.getPhoneNumber());
		req.setChannelid(flowInfoReq.getRequestHeader().getAppid());

		try {
			String entity = cmbsGDQueryService.queryAllFlow(req);
			if (StringUtils.isNotBlank(entity)) {
				JSONObject jsonObject = JSONObject.fromObject(entity);

				String respcode = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_CODE);
				String resptype = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_TYPE);
				if (IResultCode.GD_SERVICE_SUCCESS_CODE.equals(respcode)
						&& IResultCode.GD_SERVICE_SUCCESS_TYPE.equals(resptype)) {

					String totalFlowAmt = null;
					String totalUsedFlowAmt = null;
					String totalLeftFlowAmt = null;
					String result = jsonObject.getString("result");
					if (StringUtils.isNotBlank(result)) {
						JSONObject jsonResult = JSONObject.fromObject(result);
						if (null != jsonResult) {
							// 总流量
							if (StringUtils.isNotBlank(jsonResult.getString("alltotal"))) {
								totalFlowAmt = jsonResult.getString("alltotal");
							}

							if (StringUtils.isNotBlank(jsonResult.getString("allused"))) {
								totalUsedFlowAmt = jsonResult.getString("allused");
							}

							if (StringUtils.isNotBlank(jsonResult.getString("allcanuse"))) {
								totalLeftFlowAmt = jsonResult.getString("allcanuse");
							}
						}

						String dingXiangFlowAmt = null;
						String dingXiangUsedFlowAmt = null;

						// J定向流量
						if (jsonResult.containsKey("dingXiangInfo")) {
							String dingXiangInfoResult = jsonResult.getString("dingXiangInfo");
							JSONObject jsonDingxiangResult = JSONObject.fromObject(dingXiangInfoResult);

							if (jsonDingxiangResult.containsKey("monthprodtotal")
									&& StringUtils.isNotBlank(jsonDingxiangResult.getString("monthprodtotal"))) {
								dingXiangFlowAmt = jsonDingxiangResult.getString("monthprodtotal");
							}

							if (jsonDingxiangResult.containsKey("used")
									&& StringUtils.isNotBlank(jsonDingxiangResult.getString("used"))) {
								dingXiangUsedFlowAmt = jsonDingxiangResult.getString("used");
							}
						}
						// 通用流量
						String commonTotal = null;
						String commonUsedLeft = null;
						String commonUsedFlowAmt = null;
						if (jsonResult.containsKey("totalInfo")) {
							String totalInfoResult = jsonResult.getString("totalInfo");
							JSONObject totalInfoObj = JSONObject.fromObject(totalInfoResult);

							if (totalInfoObj.containsKey("total")
									&& StringUtils.isNotBlank(totalInfoObj.getString("total"))) {
								commonTotal = totalInfoObj.getString("total");
							}

							if (totalInfoObj.containsKey("useable")
									&& StringUtils.isNotBlank(totalInfoObj.getString("useable"))) {
								commonUsedLeft = totalInfoObj.getString("useable");
							}
							if (totalInfoObj.containsKey("used")
									&& StringUtils.isNotBlank(totalInfoObj.getString("used"))) {
								commonUsedFlowAmt = totalInfoObj.getString("used");
							}
						}

						// 通用定向都有值
						if (StringUtils.isNotBlank(commonTotal) && StringUtils.isNotBlank(dingXiangFlowAmt)) {
							response.setTotalFlowAmt(totalFlowAmt);
							// response.setTotalUsedFlowAmt(totalUsedFlowAmt);
							response.setTotalLeftFlowAmt(totalLeftFlowAmt);

						} else if (StringUtils.isBlank(commonTotal) && StringUtils.isNotBlank(dingXiangFlowAmt)
								&& StringUtils.isBlank(totalFlowAmt)) {
							// 只存在定向的
						} else if (StringUtils.isNotBlank(commonTotal) && StringUtils.isBlank(dingXiangFlowAmt)) {
							response.setTotalFlowAmt(totalFlowAmt);
							// response.setTotalUsedFlowAmt(totalUsedFlowAmt);
							response.setCommonFlowAmt(commonTotal);
							// response.setCommonUsedFlowAmt(commonUsedFlowAmt);
							response.setTotalLeftFlowAmt(totalLeftFlowAmt);
							response.setCommonLeftFlowAmt(commonUsedLeft);

						}

					} else {
						response.setResult(new Result(IResultCode.SUCCESS_CODE, IResultCode.SUCCESS_MESSAGE));
						return response;
					}
				} else {
					response.setResult(
							new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, jsonObject.getString("respdesc")));
					return response;
				}
			}

		} catch (Exception e) {
			LOGGER.error(">>>> process th method  cmbsGDQueryService.queryAllFlow error .e=" + e);
			response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "远程调用异常"));
			return response;
		}
		response.setResult(Result.success());
		try {
			redisTemplate.set(key, response);
			CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
					StaticDataConst.StaticCodeName.FLOW_EXPIRE_TYPE, StaticDataConst.StaticCodeName.FLOW_EXPIRE_NAME);
			int expire = MktCampaignConst.Number.ZERO;
			if (CommonUtil.isNotNull(staticData)) {
				expire = Integer.parseInt(staticData.getCodeValue());
			} else {
				expire = MktCampaignConst.Number.FIVE;
			}

			redisTemplate.expire(key, expire * MktCampaignConst.Number.SIXTY);
		} catch (Exception e) {
			LOGGER.error(">>>>> process queryFlowInfo method ,set response into redis error");
		}
		return response;
	}

	/**
	 * 查询话费余额（广东接口，提供给视讯使用）
	 * 
	 * @param req
	 *            [QueryChargeBalanceReq]
	 * @throws Exception
	 *             [Exception]
	 * @return QueryChargeBalanceResp
	 */

	private QueryChargeBalanceResp queryChargeBalanceGDProvince(QueryChargeBalanceReq req) throws Exception {

		LOGGER.error(">>>>> enter the method  queryChargeBalance . req=" + JsonUtil.objectToString(req));
		QueryChargeBalanceResp response = new QueryChargeBalanceResp();
		QueryGdServiceReq accountsBalanceReq = new QueryGdServiceReq();
		Header requestHeader = req.getRequestHeader();
		GDRequestHeader header = new GDRequestHeader();
		header.setAppId(requestHeader.getAppid());
		accountsBalanceReq.setGdRequestHeader(header);
		accountsBalanceReq.setChannelid(requestHeader.getAppid());
		accountsBalanceReq.setPhoneNumber(req.getPhoneNumber());

		try {
			String entity = cmbsGDQueryService.queryAccountsBalance(accountsBalanceReq);
			if (StringUtils.isNotBlank(entity)) {
				JSONObject jsonObject = JSONObject.fromObject(entity);
				if (null != jsonObject) {
					String respcode = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_CODE);
					String resptype = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_TYPE);
					if (IResultCode.GD_SERVICE_SUCCESS_CODE.equals(respcode)
							&& IResultCode.GD_SERVICE_SUCCESS_TYPE.equals(resptype)) {
						String result = jsonObject.getString("result");
						if (StringUtils.isNotBlank(result)) {
							Document doc = DocumentHelper.parseText(result);
							JSONObject dataBeanObj = new JSONObject();
							// 把xml文件转化为jsonObject
							dom4j2Json(doc.getRootElement(), dataBeanObj);

							if (null != dataBeanObj) {
								if (dataBeanObj.containsKey("totalbalnce")
										&& StringUtils.isNotBlank(dataBeanObj.getString("totalbalnce"))) {
									response.setTotalAvailableAmt(dataBeanObj.getString("totalbalnce"));
								} else {
									response.setTotalAvailableAmt(MktCampaignConst.Number.ZERO_PRICE);
								}
								if (dataBeanObj.containsKey("availbalance")
										&& StringUtils.isNotBlank(dataBeanObj.getString("availbalance"))) {
									response.setCurrentAvailableAmt(dataBeanObj.getString("availbalance"));
								} else {
									response.setCurrentAvailableAmt(MktCampaignConst.Number.ZERO_PRICE);
								}
							}

						} else {
							response.setResult(
									new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, IResultCode.SUCCESS_MESSAGE));
							return response;
						}

					} else {
						response.setResult(
								new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, jsonObject.getString("respdesc")));
						return response;
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("<<<<< process the method  queryChargeBalance error. e=" + e);
			response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
		}

		LOGGER.error("<<<<< end the method  queryChargeBalance . rsp=" + JsonUtil.objectToString(response));
		return response;
	}

	/**
	 * 查询话费余额（广东接口，提供给视讯使用）
	 */
	@Override
	public QueryChargeBalanceResp queryChargeBalance(QueryChargeBalanceReq req) throws Exception {

		QueryChargeBalanceResp response = new QueryChargeBalanceResp();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>>>>> process the method queryFlowInfo  req=" + JsonUtil.objectToString(req));
		}
		String phoneNumber = req.getPhoneNumber();
		// 查询号码归属地
		String provinceCode = "";
		EncsMsisdnSectionRes resp = new EncsMsisdnSectionRes();
		try {
			resp = restService.getMsisdnSection(phoneNumber);
			if (CommonUtil.isNotNull(resp) && StringUtils.isNotBlank(resp.getProvinceCode())) {
				provinceCode = resp.getProvinceCode();
			}
		} catch (Exception e) {
			LOGGER.error("CmbQueryYJService.queryMsisdnSection, resp=" + JsonUtil.objectToString(resp));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>>>>> process the method queryFlowInfo   provinceCode=" + provinceCode);
		}

		// 广东
		if (CommonConst.ProvinceCode.GUANGDONG_PROVINCE_CODE.equals(provinceCode)) {
			response = this.queryChargeBalanceGDProvince(req);
		} else {
			QueryYJPlatFormServiceReq platFomServiceReq = new QueryYJPlatFormServiceReq();
			platFomServiceReq.setServiceNumber(req.getPhoneNumber());
			response = cmbsQueryYJPlatFormService.queryAcountBalance(platFomServiceReq);
		}

		// 手机号码加密

		return response;
	}

	/**
	 * 查询家庭网成员信息(广东接口，提供给视讯使用)
	 */
	@Override
	public QueryFamilyMemberInfoResp queryFamilyMemberInfo(QueryFamilyMemberInfoReq req) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>>>> enter the method  queryFamilyMemberInfo . req=" + JsonUtil.objectToString(req));
		}
		QueryFamilyMemberInfoResp respone = new QueryFamilyMemberInfoResp();
		QueryGdServiceReq gdServiceReq = new QueryGdServiceReq();
		Header requestHeader = req.getRequestHeader();
		GDRequestHeader header = new GDRequestHeader();
		header.setAppId(requestHeader.getAppid());
		gdServiceReq.setGdRequestHeader(header);
		gdServiceReq.setPhoneNumber(req.getPhoneNumber());
		gdServiceReq.setChannelid(requestHeader.getAppid());
		// String entity =
		// cmbsGDQueryService.queryAccountsBalance(accountsBalanceReq);
		try {
			String entity = cmbsGDQueryService.queryDHQLWMembers(gdServiceReq);
			// String entity =
			// "{\"respcode\":\"1\",\"respdesc\":\"未申请此业务\",\"resptype\":\"600\",\"result\":{\"list\":{}}}";

			if (StringUtils.isNotBlank(entity)) {
				JSONObject jsonObject = JSONObject.fromObject(entity);

				String respcode = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_CODE);
				String resptype = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_TYPE);
				if (IResultCode.GD_SERVICE_SUCCESS_CODE.equals(respcode)
						&& IResultCode.GD_SERVICE_SUCCESS_TYPE.equals(resptype)) {
					List<MemberInfo> memberList = new ArrayList<MemberInfo>();
					String result = jsonObject.getString("result");
					if (StringUtils.isBlank(result)) {
						respone.setResult(new Result(IResultCode.QUERY_GD_ERROR, "查询家庭网成员信息异常"));
						return respone;
					}

					JSONObject jsonObjectResult = JSONObject.fromObject(result);
					if (null == jsonObjectResult) {
						return respone;
					}
					String shorInfoList = jsonObjectResult.getString("list");
					if (StringUtils.isBlank(shorInfoList)) {
						return respone;
					}
					JSONObject jsonObjectShortinfo = JSONObject.fromObject(shorInfoList);
					if (null == jsonObjectShortinfo) {
						return respone;
					}
					String memerinfoList = jsonObjectShortinfo.getString("shortnumbermemberdetailinfomation");
					if (StringUtils.isBlank(shorInfoList)) {
						return respone;
					}
					JSONArray jsonArray = JSONArray.fromObject(memerinfoList);
					if (null != jsonArray) {
						int len = jsonArray.size();
						for (int i = MktCampaignConst.Number.ZERO; i < len; i++) {
							JSONObject listObj = (JSONObject) jsonArray.get(i);
							MemberInfo memberInfo = new MemberInfo();
							if (null != listObj) {
								if (listObj.containsKey("shortnumber")) {
									String shortNumber = listObj.getString("shortnumber");
									memberInfo.setShortNumber(shortNumber);
									if ("551".equals(shortNumber)) {
										memberInfo.setIsMainNumber("1");
									} else {
										memberInfo.setIsMainNumber("2");
									}
								}
								if (listObj.containsKey("number")) {
									memberInfo.setPhoneNumber(listObj.getString("number"));
								}

								if (listObj.containsKey("effecttime")) {
									memberInfo.setInvalidDate(listObj.getString("effecttime"));
								}

								if (listObj.containsKey("invalidtime")) {
									memberInfo.setExpireDate(listObj.getString("invalidtime"));
								}
								memberList.add(memberInfo);
							}
						}
						respone.setMemberList(memberList);
					} else {
						respone.setResult(
								new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, jsonObject.getString("respdesc")));
						LOGGER.error("<<<<< end the method  queryFamilyMemberInfo . rsp="
								+ JsonUtil.objectToString(respone));
						return respone;
					}

				} else {
					respone.setResult(
							new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, jsonObject.getString("respdesc")));
					LOGGER.error(
							"<<<<< end the method  queryFamilyMemberInfo . rsp=" + JsonUtil.objectToString(respone));
					return respone;
				}
			}
		} catch (Exception e) {
			LOGGER.error("<<<<< end the method  queryFamilyMemberInfo . error=" + e);
			respone.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "调用上游返回异常"));
			return respone;
		}

		LOGGER.error("<<<<< end the method  queryFamilyMemberInfo . rsp=" + JsonUtil.objectToString(respone));

		return respone;
	}

	/**
	 *
	 * 〈xml数据转化为JSONObject〉 〈xml数据转化为JSONObject〉
	 *
	 * @param element
	 *            [Element]
	 * @param json
	 *            [JSONObject]
	 * @return JSONObject
	 */
	private JSONObject dom4j2Json(Element element, JSONObject json) {
		// 如果是属性
		for (Object o : element.attributes()) {
			Attribute attr = (Attribute) o;
			if (StringUtils.isNotBlank(attr.getValue())) {
				json.put("@" + attr.getName(), attr.getValue());
			}
		}
		List<Element> chdEl = element.elements();
		if (chdEl.isEmpty() && StringUtils.isNotBlank(element.getText())) { // 如果没有子元素,只有一个值
			json.put(element.getName(), element.getText());
		}

		for (Element e : chdEl) { // 有子元素
			if (!e.elements().isEmpty()) { // 子元素也有子元素
				JSONObject chdjson = new JSONObject();
				dom4j2Json(e, chdjson);
				Object o = json.get(e.getName());
				if (o != null) {
					JSONArray jsona = null;
					if (o instanceof JSONObject) { // 如果此元素已存在,则转为jsonArray
						JSONObject jsono = (JSONObject) o;
						json.remove(e.getName());
						jsona = new JSONArray();
						jsona.add(jsono);
						jsona.add(chdjson);
					}
					if (o instanceof JSONArray) {
						jsona = (JSONArray) o;
						jsona.add(chdjson);
					}
					json.put(e.getName(), jsona);
				} else {
					if (!chdjson.isEmpty()) {
						json.put(e.getName(), chdjson);
					}
				}

			} else { // 子元素没有子元素
				for (Object o : element.attributes()) {
					Attribute attr = (Attribute) o;
					if (StringUtils.isNotBlank(attr.getValue())) {
						json.put("@" + attr.getName(), attr.getValue());
					}
				}
				if (!e.getText().isEmpty()) {
					json.put(e.getName(), e.getText());
				}
			}
		}

		return json;
	}

}
