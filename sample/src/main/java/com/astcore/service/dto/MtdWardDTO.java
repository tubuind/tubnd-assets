package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MtdWard entity.
 */
public class MtdWardDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String wardcode;

    @NotNull
    @Size(min = 1, max = 50)
    private String wardname;

    private BigDecimal wrdlatitude;

    private BigDecimal wrdlongitude;

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

    private Long mtdDistrictId;
    
    private String districtname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWardcode() {
        return wardcode;
    }

    public void setWardcode(String wardcode) {
        this.wardcode = wardcode;
    }

    public String getWardname() {
        return wardname;
    }

    public void setWardname(String wardname) {
        this.wardname = wardname;
    }

    public BigDecimal getWrdlatitude() {
        return wrdlatitude;
    }

    public void setWrdlatitude(BigDecimal wrdlatitude) {
        this.wrdlatitude = wrdlatitude;
    }

    public BigDecimal getWrdlongitude() {
        return wrdlongitude;
    }

    public void setWrdlongitude(BigDecimal wrdlongitude) {
        this.wrdlongitude = wrdlongitude;
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

    public Long getMtdDistrictId() {
        return mtdDistrictId;
    }

    public void setMtdDistrictId(Long mtdDistrictId) {
        this.mtdDistrictId = mtdDistrictId;
    }

    public String getDistrictname() {
		return districtname;
	}

	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MtdWardDTO mtdWardDTO = (MtdWardDTO) o;
        if(mtdWardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdWardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdWardDTO{" +
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
