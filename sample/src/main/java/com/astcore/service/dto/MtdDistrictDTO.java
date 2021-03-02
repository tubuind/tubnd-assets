package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MtdDistrict entity.
 */
public class MtdDistrictDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String districtcode;

    @NotNull
    @Size(min = 1, max = 50)
    private String districtname;

    private BigDecimal dislatitude;

    private BigDecimal dislongitude;

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

    private Long mtdProvinceId;

    private String mtdProvinceProvincename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public BigDecimal getDislatitude() {
        return dislatitude;
    }

    public void setDislatitude(BigDecimal dislatitude) {
        this.dislatitude = dislatitude;
    }

    public BigDecimal getDislongitude() {
        return dislongitude;
    }

    public void setDislongitude(BigDecimal dislongitude) {
        this.dislongitude = dislongitude;
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

    public Long getMtdProvinceId() {
        return mtdProvinceId;
    }

    public void setMtdProvinceId(Long mtdProvinceId) {
        this.mtdProvinceId = mtdProvinceId;
    }

	public String getMtdProvinceProvincename() {
		return mtdProvinceProvincename;
	}

	public void setMtdProvinceProvincename(String mtdProvinceProvincename) {
		this.mtdProvinceProvincename = mtdProvinceProvincename;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MtdDistrictDTO mtdDistrictDTO = (MtdDistrictDTO) o;
        if(mtdDistrictDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdDistrictDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdDistrictDTO{" +
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
