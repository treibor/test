package com.smis.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Constituency {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consti_generator")
	@SequenceGenerator(name="consti_generator", sequenceName = "consti_seq", allocationSize=1)
	private long constituencyId;
	private int constituencyNo;
	
	@NotEmpty
	private String constituencyName;
	
	private String constituencyLabel;
	@NotEmpty
	private String constituencyMLA;
	@ManyToOne
	@JoinColumn(name="districtId")
	@NotNull
	private District district;
	private boolean inUse;
	
	public Constituency() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getConstituencyId() {
		return constituencyId;
	}
	public void setConstituencyId(long constituencyId) {
		this.constituencyId = constituencyId;
	}
	public int getConstituencyNo() {
		return constituencyNo;
	}
	public void setConstituencyNo(int constituencyNo) {
		this.constituencyNo = constituencyNo;
	}
	public String getConstituencyName() {
		return constituencyName;
	}
	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}
	public String getConstituencyLabel() {
		return constituencyLabel;
	}
	public void setConstituencyLabel(String constituencyLabel) {
		this.constituencyLabel = constituencyLabel;
	}
	public String getConstituencyMLA() {
		return constituencyMLA;
	}
	public void setConstituencyMLA(String constituencyMLA) {
		this.constituencyMLA = constituencyMLA;
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
		return "Constituency [constituencyId=" + constituencyId + ", constituencyNo=" + constituencyNo
				+ ", constituencyName=" + constituencyName + ", constituencyLabel=" + constituencyLabel
				+ ", constituencyMLA=" + constituencyMLA + ", district=" + district + "]";
	}
	
	
	
}
