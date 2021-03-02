package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DeviceInfo entity.
 */
public class DeviceInfoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String devicecode;

    @NotNull
    @Size(min = 1, max = 50)
    private String devicename;

    private Date makedate;

    @Size(min = 0, max = 1000)
    private String note;

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

    private Long mtdDevicetypeId;

    private Long mtdDevicegroupId;
    
    private String mtdDevicetypename;

    private String mtdDevicegroupname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public Date getMakedate() {
        return makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Long getMtdDevicetypeId() {
        return mtdDevicetypeId;
    }

    public void setMtdDevicetypeId(Long mtdDevicetypeId) {
        this.mtdDevicetypeId = mtdDevicetypeId;
    }

    public Long getMtdDevicegroupId() {
        return mtdDevicegroupId;
    }

    public void setMtdDevicegroupId(Long mtdDevicegroupId) {
        this.mtdDevicegroupId = mtdDevicegroupId;
    }

	public String getMtdDevicetypename() {
		return mtdDevicetypename;
	}

	public void setMtdDevicetypename(String mtdDevicetypename) {
		this.mtdDevicetypename = mtdDevicetypename;
	}

	public String getMtdDevicegroupname() {
		return mtdDevicegroupname;
	}

	public void setMtdDevicegroupname(String mtdDevicegroupname) {
		this.mtdDevicegroupname = mtdDevicegroupname;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceInfoDTO deviceInfoDTO = (DeviceInfoDTO) o;
        if(deviceInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceInfoDTO{" +
            "id=" + getId() +
            ", devicecode='" + getDevicecode() + "'" +
            ", devicename='" + getDevicename() + "'" +
            ", makedate='" + getMakedate() + "'" +
            ", note='" + getNote() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
