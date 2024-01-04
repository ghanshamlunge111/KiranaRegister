package com.kiranaregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiranaregister.entity.DailyReport;
import com.kiranaregister.entity.Transaction;
import com.kiranaregister.entity.TransactionRequest;
import com.kiranaregister.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@PostMapping("/record")
	public Transaction recordTransaction(@RequestBody TransactionRequest transactionRequest) {
		return transactionService.recordTransaction(transactionRequest);
	}

	@GetMapping("/dailyReports")
	public List<DailyReport> getDailyReports(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		return transactionService.generateDailyReports(startDate, endDate);
	}

}