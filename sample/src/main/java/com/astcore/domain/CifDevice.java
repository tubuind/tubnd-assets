package com.astcore.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A CifDevice.
 */
@Entity
@Table(name = "cif_device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cifdevice")
public class CifDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "actiontype", nullable = false)
    private Integer actiontype;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

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
    
    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "active", nullable = false)
    private Integer active;
    
    @Size(min = 1, max = 50)
    @Column(name = "devicecode", length = 50)
    private String devicecode;

    @ManyToOne
    private CifMaster cifMaster;

    @ManyToOne
    private DeviceInfo deviceInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActiontype() {
        return actiontype;
    }

    public CifDevice actiontype(Integer actiontype) {
        this.actiontype = actiontype;
        return this;
    }

    public void setActiontype(Integer actiontype) {
        this.actiontype = actiontype;
    }

    public Date getStartdate() {
        return startdate;
    }

    public CifDevice startdate(Date startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public CifDevice enddate(Date enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public CifDevice isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public CifDevice createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public CifDevice createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public CifDevice lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public CifDevice lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
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

	public CifMaster getCifMaster() {
        return cifMaster;
    }

    public CifDevice cifMaster(CifMaster cifMaster) {
        this.cifMaster = cifMaster;
        return this;
    }

    public void setCifMaster(CifMaster cifMaster) {
        this.cifMaster = cifMaster;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public CifDevice deviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CifDevice cifDevice = (CifDevice) o;
        if (cifDevice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifDevice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifDevice{" +
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
}
