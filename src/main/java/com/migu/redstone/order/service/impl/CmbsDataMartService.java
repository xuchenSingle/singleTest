package com.migu.redstone.order.service.impl;

import com.migu.redstone.client.dto.model.*;
import com.migu.redstone.client.dto.request.DataMartUserTagReq;
import com.migu.redstone.client.dto.response.DataMartUserTagByMsisdnResp;
import com.migu.redstone.client.dto.response.DataMartUserTagResp;
import com.migu.redstone.client.service.interfaces.IDataMartClientService;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.dto.model.*;
import com.migu.redstone.order.service.dto.request.BatchQueryUserTagsReq;
import com.migu.redstone.order.service.dto.request.QueryUserTagsByMsisdnReq;
import com.migu.redstone.order.service.dto.response.BatchQueryUserTagsResp;
import com.migu.redstone.order.service.dto.response.QueryUserTagsByMsisdnResp;
import com.migu.redstone.order.service.interfaces.ICmbsDataMartService;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类作用: 调用数据集市接口实现类
 * 项目名称: RainbowStone-cmbs
 * 包: com.migu.redstone.order.service.impl
 * 类名称: CmbsDataMartService
 * 类描述: 调用数据集市接口实现类
 * 创建人: yangyuan3
 * 创建时间: 2019/01/25 14:57
 */
