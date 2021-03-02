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
 * A MtdDistrict.
 */
@Entity
@Table(name = "mtd_district")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtddistrict")
public class MtdDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "districtcode", length = 10, nullable = false)
    private String districtcode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "districtname", length = 50, nullable = false)
    private String districtname;

    @Column(name = "dislatitude", precision=10, scale=2)
    private BigDecimal dislatitude;

    @Column(name = "dislongitude", precision=10, scale=2)
    private BigDecimal dislongitude;

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
    private MtdProvince mtdProvince;

    @OneToMany(mappedBy = "mtdDistrict")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MtdWard> mtdWards = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public MtdDistrict districtcode(String districtcode) {
        this.districtcode = districtcode;
        return this;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getDistrictname() {
        return districtname;
    }

    public MtdDistrict districtname(String districtname) {
        this.districtname = districtname;
        return this;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public BigDecimal getDislatitude() {
        return dislatitude;
    }

    public MtdDistrict dislatitude(BigDecimal dislatitude) {
        this.dislatitude = dislatitude;
        return this;
    }

    public void setDislatitude(BigDecimal dislatitude) {
        this.dislatitude = dislatitude;
    }

    public BigDecimal getDislongitude() {
        return dislongitude;
    }

    public MtdDistrict dislongitude(BigDecimal dislongitude) {
        this.dislongitude = dislongitude;
        return this;
    }

    public void setDislongitude(BigDecimal dislongitude) {
        this.dislongitude = dislongitude;
    }

    public Integer getActive() {
        return active;
    }

    public MtdDistrict active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdDistrict isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdDistrict createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdDistrict createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdDistrict lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdDistrict lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public MtdProvince getMtdProvince() {
        return mtdProvince;
    }

    public MtdDistrict mtdProvince(MtdProvince mtdProvince) {
        this.mtdProvince = mtdProvince;
        return this;
    }

    public void setMtdProvince(MtdProvince mtdProvince) {
        this.mtdProvince = mtdProvince;
    }

    public Set<MtdWard> getMtdWards() {
        return mtdWards;
    }

    public MtdDistrict mtdWards(Set<MtdWard> mtdWards) {
        this.mtdWards = mtdWards;
        return this;
    }

    public MtdDistrict addMtdWard(MtdWard mtdWard) {
        this.mtdWards.add(mtdWard);
        mtdWard.setMtdDistrict(this);
        return this;
    }

    public MtdDistrict removeMtdWard(MtdWard mtdWard) {
        this.mtdWards.remove(mtdWard);
        mtdWard.setMtdDistrict(null);
        return this;
    }

    public void setMtdWards(Set<MtdWard> mtdWards) {
        this.mtdWards = mtdWards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdDistrict mtdDistrict = (MtdDistrict) o;
        if (mtdDistrict.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdDistrict.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdDistrict{" +
            "id=" + getId() +
            ", districtcode='" + getDistrictcode() + "'" +
            ", districtname='" + getDistrictname() + "'" +
            ", dislatitude='" + getDislatitude() + "'" +
            ", dislongitude='" + getDislongitude() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
