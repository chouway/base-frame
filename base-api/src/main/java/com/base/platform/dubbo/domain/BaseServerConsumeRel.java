package com.base.platform.dubbo.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * base 服务-消费关系表
 *
 * @mbg.generated
 */
@Entity
@Table(name = "base_server_consume_rel")
public class BaseServerConsumeRel implements Serializable {
    /**
     * base_server_consume_rel.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * base_server_consume_rel.server_id
     *
     * @mbg.generated
     */
    private String serverId;

    /**
     * base_server_consume_rel.consume_id
     *
     * @mbg.generated
     */
    private String consumeId;

    /**
     * base_server_consume_rel
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
    @Column(name = "server_id")
    public String getServerId() {
        return serverId;
    }

    /**
     *
     * @mbg.generated
     */
    public void setServerId(String serverId) {
        this.serverId = serverId == null ? null : serverId.trim();
    }

    /**
     *
     * @mbg.generated
     */
    @Column(name = "consume_id")
    public String getConsumeId() {
        return consumeId;
    }

    /**
     *
     * @mbg.generated
     */
    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }
}