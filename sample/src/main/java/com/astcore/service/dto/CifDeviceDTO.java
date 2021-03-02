package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CifDevice entity.
 */
public class CifDeviceDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer actiontype;

    private Date startdate;

    private Date enddate;

    private Integer isdel;

    @Size(min = 1, max = 50)
    private String createby;

    private Date createdate;

    @Size(min = 1, max = 50)
    private String lastmodifyby;

    private Date lastmodifydate;

    private Long cifMasterId;

    private Long deviceInfoId;
    
    private Integer active;
    
    private String devicecode;
    
    private String cifMasterName;

    private String deviceInfoName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActiontype() {
        return actiontype;
    }

    public void setActiontype(Integer actiontype) {
        this.actiontype = actiontype;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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

    public Long getCifMasterId() {
        return cifMasterId;
    }

    public void setCifMasterId(Long cifMasterId) {
        this.cifMasterId = cifMasterId;
    }

    public Long getDeviceInfoId() {
        return deviceInfoId;
    }

    public void setDeviceInfoId(Long deviceInfoId) {
        this.deviceInfoId = deviceInfoId;
    }

    public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CifDeviceDTO cifDeviceDTO = (CifDeviceDTO) o;
        if(cifDeviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifDeviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifDeviceDTO{" +
            "id=" + getId() +
            ", actiontype='" + getActiontype() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }

	public String getCifMasterName() {
		return cifMasterName;
	}

	public void setCifMasterName(String cifMasterName) {
		this.cifMasterName = cifMasterName;
	}

	public String getDeviceInfoName() {
		return deviceInfoName;
	}

	public void setDeviceInfoName(String deviceInfoName) {
		this.deviceInfoName = deviceInfoName;
	}
}
