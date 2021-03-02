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
 * A MtdProvince.
 */
@Entity
@Table(name = "mtd_province")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtdprovince")
public class MtdProvince implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "provincecode", length = 10, nullable = false)
    private String provincecode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "provincename", length = 50, nullable = false)
    private String provincename;

    @Column(name = "prolatitude", precision=10, scale=2)
    private BigDecimal prolatitude;

    @Column(name = "prolongitude", precision=10, scale=2)
    private BigDecimal prolongitude;

    
    @Size(min = 1, max = 250)
    @Column(name = "countryname", length = 250, nullable = false)
    private String countryname;

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
    private MtdCountry mtdCountry;

    @OneToMany(mappedBy = "mtdProvince")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MtdDistrict> mtdDistricts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvincecode() {
        return provincecode;
    }

    public MtdProvince provincecode(String provincecode) {
        this.provincecode = provincecode;
        return this;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getProvincename() {
        return provincename;
    }

    public MtdProvince provincename(String provincename) {
        this.provincename = provincename;
        return this;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public BigDecimal getProlatitude() {
        return prolatitude;
    }

    public MtdProvince prolatitude(BigDecimal prolatitude) {
        this.prolatitude = prolatitude;
        return this;
    }

    public void setProlatitude(BigDecimal prolatitude) {
        this.prolatitude = prolatitude;
    }

    public BigDecimal getProlongitude() {
        return prolongitude;
    }

    public MtdProvince prolongitude(BigDecimal prolongitude) {
        this.prolongitude = prolongitude;
        return this;
    }

    public void setProlongitude(BigDecimal prolongitude) {
        this.prolongitude = prolongitude;
    }

    public String getCountryname() {
        return countryname;
    }

    public MtdProvince countryname(String countryname) {
        this.countryname = countryname;
        return this;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Integer getActive() {
        return active;
    }

    public MtdProvince active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdProvince isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdProvince createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdProvince createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdProvince lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdProvince lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public MtdCountry getMtdCountry() {
        return mtdCountry;
    }

    public MtdProvince mtdCountry(MtdCountry mtdCountry) {
        this.mtdCountry = mtdCountry;
        return this;
    }

    public void setMtdCountry(MtdCountry mtdCountry) {
        this.mtdCountry = mtdCountry;
    }

    public Set<MtdDistrict> getMtdDistricts() {
        return mtdDistricts;
    }

    public MtdProvince mtdDistricts(Set<MtdDistrict> mtdDistricts) {
        this.mtdDistricts = mtdDistricts;
        return this;
    }

    public MtdProvince addMtdDistrict(MtdDistrict mtdDistrict) {
        this.mtdDistricts.add(mtdDistrict);
        mtdDistrict.setMtdProvince(this);
        return this;
    }

    public MtdProvince removeMtdDistrict(MtdDistrict mtdDistrict) {
        this.mtdDistricts.remove(mtdDistrict);
        mtdDistrict.setMtdProvince(null);
        return this;
    }

    public void setMtdDistricts(Set<MtdDistrict> mtdDistricts) {
        this.mtdDistricts = mtdDistricts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdProvince mtdProvince = (MtdProvince) o;
        if (mtdProvince.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdProvince.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdProvince{" +
            "id=" + getId() +
            ", provincecode='" + getProvincecode() + "'" +
            ", provincename='" + getProvincename() + "'" +
            ", prolatitude='" + getProlatitude() + "'" +
            ", prolongitude='" + getProlongitude() + "'" +
            ", countryname='" + getCountryname() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
