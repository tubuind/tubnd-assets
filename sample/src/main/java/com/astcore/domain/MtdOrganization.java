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
 * A MtdOrganization.
 */
@Entity
@Table(name = "mtd_organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mtdorganization")
public class MtdOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "organizationcode", length = 10, nullable = false)
    private String organizationcode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "organizationname", length = 50, nullable = false)
    private String organizationname;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mobilenum", length = 50, nullable = false)
    private String mobilenum;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "phonenum", length = 50, nullable = false)
    private String phonenum;

    @Column(name = "parents")
    private Integer parents;

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

    @OneToMany(mappedBy = "mtdOrganization")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CifMaster> cifMasters = new HashSet<>();
    
    @ManyToOne
    private MtdWard mtdWard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationcode() {
        return organizationcode;
    }

    public MtdOrganization organizationcode(String organizationcode) {
        this.organizationcode = organizationcode;
        return this;
    }

    public void setOrganizationcode(String organizationcode) {
        this.organizationcode = organizationcode;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public MtdOrganization organizationname(String organizationname) {
        this.organizationname = organizationname;
        return this;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }

    public String getAddress() {
        return address;
    }

    public MtdOrganization address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public MtdOrganization mobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
        return this;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public MtdOrganization phonenum(String phonenum) {
        this.phonenum = phonenum;
        return this;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public Integer getParents() {
        return parents;
    }

    public MtdOrganization parents(Integer parents) {
        this.parents = parents;
        return this;
    }

    public void setParents(Integer parents) {
        this.parents = parents;
    }

    public Integer getActive() {
        return active;
    }

    public MtdOrganization active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public MtdOrganization isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public MtdOrganization createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public MtdOrganization createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public MtdOrganization lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public MtdOrganization lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Set<CifMaster> getCifMasters() {
        return cifMasters;
    }

    public MtdOrganization cifMasters(Set<CifMaster> cifMasters) {
        this.cifMasters = cifMasters;
        return this;
    }

    public MtdOrganization addCifMaster(CifMaster cifMaster) {
        this.cifMasters.add(cifMaster);
        cifMaster.setMtdOrganization(this);
        return this;
    }

    public MtdOrganization removeCifMaster(CifMaster cifMaster) {
        this.cifMasters.remove(cifMaster);
        cifMaster.setMtdOrganization(null);
        return this;
    }

    public void setCifMasters(Set<CifMaster> cifMasters) {
        this.cifMasters = cifMasters;
    }

    public MtdWard getMtdWard() {
		return mtdWard;
	}

	public void setMtdWard(MtdWard mtdWard) {
		this.mtdWard = mtdWard;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MtdOrganization mtdOrganization = (MtdOrganization) o;
        if (mtdOrganization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdOrganization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdOrganization{" +
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
