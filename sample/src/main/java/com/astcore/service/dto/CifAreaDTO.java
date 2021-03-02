package com.astcore.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the CifArea entity.
 */
public class CifAreaDTO implements Serializable {

    private Long id;

    private Integer cifareaparent;

    @NotNull
    @Size(min = 1, max = 30)
    private String cifareacode;

    @NotNull
    @Size(min = 1, max = 255)
    private String cifareaname;

    @NotNull
    @Size(min = 1, max = 255)
    private String cifareadesc;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer active;

    //@NotNull
    //@Min(value = 0)
    //@Max(value = 10)
    private Integer isdel;

    @Size(min = 1, max = 50)
    private String createby;

    private LocalDate createdate;

    @Size(min = 1, max = 50)
    private String lastmodifyby;

    private LocalDate lastmodifydate;

    private Long cifMasterId;
    
    private String parentname;
    
    private String cifMasterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCifareaparent() {
        return cifareaparent;
    }

    public void setCifareaparent(Integer cifareaparent) {
        this.cifareaparent = cifareaparent;
    }

    public String getCifareacode() {
        return cifareacode;
    }

    public void setCifareacode(String cifareacode) {
        this.cifareacode = cifareacode;
    }

    public String getCifareaname() {
        return cifareaname;
    }

    public void setCifareaname(String cifareaname) {
        this.cifareaname = cifareaname;
    }

    public String getCifareadesc() {
        return cifareadesc;
    }

    public void setCifareadesc(String cifareadesc) {
        this.cifareadesc = cifareadesc;
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

    public LocalDate getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public String getLastmodifyby() {
        return lastmodifyby;
    }

    public void setLastmodifyby(String lastmodifyby) {
        this.lastmodifyby = lastmodifyby;
    }

    public LocalDate getLastmodifydate() {
        return lastmodifydate;
    }

    public void setLastmodifydate(LocalDate lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    public Long getCifMasterId() {
        return cifMasterId;
    }

    public void setCifMasterId(Long cifMasterId) {
        this.cifMasterId = cifMasterId;
    }

    public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CifAreaDTO cifAreaDTO = (CifAreaDTO) o;
        if(cifAreaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifAreaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifAreaDTO{" +
            "id=" + getId() +
            ", cifareaparent='" + getCifareaparent() + "'" +
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

	public String getCifMasterName() {
		return cifMasterName;
	}

	public void setCifMasterName(String cifMasterName) {
		this.cifMasterName = cifMasterName;
	}
}
