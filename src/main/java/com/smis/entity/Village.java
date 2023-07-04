package com.smis.entity;

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
public class Village {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "village_generator")
	@SequenceGenerator(name="village_generator", sequenceName = "village_seq", allocationSize=1)
	private long villageId;
	
	@NotEmpty
	private String villageName;
	
	private String villageLabel;
	@ManyToOne
	@JoinColumn(name="blockId")
	private Block block;
	private boolean inUse;
	public long getVillageId() {
		return villageId;
	}
	public void setVillageId(long villageId) {
		this.villageId = villageId;
	}
	
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	public String getVillageLabel() {
		return villageLabel;
	}
	public void setVillageLabel(String villageLabel) {
		this.villageLabel = villageLabel;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
}
