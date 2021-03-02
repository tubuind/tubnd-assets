package com.astcore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MtdCustomergroup.
 */
@Entity
@Table(name = "mtd_customergroup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtdcustomergroup")
public class MtdCustomergroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "custgroupname", length = 250, nullable = false)
    private String custgroupname;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "active", nullable = false)
    private Integer active;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "isdel", nullable = false)
    private Integer isdel;

    @Size(min = 1, max = 50)
    @Column(name = "createby", length = 50)
    private String createby;

    @Column(name = "createdate")
    private Date createdate;

    @Size(min = 1, max = 50)
    @Column(name = "lastmodifyby", length = 50)
    private String lastmodifyby;

    @Column(name = "lastmodifydate")
    private Date lastmodifydate;

    @OneToMany(mappedBy = "mtdCustomergroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CifMaster> cifMasters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustgroupname() {
        return custgroupname;
    }

    public MtdCustomergroup custgroupname(String custgroupname) {
        this.custgroupname = custgroupname;
        return this;
    }

    public void setCustgroupname(String custgroupname) {
        this.custgroupname = custgroupname;
    }

    public Integer getActive() {
        return active;
    }

    public MtdCustomergroup active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdCustomergroup isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdCustomergroup createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdCustomergroup createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdCustomergroup lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdCustomergroup lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Set<CifMaster> getCifMasters() {
        return cifMasters;
    }

    public MtdCustomergroup cifMasters(Set<CifMaster> cifMasters) {
        this.cifMasters = cifMasters;
        return this;
    }

    public MtdCustomergroup addCifMaster(CifMaster cifMaster) {
        this.cifMasters.add(cifMaster);
        cifMaster.setMtdCustomergroup(this);
        return this;
    }

    public MtdCustomergroup removeCifMaster(CifMaster cifMaster) {
        this.cifMasters.remove(cifMaster);
        cifMaster.setMtdCustomergroup(null);
        return this;
    }

    public void setCifMasters(Set<CifMaster> cifMasters) {
        this.cifMasters = cifMasters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdCustomergroup mtdCustomergroup = (MtdCustomergroup) o;
        if (mtdCustomergroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdCustomergroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdCustomergroup{" +
            "id=" + getId() +
            ", custgroupname='" + getCustgroupname() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
