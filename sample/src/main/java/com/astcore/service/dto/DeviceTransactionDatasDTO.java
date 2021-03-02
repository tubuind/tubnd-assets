package com.astcore.service.dto;


import java.util.Date;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DeviceTransaction entity.
 */
public class DeviceTransactionDatasDTO implements Serializable {

    @NotNull
    @Size(min = 1, max = 250)
    private String ID;
    
    @NotNull
    @Size(min = 1, max = 1024)
    private String values;
    
    @Size(min = 0, max = 128)
    private String TMR;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTMR() {
		return TMR;
	}

	public void setTMR(String tMR) {
		TMR = tMR;
	}
	
}
