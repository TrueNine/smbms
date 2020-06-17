package com.yzdz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色表映射对象
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建者id
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改者id
     */
    private Integer modifyBy;

    /**
     * 修改时间
     */
    private Date modifyDate;
}