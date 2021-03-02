package com.astcore.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * A DeviceTransaction.
 */
@Entity
@Table(name = "device_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "devicetransaction")
public class DeviceTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "transdate")
    private Date transdate;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "devicecode", length = 50, nullable = false)
    private String devicecode;

    @Column(name = "currentvalue", precision=10, scale=2)
    private BigDecimal currentvalue;

    @Column(name = "valuedate")
    private Date valuedate;

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
    private DeviceInfo deviceInfo;
    
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "valuetype", length = 10, nullable = false)
    private String valuetype;
    
    public DeviceTransaction() {
	}

	//    public DeviceTransaction(BigDecimal currentvalue, Date valuedate) {
	//		super();
	//		this.currentvalue = currentvalue;
	//		this.valuedate = valuedate;
	//	}
    
    public DeviceTransaction(Double currentvalue, Date valuedate) {
		super();
		this.currentvalue = BigDecimal.valueOf(currentvalue);
		this.valuedate = valuedate;
	}
    
    public DeviceTransaction(Double currentvalue, Date valuedate, String valuetype) {
		super();
		this.currentvalue = BigDecimal.valueOf(currentvalue);
		this.valuedate = valuedate;
		this.valuetype = valuetype;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransdate() {
        return transdate;
    }

    public DeviceTransaction transdate(Date transdate) {
        this.transdate = transdate;
        return this;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public DeviceTransaction devicecode(String devicecode) {
        this.devicecode = devicecode;
        return this;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public BigDecimal getCurrentvalue() {
        return currentvalue;
    }

    public DeviceTransaction currentvalue(BigDecimal currentvalue) {
        this.currentvalue = currentvalue;
        return this;
    }

    public void setCurrentvalue(BigDecimal currentvalue) {
        this.currentvalue = currentvalue;
    }

    public Date getValuedate() {
        return valuedate;
    }

    public DeviceTransaction valuedate(Date valuedate) {
        this.valuedate = valuedate;
        return this;
    }

    public void setValuedate(Date valuedate) {
        this.valuedate = valuedate;
    }

    public Integer getActive() {
        return active;
    }

    public DeviceTransaction active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public DeviceTransaction isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public DeviceTransaction createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public DeviceTransaction createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public DeviceTransaction lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public DeviceTransaction lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public DeviceTransaction deviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getValuetype() {
		return valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceTransaction deviceTransaction = (DeviceTransaction) o;
        if (deviceTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceTransaction{" +
            "id=" + getId() +
            ", transdate='" + getTransdate() + "'" +
            ", devicecode='" + getDevicecode() + "'" +
            ", currentvalue='" + getCurrentvalue() + "'" +
            ", valuedate='" + getValuedate() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
