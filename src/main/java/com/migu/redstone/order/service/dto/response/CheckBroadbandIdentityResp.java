package com.migu.redstone.order.service.dto.response;

import java.io.Serializable;

import com.migu.redstone.entity.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBroadbandIdentityResp implements Serializable {

    private static final long serialVersionUID = 1l;

    private Result result = Result.success();

}
