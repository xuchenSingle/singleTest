/**
 * All rights Reserved, Designed By MiGu
 * Copyright:    Copyright(C) 2016-2020
 * Company       MiGu  Co., Ltd.
*/
package com.migu.redstone.order.service.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.migu.redstone.order.service.dto.response.FtpDownloadRsp;

/**
* 类作用:    依据附件id下载sftp文件
* 项目名称:   cmbs-query-order
* 包:       com.migu.redstone.order.service.interfaces
* 类名称:    ICmbsSftpDownService
* 类描述:    依据附件id下载sftp文件
* 创建人:    jianghao
* 创建时间:   2019年3月16日 下午3:34:16
*/
public interface ICmbsSftpDownService {
    /**
     *<sftpDownload>.
     *<依据附件id下载sftp文件>
     * @param  attachId  [attachId]
     * @param  request  [request]
     * @param  response  [response]
     * @exception/throws [Exception] [Exception]
     * @author jianghao
     */
     void sftpDownload(String attacheId,
        HttpServletRequest request, HttpServletResponse response) throws Exception;
}
