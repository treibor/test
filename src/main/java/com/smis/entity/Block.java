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
public class Block {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "block_generator")
	@SequenceGenerator(name="block_generator", sequenceName = "block_seq", allocationSize=1)
	private long blockId;
	@NotEmpty
	private String blockName;
	@NotEmpty
	private String blockLabel;
	@NotEmpty
	private String blockDevelopmentOfficer;
	@ManyToOne
	@JoinColumn(name="districtId")
	@NotNull
	private District district;
	private boolean inUse;
	
	
	public long getBlockId() {
		return blockId;
	}
	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getBlockLabel() {
		return blockLabel;
	}
	public void setBlockLabel(String blockLabel) {
		this.blockLabel = blockLabel;
	}
	public String getBlockDevelopmentOfficer() {
		return blockDevelopmentOfficer;
	}
	public void setBlockDevelopmentOfficer(String blockDevelopmentOfficer) {
		this.blockDevelopmentOfficer = blockDevelopmentOfficer;
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
		return "Block [blockId=" + blockId + ", blockName=" + blockName + ", blockLabel=" + blockLabel
				+ ", blockDevelopmentOfficer=" + blockDevelopmentOfficer + ", district=" + district + "]";
	}
	
	
}
