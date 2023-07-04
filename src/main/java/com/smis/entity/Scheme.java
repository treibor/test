package com.smis.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Scheme {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "scheme_generator")
	@SequenceGenerator(name="scheme_generator", initialValue = 1, sequenceName = "scheme_sequence", allocationSize = 1)
	private long schemeId;
	@NotEmpty
	private String schemeName;
	@NotEmpty
	private String schemeNameLong;
	@NotEmpty
	private String schemeLabel;
	@Digits(integer=12, fraction=2)
	private BigDecimal schemeAllocation;
	@NotEmpty
	private String schemeDept;
	private String schemeDeptLong;
	private int schemeDuration;
	@ManyToOne
	@JoinColumn(name="districtId")
	@NotNull
	private District district;
	private int schemeReport;
	private boolean inUse;
	
	
	public long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getSchemeNameLong() {
		return schemeNameLong;
	}
	public void setSchemeNameLong(String schemeNameLong) {
		this.schemeNameLong = schemeNameLong;
	}
	public String getSchemeLabel() {
		return schemeLabel;
	}
	public void setSchemeLabel(String schemeLabel) {
		this.schemeLabel = schemeLabel;
	}
	public BigDecimal getSchemeAllocation() {
		return schemeAllocation;
	}
	public void setSchemeAllocation(BigDecimal schemeAllocation) {
		this.schemeAllocation = schemeAllocation;
	}
	public String getSchemeDept() {
		return schemeDept;
	}
	public void setSchemeDept(String schemeDept) {
		this.schemeDept = schemeDept;
	}
	public String getSchemeDeptLong() {
		return schemeDeptLong;
	}
	public void setSchemeDeptLong(String schemeDeptLong) {
		this.schemeDeptLong = schemeDeptLong;
	}
	public int getSchemeDuration() {
		return schemeDuration;
	}
	public void setSchemeDuration(int schemeDuration) {
		this.schemeDuration = schemeDuration;
	}
	
	
	public int getSchemeReport() {
		return schemeReport;
	}
	public void setSchemeReport(int schemeReport) {
		this.schemeReport = schemeReport;
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
		return "Scheme [schemeId=" + schemeId + ", schemeName=" + schemeName + ", schemeNameLong=" + schemeNameLong
				+ ", schemeLabel=" + schemeLabel + ", schemeAllocation=" + schemeAllocation + ", schemeDept="
				+ schemeDept + ", schemeDeptLong=" + schemeDeptLong + ", schemeDuration=" + schemeDuration
				+ ", district=" + district + ", schemeReport=" + schemeReport + "]";
	}
	
}
