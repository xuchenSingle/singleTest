/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.impl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migu.redstone.cfg.common.mapper.po.CmbsAttach;
import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.constants.CommonConst;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.order.service.interfaces.ICmbsSftpDownService;
import com.migu.redstone.util.DownLoadUtil;
import com.migu.redstone.utils.JsonUtil;

/**
* 类作用:    依据附件id下载sftp文件
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.impl
* 类名称:    CmbsSftpDownService
* 类描述:    依据附件id下载sftp文件
* 创建人:    jianghao
* 创建时间:   2019年3月16日 下午3:36:06
*/
@Service
public class CmbsSftpDownService implements ICmbsSftpDownService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmbsSftpDownService.class);

    @Autowired
    private ICfgCommonService cfgCommonService;
    @Autowired
    private DownLoadUtil downLoadUtil;

    /**
    *<sftpDownload>.
    *<依据附件id下载sftp文件>
    * @param  attachId  [attachId]
    * @param  request  [request]
    * @param  response  [response]
    * @exception/throws [Exception] [Exception]
    * @author jianghao
    */
    @Override
    public void sftpDownload(String attachId,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOG.error("CmbsSftpDownService.sftpDownload, attachId=" + attachId);
        //获取附件
        CmbsAttach cmbsAttach = cfgCommonService.getCmbsAttachCfgByAttachId(attachId);
        LOG.error("SftpDownloadService.sftpDownload, cmbsAttach=" + JsonUtil.objectToString(cmbsAttach));

        if (cmbsAttach != null) {
        	try {
        		//下载到服务器
        		downLoadUtil.downloadFtpAttach(cmbsAttach);
                //读取下载文件返回输入流
        		downLoadUtil.setDownloadAttachResponse(cmbsAttach, response);
        	}
        	catch(Exception e) {
        		LOG.error("SftpDownloadService.sftpDownload error:统计报表下载失败,", e);
        		response.setContentType("text/html;charset=UTF-8");
            	ServletOutputStream ouPut = response.getOutputStream();
            	ouPut.write(JsonUtil.objectToString(new Result(IResultCode.CMBS_SYS_ERROR, "统计报表下载失败"))
    					.getBytes(CommonConst.CHARSET_UTF8));
    			ouPut.close();
    			return;
        	}
        }
        else {
        	response.setContentType("text/html;charset=UTF-8");
        	ServletOutputStream ouPut = response.getOutputStream();
        	ouPut.write(JsonUtil.objectToString(new Result(IResultCode.CMBS_SYS_ERROR, "统计报表下载缺少附件配置"))
					.getBytes(CommonConst.CHARSET_UTF8));
			ouPut.close();
			return;
        }
    }
}
