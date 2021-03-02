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
 * A MtdDevicegroup.
 */
@Entity
@Table(name = "mtd_devicegroup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtddevicegroup")
public class MtdDevicegroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "devicegroupname", length = 250, nullable = false)
    private String devicegroupname;

    //@NotNull
    //@Size(min = 1, max = 10)
    //@Column(name = "unitcode", length = 10, nullable = false)
    private String unitcode;

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

    @OneToMany(mappedBy = "mtdDevicegroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeviceInfo> deviceInfos = new HashSet<>();

    @ManyToOne
    private MtdUnit mtdUnit;
    
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "devicegroupcode", length = 10, nullable = false)
    private String devicegroupcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicegroupname() {
        return devicegroupname;
    }

    public MtdDevicegroup devicegroupname(String devicegroupname) {
        this.devicegroupname = devicegroupname;
        return this;
    }

    public void setDevicegroupname(String devicegroupname) {
        this.devicegroupname = devicegroupname;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public MtdDevicegroup unitcode(String unitcode) {
        this.unitcode = unitcode;
        return this;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public Integer getActive() {
        return active;
    }

    public MtdDevicegroup active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdDevicegroup isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdDevicegroup createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdDevicegroup createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdDevicegroup lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdDevicegroup lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Set<DeviceInfo> getDeviceInfos() {
        return deviceInfos;
    }

    public MtdDevicegroup deviceInfos(Set<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
        return this;
    }

    public MtdDevicegroup addDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfos.add(deviceInfo);
        deviceInfo.setMtdDevicegroup(this);
        return this;
    }

    public MtdDevicegroup removeDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfos.remove(deviceInfo);
        deviceInfo.setMtdDevicegroup(null);
        return this;
    }

    public void setDeviceInfos(Set<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    public MtdUnit getMtdUnit() {
        return mtdUnit;
    }

    public MtdDevicegroup mtdUnit(MtdUnit mtdUnit) {
        this.mtdUnit = mtdUnit;
        return this;
    }

    public void setMtdUnit(MtdUnit mtdUnit) {
        this.mtdUnit = mtdUnit;
    }

    public String getDevicegroupcode() {
		return devicegroupcode;
	}

	public void setDevicegroupcode(String devicegroupcode) {
		this.devicegroupcode = devicegroupcode;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdDevicegroup mtdDevicegroup = (MtdDevicegroup) o;
        if (mtdDevicegroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdDevicegroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdDevicegroup{" +
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
