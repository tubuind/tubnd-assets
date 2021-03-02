package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MtdOrganization entity.
 */
public class MtdOrganizationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String organizationcode;

    @NotNull
    @Size(min = 1, max = 50)
    private String organizationname;

    @NotNull
    @Size(min = 1, max = 500)
    private String address;

    @NotNull
    @Size(min = 1, max = 50)
    private String mobilenum;

    @NotNull
    @Size(min = 1, max = 50)
    private String phonenum;

    private Integer parents;

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
    
    private Long mtdProvinceId;
    
    private Long mtdDistrictId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationcode() {
        return organizationcode;
    }

    public void setOrganizationcode(String organizationcode) {
        this.organizationcode = organizationcode;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
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

    public Integer getParents() {
        return parents;
    }

    public void setParents(Integer parents) {
        this.parents = parents;
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

	public Long getMtdProvinceId() {
		return mtdProvinceId;
	}

	public void setMtdProvinceId(Long mtdProvinceId) {
		this.mtdProvinceId = mtdProvinceId;
	}

	public Long getMtdDistrictId() {
		return mtdDistrictId;
	}

	public void setMtdDistrictId(Long mtdDistrictId) {
		this.mtdDistrictId = mtdDistrictId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MtdOrganizationDTO mtdOrganizationDTO = (MtdOrganizationDTO) o;
        if(mtdOrganizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdOrganizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdOrganizationDTO{" +
            "id=" + getId() +
            ", organizationcode='" + getOrganizationcode() + "'" +
            ", organizationname='" + getOrganizationname() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobilenum='" + getMobilenum() + "'" +
            ", phonenum='" + getPhonenum() + "'" +
            ", parents='" + getParents() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
