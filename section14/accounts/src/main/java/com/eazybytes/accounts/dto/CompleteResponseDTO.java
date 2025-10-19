package com.eazybytes.accounts.dto;

import lombok.Data;

@Data
public class CompleteResponseDTO {
    private CustomerDto customerDto;
    private CardsDto cardsDto;
    private LoansDto loansDto;
}
