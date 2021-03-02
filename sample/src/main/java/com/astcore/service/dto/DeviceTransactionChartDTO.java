package com.astcore.service.dto;


import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DeviceTransaction entity.
 */
public class DeviceTransactionChartDTO implements Serializable {
	private BigDecimal currentvalue;
	
	private Date valuedate;

	public DeviceTransactionChartDTO(BigDecimal currentvalue, Date valuedate) {
		super();
		this.currentvalue = currentvalue;
		this.valuedate = valuedate;
	}

	public BigDecimal getCurrentvalue() {
		return currentvalue;
	}

	public void setCurrentvalue(BigDecimal currentvalue) {
		this.currentvalue = currentvalue;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}
}
