package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MtdProvince entity.
 */
public class MtdProvinceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String provincecode;

    @NotNull
    @Size(min = 1, max = 50)
    private String provincename;

    private BigDecimal prolatitude;

    private BigDecimal prolongitude;

    private String countryname;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer active;

    private Integer isdel;

    @Size(min = 1, max = 50)
    private String createby;

    private Date createdate;

    @Size(min = 1, max = 50)
    private String lastmodifyby;

    private Date lastmodifydate;

    private Long mtdCountryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public BigDecimal getProlatitude() {
        return prolatitude;
    }

    public void setProlatitude(BigDecimal prolatitude) {
        this.prolatitude = prolatitude;
    }

    public BigDecimal getProlongitude() {
        return prolongitude;
    }

    public void setProlongitude(BigDecimal prolongitude) {
        this.prolongitude = prolongitude;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Long getMtdCountryId() {
        return mtdCountryId;
    }

    public void setMtdCountryId(Long mtdCountryId) {
        this.mtdCountryId = mtdCountryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MtdProvinceDTO mtdProvinceDTO = (MtdProvinceDTO) o;
        if(mtdProvinceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdProvinceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdProvinceDTO{" +
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
