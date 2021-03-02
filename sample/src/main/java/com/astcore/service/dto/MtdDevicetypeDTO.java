package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MtdDevicetype entity.
 */
public class MtdDevicetypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    private String devicetypename;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicetypename() {
        return devicetypename;
    }

    public void setDevicetypename(String devicetypename) {
        this.devicetypename = devicetypename;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MtdDevicetypeDTO mtdDevicetypeDTO = (MtdDevicetypeDTO) o;
        if(mtdDevicetypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mtdDevicetypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MtdDevicetypeDTO{" +
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
