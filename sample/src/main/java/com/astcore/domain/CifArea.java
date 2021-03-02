package com.astcore.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A CifArea.
 */
@Entity
@Table(name = "cif_area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cifarea")
public class CifArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    //@Column(name = "cifareaparent")
    //private Integer cifareaparent;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "cifareacode", length = 30, nullable = false)
    private String cifareacode;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cifareaname", length = 255, nullable = false)
    private String cifareaname;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cifareadesc", length = 255, nullable = false)
    private String cifareadesc;

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
    private CifMaster cifMaster;
    
    @ManyToOne
    private CifArea parent;

    @OneToMany(mappedBy = "cifArea")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CifAreaDevice> cifAreaDevices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	//    public Integer getCifareaparent() {
	//        return cifareaparent;
	//    }
	//
	//    public CifArea cifareaparent(Integer cifareaparent) {
	//        this.cifareaparent = cifareaparent;
	//        return this;
	//    }
	//
	//    public void setCifareaparent(Integer cifareaparent) {
	//        this.cifareaparent = cifareaparent;
	//    }

    public String getCifareacode() {
        return cifareacode;
    }

    public CifArea cifareacode(String cifareacode) {
        this.cifareacode = cifareacode;
        return this;
    }

    public void setCifareacode(String cifareacode) {
        this.cifareacode = cifareacode;
    }

    public String getCifareaname() {
        return cifareaname;
    }

    public CifArea cifareaname(String cifareaname) {
        this.cifareaname = cifareaname;
        return this;
    }

    public void setCifareaname(String cifareaname) {
        this.cifareaname = cifareaname;
    }

    public String getCifareadesc() {
        return cifareadesc;
    }

    public CifArea cifareadesc(String cifareadesc) {
        this.cifareadesc = cifareadesc;
        return this;
    }

    public void setCifareadesc(String cifareadesc) {
        this.cifareadesc = cifareadesc;
    }

    public Integer getActive() {
        return active;
    }

    public CifArea active(Integer active) {
        this.active = active;
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public CifArea isdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreateby() {
        return createby;
    }

    public CifArea createby(String createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public CifArea createdate(Date createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public CifArea lastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
        return this;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public Date getLastmodifydate() {
        return lastmodifydate;
    }

    public CifArea lastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
        return this;
    }

    public void setLastmodifydate(Date lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public CifMaster getCifMaster() {
        return cifMaster;
    }

    public CifArea cifMaster(CifMaster cifMaster) {
        this.cifMaster = cifMaster;
        return this;
    }

    public void setCifMaster(CifMaster cifMaster) {
        this.cifMaster = cifMaster;
    }

    public Set<CifAreaDevice> getCifAreaDevices() {
        return cifAreaDevices;
    }

    public CifArea cifAreaDevices(Set<CifAreaDevice> cifAreaDevices) {
        this.cifAreaDevices = cifAreaDevices;
        return this;
    }

    public CifArea addCifAreaDevice(CifAreaDevice cifAreaDevice) {
        this.cifAreaDevices.add(cifAreaDevice);
        cifAreaDevice.setCifArea(this);
        return this;
    }

    public CifArea removeCifAreaDevice(CifAreaDevice cifAreaDevice) {
        this.cifAreaDevices.remove(cifAreaDevice);
        cifAreaDevice.setCifArea(null);
        return this;
    }

    public void setCifAreaDevices(Set<CifAreaDevice> cifAreaDevices) {
        this.cifAreaDevices = cifAreaDevices;
    }
    
    public CifArea getParent() {
		return parent;
	}

	public void setParent(CifArea parent) {
		this.parent = parent;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CifArea cifArea = (CifArea) o;
        if (cifArea.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifArea.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifArea{" +
            "id=" + getId() +
            //            ", cifareaparent='" + getCifareaparent() + "'" +
            ", cifareacode='" + getCifareacode() + "'" +
            ", cifareaname='" + getCifareaname() + "'" +
            ", cifareadesc='" + getCifareadesc() + "'" +
            ", active='" + getActive() + "'" +
            ", isdel='" + getIsdel() + "'" +
            ", createby='" + getCreateby() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", lastmodifyby='" + getLastmodifyby() + "'" +
            ", lastmodifydate='" + getLastmodifydate() + "'" +
            "}";
    }
}
