package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CompleteResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ICompleteResponseProvider {

    public CompleteResponseDTO getCompleteResponse(String corelationId, String mobileNumber);
}
