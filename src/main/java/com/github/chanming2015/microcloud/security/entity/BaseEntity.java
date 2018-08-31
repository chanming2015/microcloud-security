package com.github.chanming2015.microcloud.security.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Description:
 * Create Date:2018年8月29日
 * @author XuMaoSen
 * Version:1.0.0
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable
{
    @Id
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, insertable = false)
    private Date createAt;
    @Column(insertable = false)
    private boolean deleted;
    @Version
    @Column(insertable = false)
    private Integer version;

    /**
     * Author XuMaoSen
     *
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Author XuMaoSen
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Author XuMaoSen
     *
     * @return the createAt
     */
    public Date getCreateAt()
    {
        return createAt;
    }

    /**
     * Author XuMaoSen
     *
     * @param createAt
     *            the createAt to set
     */
    public void setCreateAt(Date createAt)
    {
        this.createAt = createAt;
    }

    /**
     * Author XuMaoSen
     *
     * @return the deleted
     */
    public boolean isDeleted()
    {
        return deleted;
    }

    /**
     * Author XuMaoSen
     *
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * Author XuMaoSen
     *
     * @return the version
     */
    public Integer getVersion()
    {
        return version;
    }

    /**
     * Author XuMaoSen
     *
     * @param version
     *            the version to set
     */
    public void setVersion(Integer version)
    {
        this.version = version;
    }

    /*
     * @author XuMaoSen
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "@" + getId();
    }
}
