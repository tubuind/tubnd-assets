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
 * A MtdUnit.
 */
@Entity
@Table(name = "mtd_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtdunit")
public class MtdUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "unitcode", length = 10, nullable = false)
    private String unitcode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "unitname", length = 50, nullable = false)
    private String unitname;

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

    @OneToMany(mappedBy = "mtdUnit")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MtdDevicegroup> mtdDevicegroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public MtdUnit unitcode(String unitcode) {
        this.unitcode = unitcode;
        return this;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getUnitname() {
        return unitname;
    }

    public MtdUnit unitname(String unitname) {
        this.unitname = unitname;
        return this;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public Integer getActive() {
        return active;
    }

    public MtdUnit active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdUnit isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdUnit createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdUnit createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdUnit lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdUnit lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Set<MtdDevicegroup> getMtdDevicegroups() {
        return mtdDevicegroups;
    }

    public MtdUnit mtdDevicegroups(Set<MtdDevicegroup> mtdDevicegroups) {
        this.mtdDevicegroups = mtdDevicegroups;
        return this;
    }

    public MtdUnit addMtdDevicegroup(MtdDevicegroup mtdDevicegroup) {
        this.mtdDevicegroups.add(mtdDevicegroup);
        mtdDevicegroup.setMtdUnit(this);
        return this;
    }

    public MtdUnit removeMtdDevicegroup(MtdDevicegroup mtdDevicegroup) {
        this.mtdDevicegroups.remove(mtdDevicegroup);
        mtdDevicegroup.setMtdUnit(null);
        return this;
    }

    public void setMtdDevicegroups(Set<MtdDevicegroup> mtdDevicegroups) {
        this.mtdDevicegroups = mtdDevicegroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdUnit mtdUnit = (MtdUnit) o;
        if (mtdUnit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdUnit{" +
            "id=" + getId() +
            ", unitcode='" + getUnitcode() + "'" +
            ", unitname='" + getUnitname() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
