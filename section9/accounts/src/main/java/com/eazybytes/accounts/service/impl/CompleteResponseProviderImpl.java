package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.CompleteResponseDTO;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.accounts.service.ICardsClient;
import com.eazybytes.accounts.service.ICompleteResponseProvider;
import com.eazybytes.accounts.service.ILoansClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompleteResponseProviderImpl implements ICompleteResponseProvider {
    @Autowired
    private IAccountsService iAccountsService;
    @Autowired
    private ICardsClient iCardsClient;
    @Autowired
    private ILoansClient iLoansClient;

    @Override
    public CompleteResponseDTO getCompleteResponse(String corelationId,String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = iCardsClient.fetchCardDetails(corelationId, mobileNumber);
        ResponseEntity<LoansDto> loansDtoResponseEntity = iLoansClient.fetchLoanDetails(corelationId, mobileNumber);
        CompleteResponseDTO completeResponseDTO = new CompleteResponseDTO();
        completeResponseDTO.setCustomerDto(customerDto);
        completeResponseDTO.setLoansDto(loansDtoResponseEntity.getBody());
        completeResponseDTO.setCardsDto(cardsDtoResponseEntity.getBody());
        return completeResponseDTO;
    }
}
