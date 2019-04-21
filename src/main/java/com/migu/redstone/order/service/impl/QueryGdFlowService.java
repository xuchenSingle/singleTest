/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
 */
package com.migu.redstone.order.service.impl;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsStaticData;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.constants.MktCampaignConst;
import com.migu.redstone.constants.RedisKey;
import com.migu.redstone.constants.StaticDataConst;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.request.QueryFlowInfoReq;
import com.migu.redstone.order.service.dto.request.QueryGdServiceReq;
import com.migu.redstone.order.service.dto.response.QueryFlowInfoResp;
import com.migu.redstone.order.service.interfaces.ICmbsGDQueryService;
import com.migu.redstone.order.service.interfaces.IQueryFlowService;
import com.migu.redstone.redis.RedisTemplate;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询广东用户流量信息
 */
@Service
public class QueryGdFlowService implements IQueryFlowService {

    /**
     * logger
     */
	private static final Logger logger = LoggerFactory.getLogger(QueryGdFlowService.class);
    
	/**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate redisTemplate;
    
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
     * update by xuchen 20190218,查询流量返回全部字段
     */
	@Override
	public QueryFlowInfoResp queryFlow(QueryFlowInfoReq flowInfoReq,String provinceCode) throws Exception {
        // GDFlowResult result = new GDFlowResult();

        QueryFlowInfoResp response = new QueryFlowInfoResp();
        String key = CommonUtil.getRedisKey(RedisKey.CommonCacheKey.QUERY_FLOW_INFO_PREFIX,
                flowInfoReq.getPhoneNumber());
		CmbsStaticData cacheSwitch = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
				StaticDataConst.StaticCodeName.CACHE_SWITCH, StaticDataConst.StaticCodeName.CACHE_SWITCH);
        CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
                StaticDataConst.StaticCodeName.CHANNEL_CODE, provinceCode);
        int expire = MktCampaignConst.Number.ZERO;
		//缓存开关，等于1时打开
		if(MktCampaignConst.Number.STRING_ONE.equals(cacheSwitch.getCodeValue())) {
			if (CommonUtil.isNotNull(staticData)) {
				expire = Integer.parseInt(staticData.getCodeValue());
			} else {
				expire = MktCampaignConst.Number.FIVE;
			}
			if (redisTemplate.exists(key)) {
				response = redisTemplate.get(key, QueryFlowInfoResp.class);
				if (CommonUtil.isNotNull(response) && CommonUtil.isNotNull(response.getResult())) {
					if (logger.isDebugEnabled()) {
						logger.debug(">>>>>> process the method GDFlowInfo from redis response="
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
				redisTemplate.expire(key, expire * MktCampaignConst.Number.SIXTY);
			}
		}
        QueryGdServiceReq req = new QueryGdServiceReq();
        req.setPhoneNumber(flowInfoReq.getPhoneNumber());
        req.setChannelid(flowInfoReq.getRequestHeader().getAppid());
        DecimalFormat df = new DecimalFormat("#########.00");
        try {
            String entity = cmbsGDQueryService.queryAllFlow(req);
//       	    String entity = "{\"respcode\":\"0\",\"respdesc\":\"成功\",\"resptype\":\"0\",\"result\":{\"allcanuse\":\"44783.87\",\"allhisused\":\"0.00\",\"allprodtotal\":\"45356.00\",\"alltotal\":\"45356.00\",\"allused\":\"572.12\",\"detailInfo\":{\"detailByDataList\":[{\"left\":\"12290.73\",\"prodinfoList\":[{\"left\":\"0.00\",\"prodCode\":\"\",\"prodId\":\"jieyu\",\"prodName\":\"结余流量\",\"total\":\"0.00\"},{\"left\":\"2048.00\",\"prodCode\":\"HLLTH0\",\"prodId\":\"prod.10086000006476\",\"prodName\":\"0元1G流量特惠包（有效期7天）\",\"total\":\"2048.00\"},{\"left\":\"10066.26\",\"prodCode\":\"KJ10G7\",\"prodId\":\"prod.10086000006478\",\"prodName\":\"0元10G流量特惠包（有效期7天）\",\"total\":\"10240.00\"},{\"left\":\"176.47\",\"prodCode\":\"FXQCB38\",\"prodId\":\"prod.10086000004477\",\"prodName\":\"38元飞享套餐青春版\",\"total\":\"300.00\"}],\"rultypename\":\"国内通用流量\",\"total\":\"12588.00\",\"used\":\"297.27\"}],\"detailByPlanList\":[{\"dataType\":\"nocleardata\",\"prodinfoList\":[{\"left\":\"0.00\",\"prodCode\":\"\",\"prodId\":\"jieyu\",\"prodName\":\"结余流量\",\"prodNum\":\"1\",\"prodType\":\"结余\",\"rulinfoList\":[{\"enddate\":\"20190131\",\"left\":\"0.00\",\"rultypeid\":\"35\",\"rultypename\":\"国内通用流量\",\"startdate\":\"20190101\",\"total\":\"0.00\",\"used\":\"0.00\",\"usedoutmonth\":\"0.00\"}],\"total\":\"0.00\",\"used\":\"0.00\"}],\"total\":\"0.00\",\"useable\":\"0.00\",\"used\":\"0.00\"},{\"dataType\":\"taowaidata\",\"prodinfoList\":[],\"usedexceed\":\"0.00\"},{\"dataType\":\"monthlydata\",\"prodinfoList\":[{\"left\":\"176.47\",\"prodCode\":\"FXQCB38\",\"prodId\":\"prod.10086000004477\",\"prodName\":\"38元飞享套餐青春版\",\"prodNum\":\"1\",\"prodType\":\"月结\",\"rulinfoList\":[{\"enddate\":\"20190228\",\"left\":\"176.47\",\"rultypeid\":\"35\",\"rultypename\":\"国内通用流量\",\"startdate\":\"20190101\",\"total\":\"300.00\",\"used\":\"123.52\",\"usedoutmonth\":\"0.00\"}],\"total\":\"300.00\",\"used\":\"123.52\"}],\"total\":\"300.00\",\"useable\":\"176.47\",\"used\":\"123.52\"},{\"dataType\":\"randomdata\",\"prodinfoList\":[{\"left\":\"10066.26\",\"prodCode\":\"KJ10G7\",\"prodId\":\"prod.10086000006478\",\"prodName\":\"0元10G流量特惠包（有效期7天）\",\"prodNum\":\"1\",\"prodType\":\"灵活\",\"rulinfoList\":[{\"enddate\":\"20190129\",\"left\":\"10066.26\",\"rultypeid\":\"35\",\"rultypename\":\"国内通用流量\",\"startdate\":\"20190122\",\"total\":\"10240.00\",\"used\":\"173.73\",\"usedoutmonth\":\"0.00\"}],\"total\":\"10240.00\",\"used\":\"173.73\"},{\"left\":\"2048.00\",\"prodCode\":\"HLLTH0\",\"prodId\":\"prod.10086000006476\",\"prodName\":\"0元1G流量特惠包（有效期7天）\",\"prodNum\":\"2\",\"prodType\":\"灵活\",\"rulinfoList\":[{\"enddate\":\"20190201\",\"left\":\"2048.00\",\"rultypeid\":\"35\",\"rultypename\":\"国内通用流量\",\"startdate\":\"20190125\",\"total\":\"2048.00\",\"used\":\"0.00\",\"usedoutmonth\":\"0.00\"}],\"total\":\"2048.00\",\"used\":\"0.00\"}],\"total\":\"12288.00\",\"useable\":\"12114.26\",\"used\":\"173.73\"}]},\"dingXiangInfo\":{\"monthprodtotal\":\"30720.00\",\"percent\":\"100\",\"prodInfoList\":[{\"left\":\"30720.00\",\"prodCode\":\"SDMG\",\"prodId\":\"prod.10086000010661\",\"prodName\":\"0元30GB国内APP视频定向流量（咪咕视频）\",\"prodNum\":\"1\",\"prodType\":\"月结\",\"rulinfoList\":[{\"enddate\":\"20190131\",\"left\":\"30720.00\",\"rultypeid\":\"39\",\"rultypename\":\"定向流量\",\"startdate\":\"20190103000000\",\"total\":\"30720.00\",\"used\":\"0.00\",\"usedoutmonth\":\"0.00\"}],\"total\":\"30720.00\",\"used\":\"0.00\"}],\"prodtotal\":\"30720.00\",\"update\":\"20190129150833\",\"useable\":\"30720.00\",\"used\":\"0.00\"},\"otherprod\":{},\"retCode\":\"0\",\"retMsg\":\"成功\",\"retType\":\"0\",\"totalInfo\":{\"average\":\"10.25\",\"cleardate\":\"20190228\",\"percent\":\"98\",\"total\":\"12588.00\",\"useable\":\"12290.73\",\"used\":\"297.26\",\"usedexceed\":\"0.00\"},\"weizhiinfo\":{},\"xianshiinfo\":{\"monthprodtotal\":\"2048.00\",\"percent\":\"87\",\"prodinfolist\":{\"prodinfo\":[{\"prodcode\":\"FXQCB38\",\"prodid\":\"prod.10086000004477\",\"prodname\":\"38元4G飞享套餐青春版\",\"prodnum\":\"1\",\"rulinfolist\":{\"rulinfo\":[{\"endtime\":\"20190131235959\",\"left\":\"1773.14\",\"rultypeid\":\"38\",\"rultypename\":\"国内闲时流量\",\"starttime\":\"20190101000000\",\"total\":\"2048.00\",\"used\":\"274.85\",\"usedoutmonth\":\"0.00\",\"usedtime\":\"\"}]},\"type\":\"月结\"}]},\"prodtotal\":\"2048.00\",\"update\":\"20190129150833\",\"useable\":\"1773.14\",\"used\":\"274.85\",\"xianshiave\":\"9.47\"},\"zhuanzenginfo\":[]}}";
            if (StringUtils.isNotBlank(entity)) {
                JSONObject jsonObject = JSONObject.fromObject(entity);

                String respcode = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_CODE);
                String resptype = jsonObject.getString(MktCampaignConst.GDApplicationService.RESP_TYPE);
                if (IResultCode.GD_SERVICE_SUCCESS_CODE.equals(respcode)
                        && IResultCode.GD_SERVICE_SUCCESS_TYPE.equals(resptype)) {
//                	String totalFlowAmt = MktCampaignConst.Number.STRING_ZERO;
//            		String totalLeftFlowAmt = MktCampaignConst.Number.STRING_ZERO;
            		String dingXiangFlowAmt = MktCampaignConst.Number.STRING_ZERO;
            		String dingXiangLeftFlowAmt = MktCampaignConst.Number.STRING_ZERO;
            		String commonFlowAmt = MktCampaignConst.Number.STRING_ZERO;
            		String commonLeftFlowAmt = MktCampaignConst.Number.STRING_ZERO;
//            		boolean isDingXiang = false;  // 是否有视讯定向
//            		boolean hasDingXiang = false; // 是否有定向
            		String result = jsonObject.getString("result");
            		if (StringUtils.isNotBlank(result)) {
            			JSONObject jsonResult = JSONObject.fromObject(result);
            			if (jsonResult.containsKey("dingXiangInfo")) {
            				JSONObject jsonDingxiangResult = JSONObject.fromObject(jsonResult.getString("dingXiangInfo"));
            				if (jsonDingxiangResult.containsKey("prodInfoList")) {
            					JSONArray jsonArrayProInfo = jsonDingxiangResult.getJSONArray("prodInfoList");
            					if(jsonArrayProInfo != null && jsonArrayProInfo.size() > 0){
//            					  hasDingXiang = true;
            					  for (int i = 0; i < jsonArrayProInfo.size(); i++) {
            						JSONObject json = JSONObject.fromObject(jsonArrayProInfo.get(i));
            						if (json.containsKey("prodId")) {
            							String prodId = json.getString("prodId");
            							CmbsStaticData dingXiangStaticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
            					                StaticDataConst.StaticCodeName.GD_QUERY_FLOW_ORIENT,prodId);
            							if (dingXiangStaticData != null) {
//            								isDingXiang = true;
            								if (json.containsKey("total")) {
            									String value = json.getString("total");
            									if (StringUtils.isNotBlank(value) && !CommonConst.RegExp.LINEAE.equals(dingXiangFlowAmt)) {
            										if (CommonConst.RegExp.LINEAE.equals(value)) {
            											dingXiangFlowAmt = CommonConst.RegExp.LINEAE;
            										} else {
            											dingXiangFlowAmt = df.format(Float
            													.parseFloat((dingXiangFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: dingXiangFlowAmt)
            													+ Float.parseFloat(value));
            										}
            									}
            								}

            								if (json.containsKey("left")) {
            									String value = json.getString("left");
            									if (StringUtils.isNotBlank(value) && !CommonConst.RegExp.LINEAE.equals(dingXiangLeftFlowAmt)) {
            										if (CommonConst.RegExp.LINEAE.equals(value)) {
            											dingXiangLeftFlowAmt = CommonConst.RegExp.LINEAE;
            										} else {
            											dingXiangLeftFlowAmt = df.format(Float
            													.parseFloat((dingXiangLeftFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: dingXiangLeftFlowAmt)
            													+ Float.parseFloat(value));
            										}
            									}									
            								}
            							}
            						}
            					}
            				}
            			}
            		}

            			if (jsonResult.containsKey("totalInfo")) {
            				// 处理通用流量
            				JSONObject jsonTotalInfo = JSONObject.fromObject(jsonResult.getString("totalInfo"));
            				if (jsonTotalInfo.containsKey("total") && !CommonConst.RegExp.LINEAE.equals(commonFlowAmt)) {
            					String total = jsonTotalInfo.getString("total");
            					if(StringUtils.isNotBlank(total)){
            					   if (CommonConst.RegExp.LINEAE.equals(total)) {
            						commonFlowAmt = CommonConst.RegExp.LINEAE;
            					   } else {
            						commonFlowAmt = df.format(Float.parseFloat((commonFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonFlowAmt)
            								+ Float.parseFloat(total));
            					   }
            					}
            				}

            				if (jsonTotalInfo.containsKey("useable")) {
            					String useable = jsonTotalInfo.getString("useable");
            					if (StringUtils.isNotBlank(useable) && !CommonConst.RegExp.LINEAE.equals(commonLeftFlowAmt)) {
            						  if (CommonConst.RegExp.LINEAE.equals(useable)) {
            							  commonLeftFlowAmt = CommonConst.RegExp.LINEAE;
            						    } else {
            							  commonLeftFlowAmt = df.format(Float
            									.parseFloat((commonLeftFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonLeftFlowAmt)
            									+ Float.parseFloat(useable));
            						    }
            						}
            				}
            			}
            			
            			if(jsonResult.containsKey("weizhiinfo")){
            				// 未知流量计算
            				JSONObject jsonTotalInfo = JSONObject.fromObject(jsonResult.getString("weizhiinfo"));
            				if(jsonTotalInfo.containsKey("monthprodtotal") && !CommonConst.RegExp.LINEAE.equals(commonFlowAmt)){
            					String total = jsonTotalInfo.getString("monthprodtotal");
            					if(StringUtils.isNotBlank(total)){
            					   if (CommonConst.RegExp.LINEAE.equals(total)) {
            						commonFlowAmt = CommonConst.RegExp.LINEAE;
            					   } else {
            						commonFlowAmt = df.format(Float.parseFloat((commonFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonFlowAmt)
            								+ Float.parseFloat(total));
            					   }
            					}
            				}
            				
            				if (jsonTotalInfo.containsKey("useable")) {
            					String useable = jsonTotalInfo.getString("useable");
            					if (StringUtils.isNotBlank(useable) && !CommonConst.RegExp.LINEAE.equals(commonLeftFlowAmt)) {
            						  if (CommonConst.RegExp.LINEAE.equals(useable)) {
            							  commonLeftFlowAmt = CommonConst.RegExp.LINEAE;
            						    } else {
            							  commonLeftFlowAmt = df.format(Float
            									.parseFloat((commonLeftFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonLeftFlowAmt)
            									+ Float.parseFloat(useable));
            						    }
            						}
            				}
            			}
            			
            			if(jsonResult.containsKey("xianshiinfo")){
            				// 限时流量计算
            				JSONObject jsonTotalInfo = JSONObject.fromObject(jsonResult.getString("xianshiinfo"));
            				if(jsonTotalInfo.containsKey("monthprodtotal") && !CommonConst.RegExp.LINEAE.equals(commonFlowAmt)){
            					String total = jsonTotalInfo.getString("monthprodtotal");
            					if(StringUtils.isNotBlank(total)){
            					   if (CommonConst.RegExp.LINEAE.equals(total)) {
            						commonFlowAmt = CommonConst.RegExp.LINEAE;
            					   } else {
            						commonFlowAmt = df.format(Float.parseFloat((commonFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonFlowAmt)
            								+ Float.parseFloat(total));
            					   }
            					}
            				}
            				
            				if (jsonTotalInfo.containsKey("useable")) {
            					String useable = jsonTotalInfo.getString("useable");
            					if (StringUtils.isNotBlank(useable) && !CommonConst.RegExp.LINEAE.equals(commonLeftFlowAmt)) {
            						if (CommonConst.RegExp.LINEAE.equals(useable)) {
            							  commonLeftFlowAmt = CommonConst.RegExp.LINEAE;
            						 } else {
            							  commonLeftFlowAmt = df.format(Float
            									.parseFloat((commonLeftFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonLeftFlowAmt)
            									+ Float.parseFloat(useable));
            						 }
            					}
            				}
            			}
            			
            			if(jsonResult.containsKey("zhuanzenginfo")){
            				// 转赠流量计算
            				JSONArray jsonArrayZhuanZengInfo = jsonResult.getJSONArray("zhuanzenginfo");
            				if(jsonArrayZhuanZengInfo != null && jsonArrayZhuanZengInfo.size()>0){
            					for(int i = 0;i < jsonArrayZhuanZengInfo.size();i++){
            						JSONObject json = JSONObject.fromObject(jsonArrayZhuanZengInfo.get(i));
            						if(json.containsKey("grouptotal") && !CommonConst.RegExp.LINEAE.equals(commonFlowAmt)){
            							String total = json.getString("grouptotal");
            							if(StringUtils.isNotBlank(total)){
            								if (CommonConst.RegExp.LINEAE.equals(total)) {
                        						commonFlowAmt = CommonConst.RegExp.LINEAE;
                        					   } else {
                        						commonFlowAmt = df.format(Float.parseFloat((commonFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonFlowAmt)
                        								+ Float.parseFloat(total));
                        					   }
            							}
            						}
            						
            						if(json.containsKey("groupleft")){
            							String left = json.getString("groupleft");
            							if(StringUtils.isNotBlank(left) && !CommonConst.RegExp.LINEAE.equals(commonLeftFlowAmt)){
            								if (CommonConst.RegExp.LINEAE.equals(left)) {
                  							  commonLeftFlowAmt = CommonConst.RegExp.LINEAE;
                  						 } else {
                  							  commonLeftFlowAmt = df.format(Float
                  									.parseFloat((commonLeftFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonLeftFlowAmt)
                  									+ Float.parseFloat(left));
                  						 }
            						   }
            					    }
            					}
            				}
            			}
            			
            			if(jsonResult.containsKey("otherprod")){
            				// 处理另外的流量
            			    String jsonOtherProdStr = jsonResult.getString("otherprod");
            			    if(StringUtils.isNotBlank(jsonOtherProdStr)){
            			    	JSONObject jsonOtherProd = JSONObject.fromObject(jsonOtherProdStr);
            			    	if(jsonOtherProd.containsKey("prodtotal")){
            			    		String total = jsonOtherProd.getString("prototal");
            			    		if(StringUtils.isNotBlank(total)){
        								if (CommonConst.RegExp.LINEAE.equals(total)) {
                    						commonFlowAmt = CommonConst.RegExp.LINEAE;
                    					   } else {
                    						commonFlowAmt = df.format(Float.parseFloat((commonFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonFlowAmt)
                    								+ Float.parseFloat(total));
                    					   }
        							}
            			    	}
            			    	
            			    	if(jsonOtherProd.containsKey("useable")){
        							String left = jsonOtherProd.getString("useable");
        							if(StringUtils.isNotBlank(left) && !CommonConst.RegExp.LINEAE.equals(commonLeftFlowAmt)){
        								if (CommonConst.RegExp.LINEAE.equals(left)) {
              							  commonLeftFlowAmt = CommonConst.RegExp.LINEAE;
              						 } else {
              							  commonLeftFlowAmt = df.format(Float
              									.parseFloat((commonLeftFlowAmt == null) ? CommonConst.NumberBool.FALSE_NUMBER: commonLeftFlowAmt)
              									+ Float.parseFloat(left));
              						 }
        						   }
        					    }
            			    }          			    
            			}
            			
//                        if (isDingXiang) {
                        	// 存在视讯定向
                            response.setTotalFlowAmt(df.format(Float.parseFloat(commonFlowAmt)+Float.parseFloat(dingXiangFlowAmt)));                        
                            response.setTotalLeftFlowAmt(df.format(Float.parseFloat(commonLeftFlowAmt)+Float.parseFloat(dingXiangLeftFlowAmt)));
                            response.setCommonFlowAmt(commonFlowAmt);
                            response.setCommonLeftFlowAmt(commonLeftFlowAmt);
                            response.setDingXiangFlowAmt(dingXiangFlowAmt);
                            response.setDingXiangLeftFlowAmt(dingXiangLeftFlowAmt);

//                        } else if (hasDingXiang) {
//                            // 只存在定向的,不存在视讯的
//                            response.setTotalFlowAmt(df.format(Float.parseFloat(commonFlowAmt)+Float.parseFloat(dingXiangFlowAmt)));                        
//                            response.setTotalLeftFlowAmt(df.format(Float.parseFloat(commonLeftFlowAmt)+Float.parseFloat(dingXiangLeftFlowAmt)));
//                        } else{
//                            response.setTotalFlowAmt(df.format(Float.parseFloat(commonFlowAmt)+Float.parseFloat(dingXiangFlowAmt)));                        
//                            response.setTotalLeftFlowAmt(df.format(Float.parseFloat(commonLeftFlowAmt)+Float.parseFloat(dingXiangLeftFlowAmt)));
//                            response.setCommonFlowAmt(commonFlowAmt);
//                            response.setCommonLeftFlowAmt(commonLeftFlowAmt);
//                        }
            		} else {
                        response.setResult(new Result(IResultCode.SUCCESS_CODE, IResultCode.SUCCESS_MESSAGE));
                        return response;
                    }
                } else {
                    response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, jsonObject.getString("respdesc")));
                    return response;
                }
            }

        } catch (Exception e) {
            logger.error(">>>> process th method  cmbsGDQueryService.queryAllFlow error .e=" + e);
            response.setResult(new Result(IResultCode.REMOTE_SERVICE_ERROR_CODE, "远程调用异常"));
            return response;
        }
        response.setResult(Result.success());
		//缓存开关，等于1时打开
		if(MktCampaignConst.Number.STRING_ONE.equals(cacheSwitch.getCodeValue())) {
			try {
				redisTemplate.set(key, response);
//            CmbsStaticData staticData = cfgCommonService.getCmbsStaticDataByCodeTypeAndCodeName(
//                    StaticDataConst.StaticCodeName.FLOW_EXPIRE_TYPE, StaticDataConst.StaticCodeName.FLOW_EXPIRE_NAME);
//            int expire = MktCampaignConst.Number.ZERO;
				if (CommonUtil.isNotNull(staticData)) {
					expire = Integer.parseInt(staticData.getCodeValue());
				} else {
					expire = MktCampaignConst.Number.FIVE;
				}

				redisTemplate.expire(key, expire * MktCampaignConst.Number.SIXTY);
			} catch (Exception e) {
				logger.error(">>>>> process queryFlowInfo method ,set response into redis error");
			}
		}
        return response;
    }

}
