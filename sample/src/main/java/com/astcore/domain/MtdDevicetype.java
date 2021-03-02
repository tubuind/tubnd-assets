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
 * A MtdDevicetype.
 */
@Entity
@Table(name = "mtd_devicetype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtddevicetype")
public class MtdDevicetype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "devicetypename", length = 250, nullable = false)
    private String devicetypename;

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

    @OneToMany(mappedBy = "mtdDevicetype")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeviceInfo> deviceInfos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicetypename() {
        return devicetypename;
    }

    public MtdDevicetype devicetypename(String devicetypename) {
        this.devicetypename = devicetypename;
        return this;
    }

    public void setDevicetypename(String devicetypename) {
        this.devicetypename = devicetypename;
    }

    public Integer getActive() {
        return active;
    }

    public MtdDevicetype active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdDevicetype isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdDevicetype createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdDevicetype createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdDevicetype lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdDevicetype lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Set<DeviceInfo> getDeviceInfos() {
        return deviceInfos;
    }

    public MtdDevicetype deviceInfos(Set<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
        return this;
    }

    public MtdDevicetype addDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfos.add(deviceInfo);
        deviceInfo.setMtdDevicetype(this);
        return this;
    }

    public MtdDevicetype removeDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfos.remove(deviceInfo);
        deviceInfo.setMtdDevicetype(null);
        return this;
    }

    public void setDeviceInfos(Set<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdDevicetype mtdDevicetype = (MtdDevicetype) o;
        if (mtdDevicetype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdDevicetype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdDevicetype{" +
            "id=" + getId() +
            ", devicetypename='" + getDevicetypename() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
