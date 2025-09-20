package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.service.ICardsClient;
import com.eazybytes.accounts.service.ILoansClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsServiceDelegate {
    @Autowired
    private ICardsClient cardsClient;

    @CircuitBreaker(name="cardsCallCircuitBreaker", fallbackMethod = "fetchCardsDetailsFallback")
    public ResponseEntity<CardsDto>fetchCardsDetails(String corelationId, String mobileNumber){
        return cardsClient.fetchCardDetails(corelationId, mobileNumber);
    }
    public ResponseEntity<CardsDto>fetchCardsDetailsFallback(String corelationId, String mobileNumber, Throwable throwable){
        CardsDto  cardsDto = new CardsDto();
        cardsDto.setCardNumber("000000000000");
        return ResponseEntity.ok().body(cardsDto);
    }
}
