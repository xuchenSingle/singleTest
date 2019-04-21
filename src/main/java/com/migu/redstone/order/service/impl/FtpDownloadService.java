package com.migu.redstone.order.service.impl;

import com.migu.redstone.cfg.common.service.interfaces.ICfgCommonService;
import com.migu.redstone.constants.IResultCode;
import com.migu.redstone.entity.Result;
import com.migu.redstone.cfg.common.mapper.po.CmbsAttach;
import com.migu.redstone.cfg.common.mapper.po.CmbsFtpConf;
import com.migu.redstone.cfg.common.mapper.po.CmbsFtpInfo;
import com.migu.redstone.cfg.common.mapper.po.CmbsFtpPath;
import com.migu.redstone.order.service.dto.response.FtpDownloadRsp;
import com.migu.redstone.order.service.interfaces.IFtpDownloadService;
import com.migu.redstone.utils.CommonUtil;
import com.migu.redstone.utils.FtpUtil;
import com.migu.redstone.utils.JsonUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FtpDownloadService implements IFtpDownloadService {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(
        FtpDownloadService.class);

    @Autowired
    private ICfgCommonService cfgCommonService;

    @Override
    public FtpDownloadRsp ftpDownload(String attachId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CmbsAttach cmbsAttach = cfgCommonService.getCmbsAttachCfgByAttachId(attachId);
        LOG.error("FtpDownloadService.ftpDownload, cmbsAttach=" + JsonUtil.objectToString(cmbsAttach));
        if (cmbsAttach != null) {
            String ftpPathCode = cmbsAttach.getFtpPathCode();
            CmbsFtpInfo cmbsFtpInfo = cfgCommonService.getFtpInfo(ftpPathCode);
            LOG.error("FtpDownloadService.ftpDownload, cmbsFtpInfo=" + JsonUtil.objectToString(cmbsFtpInfo));
            if(CommonUtil.isNotNull(cmbsFtpInfo)){
                FTPClient ftpClient = FtpUtil.connect(cmbsFtpInfo.getHostIp(),
                    Integer.parseInt(cmbsFtpInfo.getPort()), cmbsFtpInfo.getUserName(), cmbsFtpInfo.getPassword());
                FtpUtil.getInputStream(ftpClient, cmbsFtpInfo.getRemotePath(), cmbsAttach.getFileName(),request, response);
                FtpUtil.disConnect(ftpClient);
                return new FtpDownloadRsp(new Result(IResultCode.SUCCESS_CODE, IResultCode.SUCCESS_MESSAGE));
            }
            else {
                return new FtpDownloadRsp(new Result(IResultCode.CMBS_PARAM_VALIDATE_FAIL, "下载失败, 缺少ftp配置"));
            }
        }else {
            return new FtpDownloadRsp(new Result(IResultCode.CMBS_PARAM_VALIDATE_FAIL, "下载失败, 没有匹配的附件"));
        }
    }
}
