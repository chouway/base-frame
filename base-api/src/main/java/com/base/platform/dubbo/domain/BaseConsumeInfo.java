package com.base.platform.dubbo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * base 消费信息表
 *
 * @mbg.generated
 */
@Entity
@Table(name = "base_consume_info")
public class BaseConsumeInfo implements Serializable {
    /**
     * base_consume_info.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * base_consume_info.consume_name
     *
     * @mbg.generated
     */
    private String consumeName;

    /**
     * base_consume_info.create_user
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     * base_consume_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * base_consume_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * base_consume_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @mbg.generated
     */
    @Column(name = "id")
    public String getId() {
        return id;
    }

    /**
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     *
     * @mbg.generated
     */
    @Column(name = "consume_name")
    public String getConsumeName() {
        return consumeName;
    }

    /**
     *
     * @mbg.generated
     */
    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName == null ? null : consumeName.trim();
    }

    /**
     *
     * @mbg.generated
     */
    @Column(name = "create_user")
    public String getCreateUser() {
        return createUser;
    }

    /**
     *
     * @mbg.generated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     *
     * @mbg.generated
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     * @mbg.generated
     */
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}