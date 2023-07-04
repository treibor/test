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
public class Installment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,  generator= "inst_generator")
	@SequenceGenerator(name="inst_generator", allocationSize = 1, sequenceName = "inst_seq", initialValue = 1)
	private long installmentId;
	private int installmentNo;
	@Digits(integer=10, fraction=2)
	private BigDecimal installmentAmountPrev;
	@Digits(integer=10, fraction=2)
	private BigDecimal installmentAmount;
	private String installmentLetter;
	private String installmentCheque;
	private LocalDate installmentDate;
	private String installmentLabel;
	private String ucLetter;
	private LocalDate ucDate;
	@ManyToOne 
	@JoinColumn(name="workId", referencedColumnName = "workId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull
	private Work work;
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
	public String getInstallmentCheque() {
		return installmentCheque;
	}
	public void setInstallmentCheque(String installmentCheque) {
		this.installmentCheque = installmentCheque;
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

	
	
	public Work getWork() {
		return work;
	}
	public void setWork(Work work) {
		this.work = work;
	}
	

	@Override
	public String toString() {
		return "Installment [installmentId=" + installmentId + ", installmentNo=" + installmentNo
				+ ", installmentAmountPrev=" + installmentAmountPrev + ", installmentAmount=" + installmentAmount
				+ ", installmentLetter=" + installmentLetter + ", installmentCheque=" + installmentCheque
				+ ", installmentDate=" + installmentDate + ", installmentLabel=" + installmentLabel + ", ucLetter="
				+ ucLetter + ", ucDate=" + ucDate + ", work=" + work + "]";
	}
	


}
