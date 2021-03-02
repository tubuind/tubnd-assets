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
 * A DeviceInfo.
 */
@Entity
@Table(name = "device_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "deviceinfo")
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "devicecode", length = 10, nullable = false)
    private String devicecode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "devicename", length = 50, nullable = false)
    private String devicename;

    @Column(name = "makedate")
    private Date makedate;

    @Size(min = 0, max = 1000)
    @Column(name = "note", length = 1000)
    private String note;

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

    @ManyToOne
    private MtdDevicetype mtdDevicetype;

    @ManyToOne
    private MtdDevicegroup mtdDevicegroup;

    @OneToMany(mappedBy = "deviceInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeviceTransaction> deviceTransactions = new HashSet<>();

    @OneToMany(mappedBy = "deviceInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CifDevice> cifDevices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public DeviceInfo devicecode(String devicecode) {
        this.devicecode = devicecode;
        return this;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public String getDevicename() {
        return devicename;
    }

    public DeviceInfo devicename(String devicename) {
        this.devicename = devicename;
        return this;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public Date getMakedate() {
        return makedate;
    }

    public DeviceInfo makedate(Date makedate) {
        this.makedate = makedate;
        return this;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getNote() {
        return note;
    }

    public DeviceInfo note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getActive() {
        return active;
    }

    public DeviceInfo active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public DeviceInfo isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public DeviceInfo createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public DeviceInfo createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public DeviceInfo lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public DeviceInfo lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public MtdDevicetype getMtdDevicetype() {
        return mtdDevicetype;
    }

    public DeviceInfo mtdDevicetype(MtdDevicetype mtdDevicetype) {
        this.mtdDevicetype = mtdDevicetype;
        return this;
    }

    public void setMtdDevicetype(MtdDevicetype mtdDevicetype) {
        this.mtdDevicetype = mtdDevicetype;
    }

    public MtdDevicegroup getMtdDevicegroup() {
        return mtdDevicegroup;
    }

    public DeviceInfo mtdDevicegroup(MtdDevicegroup mtdDevicegroup) {
        this.mtdDevicegroup = mtdDevicegroup;
        return this;
    }

    public void setMtdDevicegroup(MtdDevicegroup mtdDevicegroup) {
        this.mtdDevicegroup = mtdDevicegroup;
    }

    public Set<DeviceTransaction> getDeviceTransactions() {
        return deviceTransactions;
    }

    public DeviceInfo deviceTransactions(Set<DeviceTransaction> deviceTransactions) {
        this.deviceTransactions = deviceTransactions;
        return this;
    }

    public DeviceInfo addDeviceTransaction(DeviceTransaction deviceTransaction) {
        this.deviceTransactions.add(deviceTransaction);
        deviceTransaction.setDeviceInfo(this);
        return this;
    }

    public DeviceInfo removeDeviceTransaction(DeviceTransaction deviceTransaction) {
        this.deviceTransactions.remove(deviceTransaction);
        deviceTransaction.setDeviceInfo(null);
        return this;
    }

    public void setDeviceTransactions(Set<DeviceTransaction> deviceTransactions) {
        this.deviceTransactions = deviceTransactions;
    }

    public Set<CifDevice> getCifDevices() {
        return cifDevices;
    }

    public DeviceInfo cifDevices(Set<CifDevice> cifDevices) {
        this.cifDevices = cifDevices;
        return this;
    }

    public DeviceInfo addCifDevice(CifDevice cifDevice) {
        this.cifDevices.add(cifDevice);
        cifDevice.setDeviceInfo(this);
        return this;
    }

    public DeviceInfo removeCifDevice(CifDevice cifDevice) {
        this.cifDevices.remove(cifDevice);
        cifDevice.setDeviceInfo(null);
        return this;
    }

    public void setCifDevices(Set<CifDevice> cifDevices) {
        this.cifDevices = cifDevices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceInfo deviceInfo = (DeviceInfo) o;
        if (deviceInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
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
