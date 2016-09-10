package com.base.platform.dubbo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * base 服务信息表
 *
 * @mbg.generated
 */
@Entity
@Table(name = "base_server_info")
public class BaseServerInfo implements Serializable {
    /**
     * 主键 : base_server_info.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * 服务名称 : base_server_info.server_name
     *
     * @mbg.generated
     */
    private String serverName;

    /**
     * 创建人 : base_server_info.create_user
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     * base_server_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * base_server_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * base_server_info
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
    @Column(name = "server_name")
    public String getServerName() {
        return serverName;
    }

    /**
     *
     * @mbg.generated
     */
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
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