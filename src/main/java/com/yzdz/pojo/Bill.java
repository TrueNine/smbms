package com.yzdz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单记录表映射对象
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    /**
     * 主键 id
     */
    private Integer id;

    /**
     * 编码
     */
    private String billCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品单位
     */
    private String productUnit;

    /**
     * 商品数量
     */
    private Double productCount;

    /**
     * 商品总额
     */
    private Double productPrice;

    /**
     * 是否支付
     * 1: 未支付
     * 2: 已支付
     */
    private Integer isPayment;

    /**
     * 创建者id
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改者
     */
    private Integer modifyBy;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 供应商ID
     */
    private Integer providerId;
}
