package com.glimound.lottery.domain.award.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体货物送货四级地址
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingAddress {

    /** 收货人 */
    private String name;

    /** 一级地址ID */
    private String provinceId;
    /** 一级地址名称 */
    private String provinceName;

    /** 二级地址ID */
    private String cityId;
    /** 二级地址名称 */
    private String cityName;

    /** 三级地址ID */
    private String countyId;
    /** 三级地址名称 */
    private String countyName;

    /** 四级地址ID */
    private String townId;
    /** 四级地址名称 */
    private String townName;

    /** 详细地址 */
    private String address;

    /** 手机号 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 备注 */
    private String remark;
}
