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
 * A MtdCountry.
 */
@Entity
@Table(name = "mtd_country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtdcountry")
public class MtdCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
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

    @OneToMany(mappedBy = "mtdCountry")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MtdProvince> mtdProvinces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryname() {
        return countryname;
    }

    public MtdCountry countryname(String countryname) {
        this.countryname = countryname;
        return this;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Integer getActive() {
        return active;
    }

    public MtdCountry active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdCountry isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdCountry createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdCountry createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdCountry lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdCountry lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Set<MtdProvince> getMtdProvinces() {
        return mtdProvinces;
    }

    public MtdCountry mtdProvinces(Set<MtdProvince> mtdProvinces) {
        this.mtdProvinces = mtdProvinces;
        return this;
    }

    public MtdCountry addMtdProvince(MtdProvince mtdProvince) {
        this.mtdProvinces.add(mtdProvince);
        mtdProvince.setMtdCountry(this);
        return this;
    }

    public MtdCountry removeMtdProvince(MtdProvince mtdProvince) {
        this.mtdProvinces.remove(mtdProvince);
        mtdProvince.setMtdCountry(null);
        return this;
    }

    public void setMtdProvinces(Set<MtdProvince> mtdProvinces) {
        this.mtdProvinces = mtdProvinces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdCountry mtdCountry = (MtdCountry) o;
        if (mtdCountry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdCountry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdCountry{" +
            "id=" + getId() +
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
