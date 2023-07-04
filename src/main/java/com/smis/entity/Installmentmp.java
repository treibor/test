package com.smis.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity 
public class Installmentmp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,  generator= "install_generator")
	@SequenceGenerator(name="install_generator", allocationSize = 1, sequenceName = "install_seq", initialValue = 1)
	private long installmentId;
	private int installmentNo;
	@Digits(integer=10, fraction=2)
	private BigDecimal installmentAmountPrev;
	@Digits(integer=10, fraction=2)
	private BigDecimal installmentAmount;
	private String installmentLetter;
	//private String installmentCheque;
	private LocalDate installmentDate;
	private String installmentLabel;
	private String ucLetter;
	private LocalDate ucDate;
	@ManyToOne 
	@JoinColumn(name="workId", referencedColumnName = "workId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull
	private Workmp workmp;
	public long getInstallmentId() {
		return installmentId;
	}
	public void setInstallmentId(long installmentId) {
		this.installmentId = installmentId;
	}
	public int getInstallmentNo() {
		return installmentNo;
	}
	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}
	public BigDecimal getInstallmentAmountPrev() {
		return installmentAmountPrev;
	}
	public void setInstallmentAmountPrev(BigDecimal installmentAmountPrev) {
		this.installmentAmountPrev = installmentAmountPrev;
	}
	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public String getInstallmentLetter() {
		return installmentLetter;
	}
	public void setInstallmentLetter(String installmentLetter) {
		this.installmentLetter = installmentLetter;
	}
	public LocalDate getInstallmentDate() {
		return installmentDate;
	}
	public void setInstallmentDate(LocalDate installmentDate) {
		this.installmentDate = installmentDate;
	}
	public String getInstallmentLabel() {
		return installmentLabel;
	}
	public void setInstallmentLabel(String installmentLabel) {
		this.installmentLabel = installmentLabel;
	}
	public String getUcLetter() {
		return ucLetter;
	}
	public void setUcLetter(String ucLetter) {
		this.ucLetter = ucLetter;
	}
	public LocalDate getUcDate() {
		return ucDate;
	}
	public void setUcDate(LocalDate ucDate) {
		this.ucDate = ucDate;
	}
	public Workmp getWorkmp() {
		return workmp;
	}
	public void setWorkmp(Workmp workmp) {
		this.workmp = workmp;
	}
	@Override
	public String toString() {
		return "Installmentmp [installmentId=" + installmentId + ", installmentNo=" + installmentNo
				+ ", installmentAmountPrev=" + installmentAmountPrev + ", installmentAmount=" + installmentAmount
				+ ", installmentLetter=" + installmentLetter + ", installmentDate=" + installmentDate
				+ ", installmentLabel=" + installmentLabel + ", ucLetter=" + ucLetter + ", ucDate=" + ucDate
				+ ", workmp=" + workmp + "]";
	}
	
	

}
