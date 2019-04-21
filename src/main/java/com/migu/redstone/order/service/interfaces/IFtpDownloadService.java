package com.migu.redstone.order.service.interfaces;


import com.migu.redstone.order.service.dto.response.FtpDownloadRsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * xiazai
 */
public interface IFtpDownloadService {
    /**
     * ftp下载
     * @param attachId
     * @return
     */
    FtpDownloadRsp ftpDownload(String attachId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
