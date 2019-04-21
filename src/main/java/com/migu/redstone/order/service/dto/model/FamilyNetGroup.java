package com.migu.redstone.order.service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FamilyNetGroup
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyNetGroup {
    /**
     * 手机号码
     */
    private String serviceNum;

    /**
     * 亲情号码
     */
    private String familyNum;
}
