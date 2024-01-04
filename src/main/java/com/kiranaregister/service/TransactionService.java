package com.kiranaregister.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiranaregister.entity.DailyReport;
import com.kiranaregister.entity.ExchangeRatesResponse;
import com.kiranaregister.entity.Transaction;
import com.kiranaregister.entity.TransactionRequest;
import com.kiranaregister.repository.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CurrencyConversionService currencyConversionService;

	public Transaction recordTransaction(TransactionRequest transactionRequest) {
		// Process and save the transaction using transactionRepository
		// Perform currency conversion if the transaction currency is different from the base currency
		BigDecimal convertedAmount = transactionRequest.getAmount();
		BigDecimal convertedAmountToInr = null;

		ExchangeRatesResponse exchangeRates = currencyConversionService.fetchLatestExchangeRates();
		if (!"USD".equals(transactionRequest.getCurrency())) {
			BigDecimal exchangeRate = BigDecimal.valueOf(exchangeRates.getRates().get(transactionRequest.getCurrency()));
			if("D".equalsIgnoreCase(transactionRequest.getTransactionType())) {
				convertedAmount = transactionRequest.getAmount().multiply(exchangeRate).multiply(BigDecimal.valueOf(-1));
			}else {
				convertedAmount = transactionRequest.getAmount().multiply(exchangeRate);
			} 
		}

		if("USD".equals(transactionRequest.getCurrency())) {
			BigDecimal exchangeRate = BigDecimal.valueOf(exchangeRates.getRates().get("INR"));


			if("D".equalsIgnoreCase(transactionRequest.getTransactionType())) {
				convertedAmountToInr = transactionRequest.getAmount().divide(exchangeRate, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(-1));
			} else {
				convertedAmountToInr = transactionRequest.getAmount().divide(exchangeRate, RoundingMode.HALF_UP);
			}
		}

		Transaction transaction = new Transaction();
		transaction.setAmount(transactionRequest.getAmount());

		if(!"USD".equals(transactionRequest.getCurrency())) {
			transaction.setAmountUsd(convertedAmount);
		}else {
			if("D".equalsIgnoreCase(transactionRequest.getTransactionType())) {
				transaction.setAmountUsd(transactionRequest.getAmount().multiply(BigDecimal.valueOf(-1)));
				transaction.setAmountInr(convertedAmountToInr);
			}else {
				transaction.setAmountUsd(transactionRequest.getAmount());
				transaction.setAmountInr(convertedAmountToInr);
			}
		}

		if("INR".equals(transactionRequest.getCurrency())) {
			if("D".equalsIgnoreCase(transactionRequest.getTransactionType())) {
				transaction.setAmountInr(transactionRequest.getAmount().multiply(BigDecimal.valueOf(-1)));
			}else {
				transaction.setAmountInr(transactionRequest.getAmount());
			}
		}

		transaction.setTransactionType(transactionRequest.getTransactionType());
		transaction.setCurrency(transactionRequest.getCurrency());
		transaction.setTransactionDate(transactionRequest.getTransactionDate() != null ? transactionRequest.getTransactionDate() : LocalDate.now());
		Transaction savedTransaction = transactionRepository.save(transaction);

		return savedTransaction;
	}

	public List<DailyReport> generateDailyReports(String startDate, String endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDateTime = LocalDate.parse(startDate, formatter);
		LocalDate endDateTime = LocalDate.parse(endDate, formatter);

		System.out.println("startDateTime" + startDateTime);
		List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDateTime, endDateTime);  	

		Map<LocalDate, List<Transaction>> transactionsByDate = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getTransactionDate));

		List<DailyReport> dailyTotalAmountsList = transactionsByDate.entrySet().stream()
				.map(entry -> {
					BigDecimal totalAmount = entry.getValue().stream()
							.map(Transaction::getAmount)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalAmountInr = entry.getValue().stream()
							.map(Transaction::getAmountInr)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalAmountUsd = entry.getValue().stream()
							.map(Transaction::getAmountUsd)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					return new DailyReport(entry.getKey(), totalAmount, totalAmountInr, totalAmountUsd);
				})
				.collect(Collectors.toList());

		return dailyTotalAmountsList;


	}
}
