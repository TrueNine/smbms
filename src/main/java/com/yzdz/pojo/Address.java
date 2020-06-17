package com.yzdz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 地址表映射对象
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 地址描述
     */
    private String addressDesc;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 创建者
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
     * 用户id
     */
    private Integer userId;
}
