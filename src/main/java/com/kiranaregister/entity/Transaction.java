package com.kiranaregister.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "amountInr")
	private BigDecimal amountInr;

	@Column(name = "amountUsd")
	private BigDecimal amountUsd;

	@Column(name = "currency")
	private String currency;

	@Column(name = "transaction_date")
	private LocalDate transactionDate;

	@Column(name = "transaction_type")
	private String transactionType;

	// Constructors, getters, and setters

	public Long getId() {
		return id;
	}

	public BigDecimal getAmountInr() {
		return amountInr;
	}

	public void setAmountInr(BigDecimal amountInr) {
		this.amountInr = amountInr;
	}

	public BigDecimal getAmountUsd() {
		return amountUsd;
	}

	public void setAmountUsd(BigDecimal amountUsd) {
		this.amountUsd = amountUsd;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}



}