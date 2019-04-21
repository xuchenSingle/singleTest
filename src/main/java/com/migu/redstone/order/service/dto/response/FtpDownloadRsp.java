package com.migu.redstone.order.service.dto.response;

import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FTP下载
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FtpDownloadRsp{
    private Result result = Result.success();
}
