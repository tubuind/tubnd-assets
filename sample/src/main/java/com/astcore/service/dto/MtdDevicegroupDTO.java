package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MtdDevicegroup entity.
 */
public class MtdDevicegroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    private String devicegroupname;

    //@NotNull
    //@Size(min = 1, max = 10)
    private String unitcode;

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

    private Long mtdUnitId;
    
    @NotNull
    @Size(min = 1, max = 10)
    private String devicegroupcode;
    
    private String unitname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicegroupname() {
        return devicegroupname;
    }

    public void setDevicegroupname(String devicegroupname) {
        this.devicegroupname = devicegroupname;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
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

    public Long getMtdUnitId() {
        return mtdUnitId;
    }

    public void setMtdUnitId(Long mtdUnitId) {
        this.mtdUnitId = mtdUnitId;
    }

    public String getDevicegroupcode() {
		return devicegroupcode;
	}

	public void setDevicegroupcode(String devicegroupcode) {
		this.devicegroupcode = devicegroupcode;
	}
	
	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MtdDevicegroupDTO mtdDevicegroupDTO = (MtdDevicegroupDTO) o;
        if(mtdDevicegroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdDevicegroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdDevicegroupDTO{" +
            "id=" + getId() +
            ", devicegroupname='" + getDevicegroupname() + "'" +
            ", unitcode='" + getUnitcode() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
