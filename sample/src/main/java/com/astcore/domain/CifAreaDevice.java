package com.astcore.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CifAreaDevice.
 */
@Entity
@Table(name = "cif_areadevice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cifareadevice")
public class CifAreaDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "devicecode", length = 30, nullable = false)
    private String devicecode;

    @Column(name = "startdate")
    private LocalDate startdate;

    @ManyToOne
    private CifArea cifArea;

    @OneToOne
    @JoinColumn(unique = true)
    private CifDevice cifDevice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public CifAreaDevice devicecode(String devicecode) {
        this.devicecode = devicecode;
        return this;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public CifAreaDevice startdate(LocalDate startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public CifArea getCifArea() {
        return cifArea;
    }

    public CifAreaDevice cifArea(CifArea cifArea) {
        this.cifArea = cifArea;
        return this;
    }

    public void setCifArea(CifArea cifArea) {
        this.cifArea = cifArea;
    }

    public CifDevice getCifDevice() {
        return cifDevice;
    }

    public CifAreaDevice cifDevice(CifDevice cifDevice) {
        this.cifDevice = cifDevice;
        return this;
    }

    public void setCifDevice(CifDevice cifDevice) {
        this.cifDevice = cifDevice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CifAreaDevice cifAreaDevice = (CifAreaDevice) o;
        if (cifAreaDevice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cifAreaDevice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CifAreaDevice{" +
            "id=" + getId() +
            ", devicecode='" + getDevicecode() + "'" +
            ", startdate='" + getStartdate() + "'" +
            "}";
    }
}
