package com.astcore.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the CifAreaDevice entity.
 */
public class CifAreaDeviceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String devicecode;

    private LocalDate startdate;

    private Long cifAreaId;

    private Long cifDeviceId;
    
    private String cifAreaName;

    private String cifDeviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public Long getCifAreaId() {
        return cifAreaId;
    }

    public void setCifAreaId(Long cifAreaId) {
        this.cifAreaId = cifAreaId;
    }

    public Long getCifDeviceId() {
        return cifDeviceId;
    }

    public void setCifDeviceId(Long cifDeviceId) {
        this.cifDeviceId = cifDeviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CifAreaDeviceDTO cifAreaDeviceDTO = (CifAreaDeviceDTO) o;
        if(cifAreaDeviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifAreaDeviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifAreaDeviceDTO{" +
            "id=" + getId() +
            ", devicecode='" + getDevicecode() + "'" +
            ", startdate='" + getStartdate() + "'" +
            "}";
    }

	public String getCifAreaName() {
		return cifAreaName;
	}

	public void setCifAreaName(String cifAreaName) {
		this.cifAreaName = cifAreaName;
	}

	public String getCifDeviceName() {
		return cifDeviceName;
	}

	public void setCifDeviceName(String cifDeviceName) {
		this.cifDeviceName = cifDeviceName;
	}

}