@Service
public class CmbsDataMartService implements ICmbsDataMartService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsDataMartService.class);

    @Autowired
    private IDataMartClientService dataMartClientService;

    @Override
    public QueryUserTagsByMsisdnResp queryUserTagsByMsisdn(QueryUserTagsByMsisdnReq req) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("enter the method queryUserTagsByMsisdn. req=" + JsonUtil.objectToString(req));
        }

        DataMartUserTagByMsisdnResp dataMartUserTagByMsisdnResp = dataMartClientService.getUserTagByMsisdn(req.getMsisdn());
        return checkDataMartUserTagByMsisdnResp(dataMartUserTagByMsisdnResp);
    }

    @Override
    public BatchQueryUserTagsResp batchQueryUserTags(BatchQueryUserTagsReq req) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("enter the method batchQueryUserTags. req=" + JsonUtil.objectToString(req));
        }

        //拼接入参
        DataMartUserTagReq dataMartUserTagReq = new DataMartUserTagReq();
        UserTagsQueryCondition userTagsQueryCondition = req.getUserTagsQueryCondition();
        List<DataMartSearchCondition> searchConditions = new ArrayList<>();
        List<DataMartSearchCondition> jsSearchConditions = userTagsQueryCondition.getSearchConditions();
        searchConditions.addAll(jsSearchConditions);
        dataMartUserTagReq.setSearchConditions(searchConditions);
        dataMartUserTagReq.setDisplayColumns(userTagsQueryCondition.getDisplayColumns());
        UserTagQueryPageInfo userTagQueryPageInfo = userTagsQueryCondition.getPageInfo();
        DataMartPageInfo dataMartPageInfo = new DataMartPageInfo();
        if (null != userTagQueryPageInfo){
            if (CommonUtil.isGreaterEqualZero(userTagQueryPageInfo.getCurrentPage())){
                dataMartPageInfo.setCurrentPage(userTagQueryPageInfo.getCurrentPage());
            }else {
                dataMartPageInfo.setCurrentPage(0);
            }
            if (CommonUtil.isGreaterEqualZero(userTagQueryPageInfo.getPageSize())){
                if (userTagQueryPageInfo.getPageSize().intValue() > 200){
                    dataMartPageInfo.setPageSize(200);
                }else {
                    dataMartPageInfo.setPageSize(userTagQueryPageInfo.getPageSize());
                }
            }else {
                dataMartPageInfo.setPageSize(200);
            }
            if (null != userTagQueryPageInfo.getShowOnlyCount()){
                dataMartUserTagReq.setShowOnlyCount(userTagQueryPageInfo.getShowOnlyCount());
            }else {
                dataMartUserTagReq.setShowOnlyCount(false);
            }
        }

        DataMartUserTagResp dataMartUserTagResp = dataMartClientService.getUserTag(dataMartUserTagReq);
        return checkDataMartUserTagResp(dataMartUserTagResp);
    }

    private QueryUserTagsByMsisdnResp checkDataMartUserTagByMsisdnResp(DataMartUserTagByMsisdnResp resp){
        QueryUserTagsByMsisdnResp queryUserTagsByMsisdnResp = new QueryUserTagsByMsisdnResp();
        if (CommonUtil.isEqualZero(resp.getRspcode())){
            queryUserTagsByMsisdnResp.setResult(Result.success());
            queryUserTagsByMsisdnResp.setStatus(CommonConst.DataMartConst.SUCCESS_STATUS);
            //转换出参实体
            DataMartUserTagByMsisdn data = resp.getData();
            if (null != data){
                List<DataMartUserTagGroupByMsisdn> groups = data.getGroups();
                List<UserTagGroup> uerTagGroups = new ArrayList<>();
                if (null != groups && !groups.isEmpty()){
                    for (DataMartUserTagGroupByMsisdn group : groups){
                        UserTagGroup userTagGroup = new UserTagGroup();
                        List<UserTagLabel> userTagLabels = new ArrayList<>();
                        userTagGroup.setGroupName(group.getGroupName());
                        List<DataMartUserTagLabelByMsisdn> labels = group.getLabels();
                        if (null != labels && !labels.isEmpty()){
                            for (DataMartUserTagLabelByMsisdn label : labels){
                                UserTagLabel userTagLabel = new UserTagLabel();
                                userTagLabel.setKey(label.getKey());
                                userTagLabel.setValue(label.getValue());
                                userTagLabels.add(userTagLabel);
                            }
                        }
                        userTagGroup.setUserTagLabels(userTagLabels);
                        uerTagGroups.add(userTagGroup);
                    }
                }
                queryUserTagsByMsisdnResp.setUerTagGroups(uerTagGroups);
            }
        }else {
            Result result = new Result();
            result.setResultCode(IResultCode.REMOTE_SERVICE_ERROR_CODE);
            if (StringUtils.isBlank(resp.getRspdesc())){
                result.setResultMessage("请求数据集市失败");
            }else {
                result.setResultMessage(resp.getRspdesc());
            }
            queryUserTagsByMsisdnResp.setResult(result);
            queryUserTagsByMsisdnResp.setStatus(CommonConst.DataMartConst.FAIL_STATUS);
        }
        return queryUserTagsByMsisdnResp;
    }

    private BatchQueryUserTagsResp checkDataMartUserTagResp(DataMartUserTagResp resp){
        BatchQueryUserTagsResp batchQueryUserTagsResp = new BatchQueryUserTagsResp();
        if (CommonUtil.isEqualZero(resp.getRspcode())){
            batchQueryUserTagsResp.setResult(Result.success());
            batchQueryUserTagsResp.setStatus(CommonConst.DataMartConst.SUCCESS_STATUS);
            DataMartUserTag data = resp.getData();
            batchQueryUserTagsResp.setTotalCount(data.getTotalCount());
            batchQueryUserTagsResp.setCurrentPage(data.getCurrentPage());
            //转换出参实体
            List<DataMartUserTagGroups> results = data.getResult();
            List<UserTagGroupPagingInfo> userTagGroupInfoList = new ArrayList<>();
            if (null != results && !results.isEmpty()){
                for (DataMartUserTagGroups result : results){
                    List<DataMartUserTagGroup> groups = result.getGroups();
                    if (null != groups && !groups.isEmpty()){
                        for (DataMartUserTagGroup group : groups){
                            UserTagGroupPagingInfo userTagGroupPagingInfo = new UserTagGroupPagingInfo();
                            List<UserTagLabelPagingInfo> userTagLabelPagingInfoList = new ArrayList<>();
                            userTagGroupPagingInfo.setGroupName(group.getGroupName());
                            List<DataMartUserTagLabel> labels = group.getLabels();
                            if (null != labels && !labels.isEmpty()){
                                for (DataMartUserTagLabel label : labels){
                                    UserTagLabelPagingInfo userTagLabelPagingInfo = new UserTagLabelPagingInfo();
                                    List<UserTagValueInfo> userTagValueInfoList = new ArrayList<>();
                                    userTagLabelPagingInfo.setKey(label.getKey());
                                    List<DataMartUserTagLabelValue> values = label.getValues();
                                    if (null != values && !values.isEmpty()){
                                        for (DataMartUserTagLabelValue value : values){
                                            UserTagValueInfo userTagValueInfo = new UserTagValueInfo();
                                            userTagValueInfo.setValue(value.getValue());
                                            userTagValueInfo.setWeight(value.getWeight());
                                            userTagValueInfoList.add(userTagValueInfo);
                                        }
                                    }
                                    userTagLabelPagingInfo.setValues(userTagValueInfoList);
                                    userTagLabelPagingInfoList.add(userTagLabelPagingInfo);
                                }
                            }
                            userTagGroupPagingInfo.setGroups(userTagLabelPagingInfoList);
                            userTagGroupInfoList.add(userTagGroupPagingInfo);
                        }
                    }
                }
                batchQueryUserTagsResp.setUserTagGroupInfoList(userTagGroupInfoList);
            }
        }else {
            Result result = new Result();
            result.setResultCode(IResultCode.REMOTE_SERVICE_ERROR_CODE);
            if (StringUtils.isBlank(resp.getRspdesc())){
                result.setResultMessage("请求数据集市失败");
            }else {
                result.setResultMessage(resp.getRspdesc());
            }
            batchQueryUserTagsResp.setResult(result);
            batchQueryUserTagsResp.setStatus(CommonConst.DataMartConst.FAIL_STATUS);
        }
        return batchQueryUserTagsResp;
    }
}