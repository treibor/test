package com.smis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Year {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "year_generator")
	@SequenceGenerator(name="year_generator", initialValue = 1, sequenceName = "year_sequence", allocationSize = 1)
	private long yearId;
	@NotEmpty
	private String yearName;
	@NotEmpty
	private String yearLabel;
	@ManyToOne
	@JoinColumn(name="districtId")
	@NotNull
	private District district;
	private boolean inUse;
	
	public long getYearId() {
		return yearId;
	}
	public void setYearId(long yearId) {
		this.yearId = yearId;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getYearLabel() {
		return yearLabel;
	}
	public void setYearLabel(String yearLabel) {
		this.yearLabel = yearLabel;
	}
	
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	@Override
	public String toString() {
		return "Year [yearId=" + yearId + ", yearName=" + yearName + ", yearLabel=" + yearLabel + ", district="
				+ district + "]";
	}
	
	
}
