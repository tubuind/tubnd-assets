package com.astcore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CifMaster.
 */
@Entity
@Table(name = "cif_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cifmaster")
public class CifMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "customercode", length = 30, nullable = false)
    private String customercode;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "customername", length = 500, nullable = false)
    private String customername;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "birthday")
    private Date birthday;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "identifycode", length = 50, nullable = false)
    private String identifycode;

    @Column(name = "identifydate")
    private Date identifydate;

    @Size(min = 0, max = 500)
    @Column(name = "address", length = 500)
    private String address;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mobilenum", length = 50, nullable = false)
    private String mobilenum;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "phonenum", length = 50, nullable = false)
    private String phonenum;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "customertype", length = 10, nullable = false)
    private String customertype;

    @Column(name = "custparents")
    private Integer custparents;

    @Size(min = 0, max = 1000)
    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "custlatitude", precision=10, scale=2)
    private BigDecimal custlatitude;

    @Column(name = "custlongitude", precision=10, scale=2)
    private BigDecimal custlongitude;

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
    private MtdWard mtdWard;

    @ManyToOne
    private MtdOrganization mtdOrganization;

    @ManyToOne
    private MtdEcosectors mtdEcosectors;

    @ManyToOne
    private MtdCustomergroup mtdCustomergroup;

    @OneToMany(mappedBy = "cifMaster")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CifDevice> cifDevices = new HashSet<>();
    
    @Column(name = "login")
    private String login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomercode() {
        return customercode;
    }

    public CifMaster customercode(String customercode) {
        this.customercode = customercode;
        return this;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getCustomername() {
        return customername;
    }

    public CifMaster customername(String customername) {
        this.customername = customername;
        return this;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Integer getSex() {
        return sex;
    }

    public CifMaster sex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public CifMaster birthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentifycode() {
        return identifycode;
    }

    public CifMaster identifycode(String identifycode) {
        this.identifycode = identifycode;
        return this;
    }

    public void setIdentifycode(String identifycode) {
        this.identifycode = identifycode;
    }

    public Date getIdentifydate() {
        return identifydate;
    }

    public CifMaster identifydate(Date identifydate) {
        this.identifydate = identifydate;
        return this;
    }

    public void setIdentifydate(Date identifydate) {
        this.identifydate = identifydate;
    }

    public String getAddress() {
        return address;
    }

    public CifMaster address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public CifMaster mobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
        return this;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public CifMaster phonenum(String phonenum) {
        this.phonenum = phonenum;
        return this;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getCustomertype() {
        return customertype;
    }

    public CifMaster customertype(String customertype) {
        this.customertype = customertype;
        return this;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public Integer getCustparents() {
        return custparents;
    }

    public CifMaster custparents(Integer custparents) {
        this.custparents = custparents;
        return this;
    }

    public void setCustparents(Integer custparents) {
        this.custparents = custparents;
    }

    public String getNote() {
        return note;
    }

    public CifMaster note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getCustlatitude() {
        return custlatitude;
    }

    public CifMaster custlatitude(BigDecimal custlatitude) {
        this.custlatitude = custlatitude;
        return this;
    }

    public void setCustlatitude(BigDecimal custlatitude) {
        this.custlatitude = custlatitude;
    }

    public BigDecimal getCustlongitude() {
        return custlongitude;
    }

    public CifMaster custlongitude(BigDecimal custlongitude) {
        this.custlongitude = custlongitude;
        return this;
    }

    public void setCustlongitude(BigDecimal custlongitude) {
        this.custlongitude = custlongitude;
    }

    public Integer getActive() {
        return active;
    }

    public CifMaster active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public CifMaster isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public CifMaster createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public CifMaster createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public CifMaster lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public CifMaster lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public MtdWard getMtdWard() {
        return mtdWard;
    }

    public CifMaster mtdWard(MtdWard mtdWard) {
        this.mtdWard = mtdWard;
        return this;
    }

    public void setMtdWard(MtdWard mtdWard) {
        this.mtdWard = mtdWard;
    }

    public MtdOrganization getMtdOrganization() {
        return mtdOrganization;
    }

    public CifMaster mtdOrganization(MtdOrganization mtdOrganization) {
        this.mtdOrganization = mtdOrganization;
        return this;
    }

    public void setMtdOrganization(MtdOrganization mtdOrganization) {
        this.mtdOrganization = mtdOrganization;
    }

    public MtdEcosectors getMtdEcosectors() {
        return mtdEcosectors;
    }

    public CifMaster mtdEcosectors(MtdEcosectors mtdEcosectors) {
        this.mtdEcosectors = mtdEcosectors;
        return this;
    }

    public void setMtdEcosectors(MtdEcosectors mtdEcosectors) {
        this.mtdEcosectors = mtdEcosectors;
    }

    public MtdCustomergroup getMtdCustomergroup() {
        return mtdCustomergroup;
    }

    public CifMaster mtdCustomergroup(MtdCustomergroup mtdCustomergroup) {
        this.mtdCustomergroup = mtdCustomergroup;
        return this;
    }

    public void setMtdCustomergroup(MtdCustomergroup mtdCustomergroup) {
        this.mtdCustomergroup = mtdCustomergroup;
    }

    public Set<CifDevice> getCifDevices() {
        return cifDevices;
    }

    public CifMaster cifDevices(Set<CifDevice> cifDevices) {
        this.cifDevices = cifDevices;
        return this;
    }

    public CifMaster addCifDevice(CifDevice cifDevice) {
        this.cifDevices.add(cifDevice);
        cifDevice.setCifMaster(this);
        return this;
    }

    public CifMaster removeCifDevice(CifDevice cifDevice) {
        this.cifDevices.remove(cifDevice);
        cifDevice.setCifMaster(null);
        return this;
    }

    public void setCifDevices(Set<CifDevice> cifDevices) {
        this.cifDevices = cifDevices;
    }

    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CifMaster cifMaster = (CifMaster) o;
        if (cifMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifMaster{" +
            "id=" + getId() +
            ", customercode='" + getCustomercode() + "'" +
            ", customername='" + getCustomername() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", identifycode='" + getIdentifycode() + "'" +
            ", identifydate='" + getIdentifydate() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobilenum='" + getMobilenum() + "'" +
            ", phonenum='" + getPhonenum() + "'" +
            ", customertype='" + getCustomertype() + "'" +
            ", custparents='" + getCustparents() + "'" +
            ", note='" + getNote() + "'" +
            ", custlatitude='" + getCustlatitude() + "'" +
            ", custlongitude='" + getCustlongitude() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
