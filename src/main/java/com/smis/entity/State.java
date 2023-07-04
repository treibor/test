package com.smis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_generator")
	@SequenceGenerator(name="state_generator", sequenceName = "state_seq", allocationSize=1)
	private long stateId;
	@Column(unique=true)
	private String stateName;
	private String stateLabel;
	private String stateHq;
	
	public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}

	public String getStateHq() {
		return stateHq;
	}

	public void setStateHq(String stateHq) {
		this.stateHq = stateHq;
	}

	@Override
	public String toString() {
		return "State [stateId=" + stateId + ", stateName=" + stateName + ", stateLabel=" + stateLabel + ", stateHq="
				+ stateHq + "]";
	}
	
	
	
}
