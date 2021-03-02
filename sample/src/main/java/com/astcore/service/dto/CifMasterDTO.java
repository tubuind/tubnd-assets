package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CifMaster entity.
 */
public class CifMasterDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String customercode;

    @NotNull
    @Size(min = 1, max = 500)
    private String customername;

    private Integer sex;

    private Date birthday;

    @NotNull
    @Size(min = 1, max = 50)
    private String identifycode;

    private Date identifydate;

    @Size(min = 0, max = 500)
    private String address;

    @NotNull
    @Size(min = 1, max = 50)
    private String mobilenum;

    @NotNull
    @Size(min = 1, max = 50)
    private String phonenum;

    @NotNull
    @Size(min = 1, max = 10)
    private String customertype;

    private Integer custparents;
    
    private String custparentsName;

    @Size(min = 0, max = 1000)
    private String note;

    private BigDecimal custlatitude;

    private BigDecimal custlongitude;

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

    private Long mtdWardId;

    private Long mtdOrganizationId;

    private Long mtdEcosectorsId;

    private Long mtdCustomergroupId;
    
    private String mtdOrganizationName;

    private String mtdEcosectorsName;

    private String mtdCustomergroupName;
    
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

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentifycode() {
        return identifycode;
    }

    public void setIdentifycode(String identifycode) {
        this.identifycode = identifycode;
    }

    public Date getIdentifydate() {
        return identifydate;
    }

    public void setIdentifydate(Date identifydate) {
        this.identifydate = identifydate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public Integer getCustparents() {
        return custparents;
    }

    public void setCustparents(Integer custparents) {
        this.custparents = custparents;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getCustlatitude() {
        return custlatitude;
    }

    public void setCustlatitude(BigDecimal custlatitude) {
        this.custlatitude = custlatitude;
    }

    public BigDecimal getCustlongitude() {
        return custlongitude;
    }

    public void setCustlongitude(BigDecimal custlongitude) {
        this.custlongitude = custlongitude;
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

    public Long getMtdWardId() {
        return mtdWardId;
    }

    public void setMtdWardId(Long mtdWardId) {
        this.mtdWardId = mtdWardId;
    }

    public Long getMtdOrganizationId() {
        return mtdOrganizationId;
    }

    public void setMtdOrganizationId(Long mtdOrganizationId) {
        this.mtdOrganizationId = mtdOrganizationId;
    }

    public Long getMtdEcosectorsId() {
        return mtdEcosectorsId;
    }

    public void setMtdEcosectorsId(Long mtdEcosectorsId) {
        this.mtdEcosectorsId = mtdEcosectorsId;
    }

    public Long getMtdCustomergroupId() {
        return mtdCustomergroupId;
    }

    public void setMtdCustomergroupId(Long mtdCustomergroupId) {
        this.mtdCustomergroupId = mtdCustomergroupId;
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

        CifMasterDTO cifMasterDTO = (CifMasterDTO) o;
        if(cifMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifMasterDTO{" +
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

	public String getMtdOrganizationName() {
		return mtdOrganizationName;
	}

	public void setMtdOrganizationName(String mtdOrganizationName) {
		this.mtdOrganizationName = mtdOrganizationName;
	}

	public String getMtdEcosectorsName() {
		return mtdEcosectorsName;
	}

	public void setMtdEcosectorsName(String mtdEcosectorsName) {
		this.mtdEcosectorsName = mtdEcosectorsName;
	}

	public String getMtdCustomergroupName() {
		return mtdCustomergroupName;
	}

	public void setMtdCustomergroupName(String mtdCustomergroupName) {
		this.mtdCustomergroupName = mtdCustomergroupName;
	}

	public String getCustparentsName() {
		return custparentsName;
	}

	public void setCustparentsName(String custparentsName) {
		this.custparentsName = custparentsName;
	}
}
