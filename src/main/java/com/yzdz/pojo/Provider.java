package com.yzdz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 供应商表实体类
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Provider {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 供应商编码,字符串形式
     */
    private String proCode;

    /**
     * 名称
     */
    private String proName;

    /**
     * 描述
     */
    private String proDesc;

    /**
     * 联系人
     */
    private String proContact;

    /**
     * 联系电话
     */
    private String proPhone;

    /**
     * 地址
     */
    private String proAddress;

    /**
     * 传真
     */
    private String proFax;

    /**
     * 创建者
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 修改人id
     */
    private Integer modifyBy;
}