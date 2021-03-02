package com.astcore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MtdWard.
 */
@Entity
@Table(name = "mtd_ward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtdward")
public class MtdWard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "wardcode", length = 10, nullable = false)
    private String wardcode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "wardname", length = 50, nullable = false)
    private String wardname;

    @Column(name = "wrdlatitude", precision=10, scale=2)
    private BigDecimal wrdlatitude;

    @Column(name = "wrdlongitude", precision=10, scale=2)
    private BigDecimal wrdlongitude;

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

    @ManyToOne
    private MtdDistrict mtdDistrict;

    @OneToMany(mappedBy = "mtdWard")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CifMaster> cifMasters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWardcode() {
        return wardcode;
    }

    public MtdWard wardcode(String wardcode) {
        this.wardcode = wardcode;
        return this;
    }

    public void setWardcode(String wardcode) {
        this.wardcode = wardcode;
    }

    public String getWardname() {
        return wardname;
    }

    public MtdWard wardname(String wardname) {
        this.wardname = wardname;
        return this;
    }

    public void setWardname(String wardname) {
        this.wardname = wardname;
    }

    public BigDecimal getWrdlatitude() {
        return wrdlatitude;
    }

    public MtdWard wrdlatitude(BigDecimal wrdlatitude) {
        this.wrdlatitude = wrdlatitude;
        return this;
    }

    public void setWrdlatitude(BigDecimal wrdlatitude) {
        this.wrdlatitude = wrdlatitude;
    }

    public BigDecimal getWrdlongitude() {
        return wrdlongitude;
    }

    public MtdWard wrdlongitude(BigDecimal wrdlongitude) {
        this.wrdlongitude = wrdlongitude;
        return this;
    }

    public void setWrdlongitude(BigDecimal wrdlongitude) {
        this.wrdlongitude = wrdlongitude;
    }

    public Integer getActive() {
        return active;
    }

    public MtdWard active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdWard isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdWard createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdWard createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdWard lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdWard lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public MtdDistrict getMtdDistrict() {
        return mtdDistrict;
    }

    public MtdWard mtdDistrict(MtdDistrict mtdDistrict) {
        this.mtdDistrict = mtdDistrict;
        return this;
    }

    public void setMtdDistrict(MtdDistrict mtdDistrict) {
        this.mtdDistrict = mtdDistrict;
    }

    public Set<CifMaster> getCifMasters() {
        return cifMasters;
    }

    public MtdWard cifMasters(Set<CifMaster> cifMasters) {
        this.cifMasters = cifMasters;
        return this;
    }

    public MtdWard addCifMaster(CifMaster cifMaster) {
        this.cifMasters.add(cifMaster);
        cifMaster.setMtdWard(this);
        return this;
    }

    public MtdWard removeCifMaster(CifMaster cifMaster) {
        this.cifMasters.remove(cifMaster);
        cifMaster.setMtdWard(null);
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
        MtdWard mtdWard = (MtdWard) o;
        if (mtdWard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdWard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdWard{" +
            "id=" + getId() +
            ", wardcode='" + getWardcode() + "'" +
            ", wardname='" + getWardname() + "'" +
            ", wrdlatitude='" + getWrdlatitude() + "'" +
            ", wrdlongitude='" + getWrdlongitude() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
