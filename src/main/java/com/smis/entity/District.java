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
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_generator")
	@SequenceGenerator(name="district_generator", sequenceName = "district_seq", allocationSize=1)
	private long districtId;
	
	@NotEmpty
	private String districtName;
	private long districtCode;
	@NotEmpty
	private String districtHq;
	private String districtLabel;
	private String districtAddress;
	private String districtPhone;
	private String districtPin;
	private String districtFax;
	private String districtEmail;
	@NotEmpty
	private String deputyCommissioner;
	private String deputyCommissionerName;
	@ManyToOne
	@JoinColumn(name="stateId")
	@NotNull
	private State state;
	
	public District() {
		super();
	}

	public long getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(long districtCode) {
		this.districtCode = districtCode;
	}

	public long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictLabel() {
		return districtLabel;
	}

	public void setDistrictLabel(String districtLabel) {
		this.districtLabel = districtLabel;
	}

	public String getDistrictAddress() {
		return districtAddress;
	}

	public void setDistrictAddress(String districtAddress) {
		this.districtAddress = districtAddress;
	}

	public String getDistrictPhone() {
		return districtPhone;
	}

	public void setDistrictPhone(String districtPhone) {
		this.districtPhone = districtPhone;
	}

	public String getDistrictPin() {
		return districtPin;
	}

	public void setDistrictPin(String districtPin) {
		this.districtPin = districtPin;
	}

	public String getDistrictFax() {
		return districtFax;
	}

	public void setDistrictFax(String districtFax) {
		this.districtFax = districtFax;
	}

	public String getDistrictEmail() {
		return districtEmail;
	}

	public void setDistrictEmail(String districtEmail) {
		this.districtEmail = districtEmail;
	}

	public String getDeputyCommissioner() {
		return deputyCommissioner;
	}

	public void setDeputyCommissioner(String deputyCommissioner) {
		this.deputyCommissioner = deputyCommissioner;
	}
	

	public String getDistrictHq() {
		return districtHq;
	}

	public void setDistrictHq(String districtHq) {
		this.districtHq = districtHq;
	}

	public String getDeputyCommissionerName() {
		return deputyCommissionerName;
	}

	public void setDeputyCommissionerName(String deputyCommissionerName) {
		this.deputyCommissionerName = deputyCommissionerName;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "District [districtId=" + districtId + ", districtName=" + districtName + ", districtHq=" + districtHq
				+ ", districtLabel=" + districtLabel + ", districtAddress=" + districtAddress + ", districtPhone="
				+ districtPhone + ", districtPin=" + districtPin + ", districtFax=" + districtFax + ", districtEmail="
				+ districtEmail + ", deputyCommissioner=" + deputyCommissioner + ", deputyCommissionerName="
				+ deputyCommissionerName + ", state=" + state + "]";
	}

	
}
