package com.kiranaregister.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyReport {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    
    @JsonProperty("totalAmountInr")
    private BigDecimal totalAmountInr;
    
    @JsonProperty("totalAmountUsd")
    private BigDecimal totalAmountUsd;

    public DailyReport(LocalDate date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public DailyReport(LocalDate date, BigDecimal totalAmount, BigDecimal totalAmountInr, BigDecimal totalAmountUsd) {
        this.date = date;
        this.totalAmount = totalAmount;
        this.totalAmountInr = totalAmountInr;
        this.totalAmountUsd = totalAmountUsd;
    }
    
    public BigDecimal getTotalAmountInr() {
		return totalAmountInr;
	}
    
	public BigDecimal getTotalAmountUsd() {
		return totalAmountUsd;
	}

	// Getters for the properties
    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

}
