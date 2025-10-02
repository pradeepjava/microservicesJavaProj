package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.CompleteResponseDTO;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.accounts.service.ICardsClient;
import com.eazybytes.accounts.service.ICompleteResponseProvider;
import com.eazybytes.accounts.service.ILoansClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompleteResponseProviderImpl implements ICompleteResponseProvider {
    @Autowired
    private IAccountsService iAccountsService;
    @Autowired
    LoansServiceDelegate loansServiceDelegate;
    @Autowired
    CardsServiceDelegate cardsServiceDelegate;

    @Override
    public CompleteResponseDTO getCompleteResponse(String corelationId,String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsServiceDelegate.fetchCardsDetails(corelationId, mobileNumber);
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansServiceDelegate.fetchLoanDetails(corelationId, mobileNumber);
        CompleteResponseDTO completeResponseDTO = new CompleteResponseDTO();
        completeResponseDTO.setCustomerDto(customerDto);
        if(loansDtoResponseEntity != null) {
            completeResponseDTO.setLoansDto(loansDtoResponseEntity.getBody());

        }
        if(cardsDtoResponseEntity != null) {
            completeResponseDTO.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return completeResponseDTO;
    }

}
