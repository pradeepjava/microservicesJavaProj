package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.service.ILoansClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoansServiceDelegate {

    @Autowired
    private ILoansClient iLoansClient;

    @CircuitBreaker(name="loansCallCircuitBreaker", fallbackMethod ="fetchLoanDetailsFallback")
    public ResponseEntity<LoansDto> fetchLoanDetails(String corelationId, String mobileNumber) {
        return iLoansClient.fetchLoanDetails(corelationId, mobileNumber);
    }

    public ResponseEntity<LoansDto> fetchLoanDetailsFallback(String corelationId, String mobileNumber, Throwable t) {
        LoansDto loansDto = new LoansDto();
        loansDto.setLoanNumber("000000000000");
        return ResponseEntity.ok(loansDto);
    }
}
